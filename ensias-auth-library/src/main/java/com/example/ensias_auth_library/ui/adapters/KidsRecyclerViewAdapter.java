package com.example.ensias_auth_library.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ensias_auth_library.R;
import com.example.ensias_auth_library.animations.AnimationHelper;
import com.example.ensias_auth_library.models.Kid;
import com.example.ensias_auth_library.utils.CrossVariables;
import com.example.ensias_auth_library.utils.SaveSharedPreference;

import java.util.List;

/**
 * Created by younes on 8/20/2018.
 */

public class KidsRecyclerViewAdapter extends RecyclerView.Adapter<KidsRecyclerViewAdapter.ViewHolder> {
    private View cardView;
    List<Kid> mKids;
    Context mContext;
    private static int itemToOpen;
    public KidsRecyclerViewAdapter(List<Kid> kids, Context context){
        this.mKids = kids;
        this.mContext = context;
    }
    @Override
    public KidsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        cardView = inflater.inflate(R.layout.kid_item
                , parent, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KidsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.fillData(mKids.get(position));
    }

    @Override
    public int getItemCount() {
        return mKids.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout listViewItem;
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
//        ImageView offreView;
        TextView organisationNameTextView;
        ImageView detailsImageView;
        RelativeLayout kidDetailsRelativeLayout;
        TextView kidParentEmailTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.

            super(itemView);
            listViewItem = itemView.findViewById(R.id.list_view_item);
            organisationNameTextView = itemView.findViewById(R.id.organisation_name);
            detailsImageView = itemView.findViewById(R.id.details_imageView);
            kidDetailsRelativeLayout = itemView.findViewById(R.id.kid_details);
            kidParentEmailTextView = itemView.findViewById(R.id.company_email);



            //offerImageView = itemView.findViewById(R.id.offre);
        }
        public void fillData(final Kid kid){
            organisationNameTextView.setText(kid.getFirstName()+" - "+kid.getLastName());
            kidParentEmailTextView.setText(kid.getParentEmail());
            if(itemToOpen == kid.getId()){
                if(kidDetailsRelativeLayout.getVisibility() == View.GONE)
                    AnimationHelper.getInstance(mContext).expand(kidDetailsRelativeLayout);
                else
                    AnimationHelper.getInstance(mContext).collapse(kidDetailsRelativeLayout);
            }
            else {
                if(kidDetailsRelativeLayout.getVisibility() == View.VISIBLE)
                    AnimationHelper.getInstance(mContext).collapse(kidDetailsRelativeLayout);
            }
            if(SaveSharedPreference.getUserType(mContext).equals("tutor")){
                detailsImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(kidDetailsRelativeLayout.getVisibility() == View.VISIBLE)
                            itemToOpen = 0;
                        else
                            itemToOpen = kid.getId();
                        notifyDataSetChanged();
                    }
                });
            }
            else{
                detailsImageView.setVisibility(View.GONE);
            }

            listViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrossVariables.kidId = kid.getId().toString();
                    Intent intent = new Intent(mContext, CrossVariables.mainActivityName);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}

