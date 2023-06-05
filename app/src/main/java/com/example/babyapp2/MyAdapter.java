package com.example.babyapp2;

// MyAdapter RecyclerView class to connect baby information.

// The adapter takes in four arrays as parameters (id, baby_name, baby_age, and baby_gender) and uses them to populate a RecyclerView with information about babies.
// The adapter inflates a layout called profile_display for each item in the RecyclerView and assigns values from the arrays to TextViews and an ImageView in the layout.

// The adapter also sets up click listeners for the items in the RecyclerView.
// When the user clicks on the menu_dropdown ImageView, the adapter launches an UpdateActivity with information about the selected baby.

// When the user clicks on the mainProfileListLayout, the adapter launches a Dashboard activity with information about the selected baby.
// The adapter includes an inner class called MyViewHolder, which defines views for each item in the RecyclerView and sets up animations for them.

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList id;
    private ArrayList baby_name;
    private ArrayList baby_age;
    private ArrayList baby_gender;
    Activity activity;
    Animation animation;


    public static String x_child_id;

    MyAdapter(Activity activity, Context context,ArrayList id,ArrayList baby_name, ArrayList baby_age, ArrayList baby_gender){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.baby_name = baby_name;
        this.baby_age = baby_age;
        this.baby_gender = baby_gender;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_display, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.baby_name_input.setText(String.valueOf(baby_name.get(position)));
        holder.baby_age_input.setText(String.valueOf(baby_age.get(position)));
        holder.baby_gender_input.setText(String.valueOf(baby_gender.get(position)));
        holder.menu_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(baby_name.get(position)));
                intent.putExtra("age", String.valueOf(baby_age.get(position)));
                intent.putExtra("gender", String.valueOf(baby_gender.get(position)));
                activity.startActivityForResult(intent, 1);

            }
        });
        holder.mainProfileListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x_child_id = String.valueOf(id.get(position));
                Intent intent = new Intent(context, Dashboard.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(baby_name.get(position)));
                intent.putExtra("age", String.valueOf(baby_age.get(position)));
                intent.putExtra("gender", String.valueOf(baby_gender.get(position)));
                activity.startActivityForResult(intent, 1);


            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView baby_name_input, baby_age_input, baby_gender_input;
        ImageView menu_dropdown;
        ConstraintLayout mainProfileListLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            baby_name_input = itemView.findViewById(R.id.baby_name_input);
            baby_age_input = itemView.findViewById(R.id.baby_age_input);
            baby_gender_input = itemView.findViewById(R.id.baby_gender_input);
            menu_dropdown = itemView.findViewById(R.id.menu_dropdown);
            mainProfileListLayout = itemView.findViewById(R.id.mainProfileListLayout);
            animation = AnimationUtils.loadAnimation(context, R.anim.animation);
            mainProfileListLayout.setAnimation(animation);

        }
    }
}
