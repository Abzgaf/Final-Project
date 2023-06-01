package com.example.babyapp2;

// implementing a custom adapter for a ListView for FoodActivity
// The adapter extends the ArrayAdapter class and takes in an ArrayList of custom objects called FoodClass.
// The adapter inflates a layout for each row of the ListView and populates it with data from the FoodClass objects.
// The adapter sets an onClickListener for each row that starts a new activity with more details about the selected food item.

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

    private int resLayout;
    private Activity thisActivity;
    private ArrayList<FoodClass> food_list = new ArrayList<>();

    public FoodAdapter(Activity act, int resource, ArrayList<FoodClass> data) {
        super(act, resource, data);
        resLayout = resource;
        thisActivity = act;
        food_list = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return food_list.size();
    }

    @Override
    public FoodClass getItem(int position) {
        return food_list.get(position);
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
        if ( row == null || (row.getTag() == null)) {
            LayoutInflater inflater = LayoutInflater.from(thisActivity);
            row = inflater.inflate(resLayout, null);
            holder = new ViewHolder();
            holder.foodName = row.findViewById(R.id.food_name);
            holder.foodDate = row.findViewById(R.id.food_date);
            holder.foodCalories = row.findViewById(R.id.food_cal);

            row.setTag(holder);

        }else {

            holder = (ViewHolder) row.getTag();
        }

        holder.food = getItem(position);
        holder.foodName.setText(holder.food.getFoodName());
        holder.foodDate.setText(holder.food.getRecordDate());
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(thisActivity, FoodDesc.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalHolder.food);
                i.putExtras(mBundle);
                thisActivity.startActivity(i);
            }
        });

        return row;

    }

    public class ViewHolder {
        FoodClass food;
        TextView foodName;
        TextView foodCalories;
        TextView foodDate;
    }
}
