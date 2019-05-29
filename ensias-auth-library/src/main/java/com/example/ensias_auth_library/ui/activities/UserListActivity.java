package com.example.ensias_auth_library.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import com.example.ensias_auth_library.R;
import com.example.ensias_auth_library.models.Kid;
import com.example.ensias_auth_library.models.Organisation;
import com.example.ensias_auth_library.services.bgservices.BackgroundGameService;
import com.example.ensias_auth_library.services.db.DatabaseHelper;
import com.example.ensias_auth_library.services.db.DatabaseManager;
import com.example.ensias_auth_library.services.internet.NetworkStateReceiver;
import com.example.ensias_auth_library.ui.adapters.KidsRecyclerViewAdapter;
import com.example.ensias_auth_library.ui.adapters.OrganisationsRecyclerViewAdapter;
import com.example.ensias_auth_library.utils.CrossVariables;
import com.example.ensias_auth_library.utils.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;



/**
 * @ActivityRole Show the (organisations|kids) list depending on the logged user (Parent|Tutor)
 */
public class UserListActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    // Views
    RecyclerView myOrganisationsRecyclerView;
    private ImageButton backArrowImageView;
    private ImageButton logoutImageView;
    private TransitionDrawable transition;

    //Adapters
    private OrganisationsRecyclerViewAdapter mOrganisationRecyclerViewAdapter;
    private KidsRecyclerViewAdapter mKidsRecyclerViewAdapter;

    //DB Management
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    // Internet Check
    private NetworkStateReceiver networkStateReceiver;


    private LinearLayoutManager myOrganisationsLayoutManager;
    OrganisationsRecyclerViewAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onResume() {
        super.onResume();
        startNetworkBroadcastReceiver(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_user_list);

        mDBHelper = DatabaseHelper.getInstance(this);
        mDatabase  = mDBHelper.getWritableDatabase();

        //View Declarations
        backArrowImageView = findViewById(R.id.back_arrow);
        myOrganisationsRecyclerView = findViewById(R.id.my_list_recycler_view);
        logoutImageView = findViewById(R.id.logout_button);
        transition = (TransitionDrawable) myOrganisationsRecyclerView.getBackground();

        //Listners Settings
        listener = new OrganisationItemClickListner();
        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                SaveSharedPreference.setAccessToken(getApplicationContext(), null);
                logout();
            }
        });


        // Layout Manager Settings
        myOrganisationsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        myOrganisationsRecyclerView.setLayoutManager(myOrganisationsLayoutManager);


        // Adapter Settings
        if(SaveSharedPreference.getUserType(this).equals("tutor"))
            setOrganisationAdapter();
        else
            setParentAdapter();

        // set accompagnantId
        CrossVariables.accompagnantId = SaveSharedPreference.getAccompaniantId(getApplicationContext());
    }

    private void setOrganisationAdapter() {

        mOrganisationRecyclerViewAdapter = new OrganisationsRecyclerViewAdapter(DatabaseManager.getInstance(this).getOrganisationsList(),this,listener);
        myOrganisationsRecyclerView.setAdapter(mOrganisationRecyclerViewAdapter);
        runLayoutAnimation(myOrganisationsRecyclerView);
    }
    private void setParentAdapter() {

        mKidsRecyclerViewAdapter = new KidsRecyclerViewAdapter(DatabaseManager.getInstance(this).getParentKidsList(),this);
        myOrganisationsRecyclerView.setAdapter(mKidsRecyclerViewAdapter);
        runLayoutAnimation(myOrganisationsRecyclerView);
    }

    private class OrganisationItemClickListner implements OrganisationsRecyclerViewAdapter.RecyclerViewClickListener {
        @Override
        public void onClick(View view, Organisation organisation) {
            mKidsRecyclerViewAdapter = new KidsRecyclerViewAdapter(DatabaseManager.getInstance(getApplicationContext()).getOrganisationKidsList(organisation.getId()),UserListActivity.this);
            myOrganisationsRecyclerView.setAdapter(mKidsRecyclerViewAdapter);
            runLayoutAnimation(myOrganisationsRecyclerView);
            backArrowImageView.setVisibility(View.VISIBLE);
            transition.startTransition(500);
            backArrowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOrganisationAdapter();
                    v.setOnClickListener(null);
                    v.setVisibility(View.INVISIBLE);
                    transition.reverseTransition(500);
                }
            });
        }
    }
    public void logout() {
        clearUserData();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void clearUserData() {
        mDatabase.execSQL("DELETE FROM organisations");
        mDatabase.execSQL("DELETE FROM kids");
    }
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
    @Override
    public void onBackPressed() {
        /*Do Nothing*/
    }
    @Override
    public void networkAvailable() {
        Log.e("service started", "networkAvailable: service started" );
        int gameStatRowCount = DatabaseManager.getInstance(getApplicationContext()).gameStatsRowCount();
        if(gameStatRowCount>0){ // If there is data(rows) to send to the server
            startBackgroundGameService();
        }
    }

    @Override
    public void networkUnavailable() {

    }
    public void startBackgroundGameService(){
        Intent intentToService = new Intent(this, BackgroundGameService.class);
        startService(intentToService);
    }



    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    /**
     * Register the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     Unregister the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }
}
