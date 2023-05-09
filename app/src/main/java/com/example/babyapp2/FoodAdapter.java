package com.example.babyapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<FoodClass> {

    //passing in activity or context so CustomViewAdaptor can be used in many activities//
    //its needs to know at the time which activity the context is referring to//
    private int layoutResource;
    private Activity activity;
    private ArrayList<FoodClass> foodList = new ArrayList<>();

    public FoodAdapter(Activity act, int resource, ArrayList<FoodClass> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity = act;
        foodList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public FoodClass getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(FoodClass item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        //if there's no row - then we have to create row//
        if ( row == null || (row.getTag() == null)) {

            //makes object visible within the Android app//
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            //row is holding our view that we are inflating - holding list row//
            holder.foodName = (TextView) row.findViewById(R.id.name);
            holder.foodDate = (TextView) row.findViewById(R.id.dateText);
            holder.foodCalories = (TextView) row.findViewById(R.id.calories);

            row.setTag(holder);

        }else {

            holder = (ViewHolder) row.getTag();
        }


        holder.food = getItem(position);

        holder.foodName.setText(holder.food.getFoodName());
        holder.foodDate.setText(holder.food.getRecordDate());
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));

        //setting onClick listener on food item rows to take to delete page//
        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create intent and point it in the direction of where we want to go//
                Intent i = new Intent(activity, FoodDesc.class);

                //object we can add things to//
                //bundles hold data and pass them to our other activities, key/value pair//
                Bundle mBundle = new Bundle();
                //adds serializable object - food//
                mBundle.putSerializable("userObj", finalHolder.food);
                i.putExtras(mBundle);

                //passes our intent to activity//
                //have to specify which activity we are currently attached to//
                activity.startActivity(i);
            }
        });

        return row;

    }

    //for displaying food in list view//
    public class ViewHolder {
        FoodClass food;
        TextView foodName;
        TextView foodCalories;
        TextView foodDate;
    }
}
