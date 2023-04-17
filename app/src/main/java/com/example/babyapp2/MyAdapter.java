package com.example.babyapp2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    public static String x_babyName;

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
        holder.id.setText(String.valueOf(id.get(position)));
        holder.baby_name_input.setText(String.valueOf(baby_name.get(position)));
        holder.baby_age_input.setText(String.valueOf(baby_age.get(position)));
        holder.baby_gender_input.setText(String.valueOf(baby_gender.get(position)));
        holder.mainProfileListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x_babyName = String.valueOf(baby_name.get(position));
                Toast.makeText(v.getContext(), x_babyName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Dashboard.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(baby_name.get(position)));
                intent.putExtra("age", String.valueOf(baby_age.get(position)));
                intent.putExtra("gender", String.valueOf(baby_gender.get(position)));
                activity.startActivityForResult(intent, 1);

            }
        });

        /*holder.mainProfileListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(baby_name.get(position)));
                intent.putExtra("age", String.valueOf(baby_age.get(position)));
                intent.putExtra("gender", String.valueOf(baby_gender.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id, baby_name_input, baby_age_input, baby_gender_input;
        ConstraintLayout mainProfileListLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            baby_name_input = itemView.findViewById(R.id.baby_name_input);
            baby_age_input = itemView.findViewById(R.id.baby_age_input);
            baby_gender_input = itemView.findViewById(R.id.baby_gender_input);
            mainProfileListLayout = itemView.findViewById(R.id.mainProfileListLayout);


        }
    }
}
