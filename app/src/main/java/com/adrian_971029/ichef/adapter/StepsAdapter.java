package com.adrian_971029.ichef.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.model.Step;

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
            holder.mNumberSteps = (TextView) convertView.findViewById(R.id.tv_numberSteps);
            holder.mDescriptionsSteps = (TextView)convertView.findViewById(R.id.tv_descriptionsSteps);
            convertView.setTag(holder);
        }
        else{
            holder = (StepsAdapter.ViewHolder)convertView.getTag();
        }

        holder.mNumberSteps.setText("Passo " + steps.getId());
        holder.mDescriptionsSteps.setText(steps.getShortDescription());

        holder.mNumberSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.mDescriptionsSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        return convertView;

    }

    static class ViewHolder{
        TextView mNumberSteps;
        TextView mDescriptionsSteps;
    }

}
