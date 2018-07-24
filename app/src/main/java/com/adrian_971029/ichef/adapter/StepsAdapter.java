package com.adrian_971029.ichef.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.activity.DetailsActivity;
import com.adrian_971029.ichef.activity.VideoActivity;
import com.adrian_971029.ichef.fragment.StepsListFragment;
import com.adrian_971029.ichef.fragment.VideoFragment;
import com.adrian_971029.ichef.model.Step;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends ArrayAdapter<Step>{

    private List<Step> mListSteps;
    private static final String STEPS = "steps";
    private boolean isTablet;
    private DetailsActivity activity;
    private static boolean stepVisible;

    @Override
    public int getCount() {
        return mListSteps.size();
    }

    public StepsAdapter(Context context, List<Step> objects, boolean isTablet, DetailsActivity activity) {
        super(context,0, objects);
        mListSteps = objects;
        this.isTablet = isTablet;
        this.activity = activity;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Step steps = getItem(position);

        StepsAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_steps,null);
            holder = new StepsAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (StepsAdapter.ViewHolder)convertView.getTag();
        }

        if(!steps.getThumbnailURL().equals("")){
            Glide.with(getContext()).load(steps.getThumbnailURL()).thumbnail(0.1f).into(holder.mImagStep);
        } else if( !steps.getVideoURL().equals("")) {
            Glide.with(getContext()).load(steps.getVideoURL()).thumbnail(0.1f).into(holder.mImagStep);
        } else {
            Glide.with(getContext()).load(R.drawable.steps).thumbnail(0.1f).into(holder.mImagStep);
        }

        holder.mNumberSteps.setText("Passo " + steps.getId());
        holder.mDescriptionsSteps.setText(steps.getShortDescription());

        holder.layoutSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet) {
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setSteps(steps);

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.ingredient_container, videoFragment)
                            .commit();
                    stepVisible = true;
                } else {
                    Intent i = new Intent(getContext(), VideoActivity.class);
                    i.putExtra(STEPS,steps);
                    getContext().startActivity(i);
                }
            }
        });

        return convertView;

    }

    static class ViewHolder{
        @BindView(R.id.img_step)
        ImageView mImagStep;
        @BindView(R.id.tv_numberSteps)
        TextView mNumberSteps;
        @BindView(R.id.tv_descriptionsSteps)
        TextView mDescriptionsSteps;
        @BindView(R.id.layout_steps)
        LinearLayout layoutSteps;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public static boolean isStepVisible() {
        return stepVisible;
    }

    public static void setStepVisible(boolean visible) {
        stepVisible = visible;
    }

}
