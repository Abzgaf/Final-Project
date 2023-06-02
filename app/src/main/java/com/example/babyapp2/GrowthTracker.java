package com.example.babyapp2;

import static com.example.babyapp2.MyAdapter.x_child_id;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class GrowthTracker extends AppCompatActivity {

    public LineChart chart;
    public EditText height;
    public EditText weight;
    SQLdb db;

    // Constants for the WHO growth standards
    private static final double L = 1.0378;
    private static final double M = 16.244;
    private static final double S = 0.0914;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_tracker);

        chart = findViewById(R.id.chart);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);

        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        chart.setData(data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    public void onAddEntryButtonClick(View view) {

        SQLdb db = new SQLdb(GrowthTracker.this);
        db.addGrowthTrackerData(Integer.valueOf(height.getText().toString().trim()),Integer.valueOf(weight.getText().toString().trim()),x_child_id);

        String heightString = height.getText().toString();
        String weightString = weight.getText().toString();



        if (TextUtils.isEmpty(heightString) || TextUtils.isEmpty(weightString)) {
            Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!heightString.isEmpty() && !weightString.isEmpty()){
        float height = Float.parseFloat(heightString);
        float weight = Float.parseFloat(weightString);


        LineData data = chart.getData();

        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), weight), 0);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Baby's Weight");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(Color.RED);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawValues(false);
        return set;
    }

    public static double calculateZScore(double measurement, double ageInMonths, double sex) {
        // Calculate the expected measurement for the given age and sex
        double expectedMeasurement = calculateExpectedMeasurement(ageInMonths, sex);

        // Calculate the standard deviation for the given age and sex
        double standardDeviation = calculateStandardDeviation(ageInMonths, sex);

        // Calculate the z-score using the formula (measurement - expectedMeasurement) / standardDeviation
        return (measurement - expectedMeasurement) / standardDeviation;
    }

    // Calculate the expected measurement for a given age and sex
    private static double calculateExpectedMeasurement(double ageInMonths, double sex) {
        return L * Math.pow(M, 1 - L) * Math.pow(ageInMonths, L) * Math.exp(S * calculateZ0(ageInMonths, sex));
    }

    // Calculate the standard deviation for a given age and sex
    private static double calculateStandardDeviation(double ageInMonths, double sex) {
        return M * Math.exp(S * calculateZ0(ageInMonths, sex));
    }

    // Calculate the Z0 value for a given age and sex
    private static double calculateZ0(double ageInMonths, double sex) {
        double sexFactor = (sex == 1) ? 0 : 1;
        return (Math.log(ageInMonths) - Math.log(M) - sexFactor * S) / L;
    }
}