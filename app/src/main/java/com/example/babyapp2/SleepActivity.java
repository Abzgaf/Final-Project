package com.example.babyapp2;

// SleepActivity class with its main purpose to track the user's sleep time and display it on a LineChart.

// The class initializes TextViews (time1 and time2), Buttons (start and stop), a LineChart (chart1), and other variables needed for the chart.
// In the onCreate() method, the activity sets the layout, finds the views by their IDs, and initializes the chart settings, such as touch and drag interactions.

// The start button's onClick() method is defined to add the current timestamp to the entries list, disable the start button, and enable the stop button.
// It also sets the time1 TextView to display the current time.

// The stop button's onClick() method is defined to add the current timestamp to the entries list, disable the stop button, enable the start button,
// and increment the day variable. It then calls the updateChart() method to update the chart with the new data.
// The time2 TextView is set to display the current time, and the sleep data is added to an SQL database.

// The X-axis of the chart is set up with labels for each day of the week using the getAreaCount() method.

// The updateChart() method creates a LineDataSet with the sleep tracker data, sets the chart data, and invalidates the chart to redraw it with the updated data.


import static com.example.babyapp2.MyAdapter.x_child_id;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SleepActivity extends AppCompatActivity {

    public TextView time1;
    public TextView time2;
    public Button start;
    public Button stop;
    private int day = 0;

    long startTime = 0;
    long stopTime = 0;

    public LineChart chart1;
    private List<Entry> entries;
    private LineDataSet dataSet;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        chart1 = findViewById(R.id.chart1);

        chart1.setTouchEnabled(true);
        chart1.setDragEnabled(true);
        chart1.setScaleEnabled(true);
        chart1.setPinchZoom(true);
        entries = new ArrayList<>();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entries.add(new Entry(day, System.currentTimeMillis()));
                stop.setEnabled(true);
                start.setEnabled(false);

                final Date d = new Date();
                SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
                final String currentDateTimeString = sdf1.format(d);
                time1.setText(currentDateTimeString);
                startTime = System.currentTimeMillis();

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entries.add(new Entry(day, System.currentTimeMillis()));
                stop.setEnabled(false);
                start.setEnabled(true);
                day++;
                updateChart();

                final Date d1= new Date();
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                final String currentDateTimeString1 = sdf2.format(d1);
                time2.setText(currentDateTimeString1);

                SQLdb db = new SQLdb(SleepActivity.this);
                try {
                    db.addSleepTrackerData(sdf2.parse(time1.getText().toString().trim()),
                            sdf2.parse(time2.getText().toString().trim()), x_child_id);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long difference = (stopTime - startTime)/1000;
                entries.add(new Entry(entries.size(), difference));
                stopTime = System.currentTimeMillis();
                //populateChart();
                updateChart();


            }
        });

        XAxis xAxis = chart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount()));

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    public ArrayList<String> getAreaCount() {
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");

        return xAxisLabel ;
    }

    private void updateChart() {
        LineDataSet dataSet = new LineDataSet(entries, "Sleep Tracker");
        LineData lineData = new LineData(dataSet);
        chart1.setData(lineData);
        chart1.invalidate();

    }

    /*private void populateChart() {
        // Calculate difference between start and stop times
        long difference = (stopTime - startTime)/1000;

        // Create data set with difference as y-axis values and time as x-axis values
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(startTime, difference));
        entries.add(new Entry(stopTime, 0));
        LineDataSet dataSet = new LineDataSet(entries, "Sleep Duration");
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);

        // Add data set to chart
        LineData lineData = new LineData(dataSet);
        chart1.setData(lineData);
        chart1.invalidate();
    }*/





     


}