package com.example.babyapp2;
import static com.example.babyapp2.MyAdapter.x_babyName;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SleepActivity extends AppCompatActivity {

    public TextView time1;
    public TextView time2;
    public TextView diff;
    public Button start;
    public Button stop;
    public Button diffBtn;

    private BarChart mChart;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        diff = findViewById(R.id.diff);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);



        diffBtn = findViewById(R.id.diffBtn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Date d = new Date();
                SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
                final String currentDateTimeString = sdf1.format(d);
                time1.setText(currentDateTimeString);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date d1= new Date();
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                final String currentDateTimeString1 = sdf2.format(d1);
                time2.setText(currentDateTimeString1);

                SQLdb db = new SQLdb(SleepActivity.this);
                try {
                    db.addSleepTrackerData(sdf2.parse(time1.getText().toString().trim()),
                            sdf2.parse(time2.getText().toString().trim()), x_babyName);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        diffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = simpleDateFormat.parse(time1.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2 = null;
                try {
                    date2 = simpleDateFormat.parse(time2.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String diffResult= DateUtils.getRelativeTimeSpanString(date1.getTime(), date2.getTime(), DateUtils.FORMAT_SHOW_TIME).toString();
                diff.setText(diffResult);
            }
        });


    }



     


}