package com.adrian_971029.ichef.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.activity.VideoActivity;
import com.adrian_971029.ichef.model.Step;
import com.bumptech.glide.Glide;

import java.util.List;

public class StepsAdapter extends ArrayAdapter<Step>{

    private List<Step> mListSteps;
    private static final String STEPS = "steps";

    @Override
    public int getCount() {
        return mListSteps.size();
    }

    public StepsAdapter(Context context, List<Step> objects) {
        super(context,0, objects);
        mListSteps = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Step steps = getItem(position);

        StepsAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_steps,null);
            holder = new StepsAdapter.ViewHolder();
            holder.mImagStep = (ImageView) convertView.findViewById(R.id.img_step);
            holder.mNumberSteps = (TextView) convertView.findViewById(R.id.tv_numberSteps);
            holder.mDescriptionsSteps = (TextView)convertView.findViewById(R.id.tv_descriptionsSteps);
            holder.layoutSteps = (LinearLayout)convertView.findViewById(R.id.layout_steps);
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
                if (steps.getVideoURL().equals("")){
                    Toast.makeText(getContext(), R.string.msg_nao_video,Toast.LENGTH_SHORT).show();
                } else{
                    Intent i = new Intent(getContext(), VideoActivity.class);
                    i.putExtra(STEPS,steps);
                    getContext().startActivity(i);
                }
            }
        });

        return convertView;

    }

    static class ViewHolder{
        ImageView mImagStep;
        TextView mNumberSteps;
        TextView mDescriptionsSteps;
        LinearLayout layoutSteps;
    }

}
