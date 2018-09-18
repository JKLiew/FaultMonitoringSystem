package com.example.jkliew.jk_fms;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class plot_Temp_Graph extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference TarTempRef = database.getReference("Target Values").child("Target Temperature");
    DatabaseReference AvgTempRef = database.getReference("Summary").child("Average Temperature");
    DatabaseReference Temp1Ref = database.getReference("Sensors").child("Sensor_1").child("Temperature");
    DatabaseReference Temp2Ref = database.getReference("Sensors").child("Sensor_2").child("Temperature");
    DatabaseReference Temp3Ref = database.getReference("Sensors").child("Sensor_3").child("Temperature");
    DatabaseReference Temp4Ref = database.getReference("Sensors").child("Sensor_4").child("Temperature");
    DatabaseReference HeaterRef = database.getReference("Actuators").child("Heater Relay");
    DatabaseReference EStopRef = database.getReference("Emergency Stop");

    private PointsGraphSeries<DataPoint> series, series1, series2, series3, series4, seriesAvg;
    private int lastX = 0;

    private int graphSize = 3;
    Date[] x = new Date[graphSize];
    double[] y = new double[graphSize];

    GraphView TempGraph;
    Button setTTempG;
    ToggleButton TogButHeaterG;
    EditText editTTempG;
    Switch STemp1, STemp2, STemp3, STemp4, SAvgTemp;
    String TargetTemp, AvgTemp,HeaterStatus;
    HashMap<String, String> Sensor1, Sensor2, Sensor3, Sensor4;

    Handler handler;
    Runnable runner4;
    double graph2LastXValue = 5d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        setContentView(R.layout.activity_plot__temp__graph);

        TogButHeaterG = findViewById(R.id.togbutHeaterG);
        setTTempG = findViewById(R.id.butSetTempG);
        editTTempG = findViewById(R.id.editTempG);
        TempGraph = findViewById(R.id.gvTempGraph);
        STemp1 = findViewById(R.id.switch1);
        STemp2 = findViewById(R.id.switch2);
        STemp3 = findViewById(R.id.switch3);
        STemp4 = findViewById(R.id.switch4);
        SAvgTemp = findViewById(R.id.switch5);

        Temp4Ref.limitToLast(graphSize).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                series4.resetData(data());
                int counter = 0;
                final Date HAHA;
                final Double HEHE;
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Sensor4 = (HashMap<String, String>) dataSnapshot.getValue();
                    HAHA = convertTime(Long.parseLong(Sensor4.get("Timestamp")));
                    HEHE = Double.parseDouble(Sensor4.get("Value"));
//                }

                runner4 = new Runnable() {
                    @Override
                    public void run() {
                        graph2LastXValue += 1d;
                        series4.appendData(new DataPoint(graph2LastXValue, getRandom()), true, graphSize);
                    }
                };
                handler.post(runner4);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                }
        });

//        For toggle button
//        STemp4.setChecked(false);
//        STemp4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)  TempGraph.addSeries(series4);
//                else    TempGraph.removeSeries(series4);
//            }
//        });

//        Graph property
        series4 = new PointsGraphSeries<DataPoint>();
        TempGraph.addSeries(series4);
        Viewport viewport = TempGraph.getViewport();
        viewport.setYAxisBoundsManual(true);
//        viewport.setMinY(0);
//        viewport.setMaxY(100);
        TempGraph.getViewport().setScalable(true);
        TempGraph.getViewport().setScalableY(true);
        TempGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        TempGraph.getGridLabelRenderer().setHumanRounding(false);

//        Prepare for changes on Sensor 4
        Temp4Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Sensor4 = (HashMap<String, String>) dataSnapshot.getValue();
//                String haha = (Sensor4.get("Value"));
//                editTTempG.setText(haha.toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


//        Toggle button and set target temperature code
        TogButHeaterG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) HeaterRef.setValue("ON");
                else HeaterRef.setValue("OFF");
            }
        });

        HeaterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HeaterStatus = dataSnapshot.getValue(String.class);
                if (HeaterStatus.equals("OFF")) TogButHeaterG.setChecked(false);
                else if (HeaterStatus.equals("ON")) TogButHeaterG.setChecked(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        setTTempG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Editable data = editTTempG.getText();
//                editTTempG.setCursorVisible(false);
//                TarTempRef.setValue(data.toString(), new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                        if (databaseError != null)
//                            Toast.makeText(getApplicationContext(), "Update Target Temperature FAIL", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(getApplicationContext(), "Update Target Temperature SUCCESS", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });

//        editTTempG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editTTempG.setCursorVisible(true);
//            }
//        });
//
//        TarTempRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                editTTempG.setText(value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runner4);
        super.onPause();
    }

    private DataPoint[] data() {
        DataPoint[] values = new DataPoint[graphSize];
        for(int i=0; i<graphSize; i++){
            DataPoint v = new DataPoint(x[i],y[i]);
            values[i] = v;
        }
        return values;
    }

    Date convertTime(Long unixtime) {
        Date dateObject = new Date(unixtime);
        return dateObject;
    }

    public double getRandom() {
        Random random = new Random();
        return random.nextDouble();
    }
}