package com.example.jkliew.jk_fms;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

//    float Heater;
//    boolean Valve;
//    boolean Humidifier;

    TextView textVAvgTemp, textVAvgHumid, textVWaterLvl, textVHeater, textVHumidifier, textVValve, editTTemp, editTHumid;

    String HeaterStatus = "OFF";
    String ValveStatus = "OFF";
    String HumidifierStatus = "OFF";
    String AvgTemp, AvgHumid, WaterLvl;
    final boolean[] EStopReady = new boolean[1];

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ActuatorsRef = database.getReference("Actuators");
    DatabaseReference SensorsRef = database.getReference("Sensors");
    DatabaseReference SummaryRef = database.getReference("Summary");
    DatabaseReference TarValuesRef = database.getReference("Target Values");
    DatabaseReference EStopRef = database.getReference("Emergency Stop");
    DatabaseReference RefreshWaterLvlRef = database.getReference("Refresh Control").child("Water Level");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        textVAvgTemp = findViewById(R.id.tvAvgTemp);
        Button buttonAvgTemp = findViewById(R.id.butAvgTemp);
        textVAvgHumid = findViewById(R.id.tvAvgHumid);
        Button buttonAvgHumid = findViewById(R.id.butAvgHumid);
        textVWaterLvl = findViewById(R.id.tvWaterLvl);
        Button buttonWaterLvl = findViewById(R.id.butWaterLvl);
        textVHeater = findViewById(R.id.tvHeater);
        final Button buttonHeater = findViewById(R.id.togbutHeaterIn);
        textVValve = findViewById(R.id.tvValve);
        final Button buttonValve = findViewById(R.id.butValve);
        textVHumidifier = findViewById(R.id.tvHumidifier);
        final Button buttonHumidifier = findViewById(R.id.butHumidifier);
        editTTemp = findViewById(R.id.editTemp);
        Button buttonSetTemp = findViewById(R.id.butSetTemp);
        editTHumid = findViewById(R.id.editHumid);
        Button buttonSetHumid = findViewById(R.id.butSetHumid);
        Button buttonMainErrorLog = findViewById(R.id.butMainErrorLog);
        final Button buttonEStop = findViewById(R.id.butEStop);
        final Button buttonHidden = findViewById(R.id.butHidden);


//        Average Temp code here
        buttonAvgTemp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent TempIntent = new Intent(MainActivity.this, Calculate_Temp.class);
                startActivity(TempIntent);
            }
        });
        SummaryRef.child("Temperature Range").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String humidityStatus = dataSnapshot.getValue(String.class);
                if (humidityStatus.equals("Temperature is too HIGH") || humidityStatus.equals("Temperature is too LOW"))  textVAvgTemp.setTextColor(Color.RED);
                else textVAvgTemp.setTextColor(Color.GRAY);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgHumid.setText("?");
            }
        });
        SummaryRef.child("Average Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AvgTemp = dataSnapshot.getValue(String.class);
                textVAvgTemp.setText(AvgTemp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgTemp.setText("?");
            }
        });

//        Avg Humidity code here
        buttonAvgHumid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HumidIntent = new Intent(MainActivity.this, Calculate_Humid.class);
                startActivity(HumidIntent);
            }
        });
        SummaryRef.child("Humidity Range").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String humidityStatus = dataSnapshot.getValue(String.class);
                if (humidityStatus.equals("Humidity is too HIGH") || humidityStatus.equals("Humidity is too LOW"))  textVAvgHumid.setTextColor(Color.RED);
                else textVAvgHumid.setTextColor(Color.GRAY);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgHumid.setText("?");
            }
        });
        SummaryRef.child("Average Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AvgHumid = dataSnapshot.getValue(String.class);
                textVAvgHumid.setText(AvgHumid);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgHumid.setText("?");
            }
        });

//        Water Level code here
        buttonWaterLvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater liTemp= LayoutInflater.from(MainActivity.this);
                View humidView = liTemp.inflate(R.layout.activity_water__lvl, null);
                final AlertDialog.Builder builderTemp = new AlertDialog.Builder(MainActivity.this);
                builderTemp.setTitle("Water Level");

                builderTemp.setView(humidView);

                final Button buttonWaterLvlRefresh = humidView.findViewById(R.id.butWaterLvlRefresh);
                final Button buttonWaterLvlErrorLog = humidView.findViewById(R.id.butWaterLvlErrorLog);

                final AlertDialog alertDialogTemp = builderTemp.create();
                alertDialogTemp.setCanceledOnTouchOutside(true);
                alertDialogTemp.show();

                buttonWaterLvlRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RefreshWaterLvlRef.setValue("1");
                        alertDialogTemp.cancel();
                    }
                });

                buttonWaterLvlErrorLog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent WaterLvlErrorLogIntent = new Intent(MainActivity.this, WaterLvl_Error_Log.class);
                        startActivity(WaterLvlErrorLogIntent);
                        alertDialogTemp.cancel();
                    }
                });
            }
        });
        RefreshWaterLvlRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")){
                    textVWaterLvl.setTextColor(Color.GRAY);
                    textVWaterLvl.setText("Updating...");
                }
                else{
                    textVWaterLvl.setTextColor(Color.GRAY);
                    textVWaterLvl.setText(WaterLvl);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SummaryRef.child("Water Level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WaterLvl = dataSnapshot.getValue(String.class);
                if (WaterLvl.equals("Low"))  textVWaterLvl.setTextColor(Color.RED);
                else textVWaterLvl.setTextColor(Color.GRAY);
                textVWaterLvl.setText(WaterLvl);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgHumid.setText("?");
            }
        });

//        Heater code here
        buttonHeater.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (EStopReady[0]){
                    if (HeaterStatus.equals("OFF"))  HeaterStatus = "ON";
                    else if (HeaterStatus.equals("ON")) HeaterStatus = "OFF";
                    ActuatorsRef.child("Heater Relay").setValue(HeaterStatus);
                }
            }
        });

        ActuatorsRef.child("Heater Relay").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HeaterStatus = dataSnapshot.getValue(String.class);
                textVHeater.setText(HeaterStatus);
                if (HeaterStatus.equals("OFF")) buttonHeater.setText("TURN ON");
                else if (HeaterStatus.equals("ON")) buttonHeater.setText("TURN OFF");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Valve
        buttonValve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (EStopReady[0]){
                    if (ValveStatus.equals("OFF"))  ValveStatus = "ON";
                    else if (ValveStatus.equals("ON")) ValveStatus = "OFF";
                    ActuatorsRef.child("Valve Relay").setValue(ValveStatus);
                }
            }
        });

        ActuatorsRef.child("Valve Relay").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ValveStatus = dataSnapshot.getValue(String.class);
                textVValve.setText(ValveStatus);
                if (ValveStatus.equals("OFF")) buttonValve.setText("TURN ON");
                else if (ValveStatus.equals("ON")) buttonValve.setText("TURN OFF");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//Humidifier code here
        buttonHumidifier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (EStopReady[0]){
                    if (HumidifierStatus.equals("OFF"))  HumidifierStatus = "ON";
                    else if (HumidifierStatus.equals("ON")) HumidifierStatus = "OFF";
                    ActuatorsRef.child("Humidifier Relay").setValue(HumidifierStatus);
                }
            }
        });

        ActuatorsRef.child("Humidifier Relay").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HumidifierStatus = dataSnapshot.getValue(String.class);
                textVHumidifier.setText(HumidifierStatus);
                if (HumidifierStatus.equals("OFF")) buttonHumidifier.setText("TURN ON");
                else if (HumidifierStatus.equals("ON")) buttonHumidifier.setText("TURN OFF");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Set Temperature code here
        buttonSetTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater liTemp= LayoutInflater.from(MainActivity.this);
                View humidView = liTemp.inflate(R.layout.set_target_temp, null);
                final AlertDialog.Builder builderTemp = new AlertDialog.Builder(MainActivity.this);
                builderTemp.setTitle("Set Target Temperature");
                builderTemp.setMessage("Set the range of temperature here.");
                builderTemp.setView(humidView);

                final EditText inputTempMax = humidView.findViewById(R.id.editTextTempMax);
                final EditText inputTempMin = humidView.findViewById(R.id.editTextTempMin);

                // Set up the buttons
                builderTemp.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        inputTempMax.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                        inputTempMin.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        String maxTemp = inputTempMax.getText().toString();
                        String minTemp = inputTempMin.getText().toString();
                        TarValuesRef.child("Target Temperature").child("Maximum").setValue(maxTemp, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Maximum Target Temperature FAIL", Toast.LENGTH_LONG).show();
//                                else    Toast.makeText(getApplicationContext(), "Update Maximum Target Temperature SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        });
                        TarValuesRef.child("Target Temperature").child("Minimum").setValue(minTemp, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Minimum Target Temperature FAIL", Toast.LENGTH_LONG).show();
//                                else    Toast.makeText(getApplicationContext(), "Update Minimum Target Temperature SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                builderTemp.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialogTemp = builderTemp.create();
                alertDialogTemp.setCanceledOnTouchOutside(true);
                alertDialogTemp.show();
            }
        });

        TarValuesRef.child("Target Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String max = dataSnapshot.child("Maximum").getValue(String.class);
                String min = dataSnapshot.child("Minimum").getValue(String.class);
                String value = min + " - " + max;
                editTTemp.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        Set Humidity code here
        buttonSetHumid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater liHumid= LayoutInflater.from(MainActivity.this);
                View humidView = liHumid.inflate(R.layout.set_target_humid, null);
                final AlertDialog.Builder builderHumid = new AlertDialog.Builder(MainActivity.this);
                builderHumid.setTitle("Set Target Humidity");
                builderHumid.setMessage("Set the range of humidity here.");
                builderHumid.setView(humidView);

                final EditText inputHumidMax = humidView.findViewById(R.id.editTextHumidMax);
                final EditText inputHumidMin = humidView.findViewById(R.id.editTextHumidMin);

                // Set up the buttons
                builderHumid.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maxHumid = inputHumidMax.getText().toString();
                        String minHumid = inputHumidMin.getText().toString();
                        JSONObject humidityValue = new JSONObject();
                        try{
                            humidityValue.put("Maximum", maxHumid);
                            humidityValue.put("Minimum", minHumid);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        TarValuesRef.child("Target Humidity").child("Maximum").setValue(maxHumid, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Maximum Target Humidity FAIL", Toast.LENGTH_LONG).show();
//                                else    Toast.makeText(getApplicationContext(), "Update Maximum Target Humidity SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        });
                        TarValuesRef.child("Target Humidity").child("Minimum").setValue(minHumid, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Minimum Target Humidity FAIL", Toast.LENGTH_LONG).show();
//                                else    Toast.makeText(getApplicationContext(), "Update Minimum Target Humidity SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                builderHumid.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialogHumid = builderHumid.create();
                alertDialogHumid.setCanceledOnTouchOutside(true);
                alertDialogHumid.show();
            }
        });

        TarValuesRef.child("Target Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String max = dataSnapshot.child("Maximum").getValue(String.class);
                String min = dataSnapshot.child("Minimum").getValue(String.class);
                String value = min + " - " + max;
                editTHumid.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        Error Log here
        buttonMainErrorLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainErrorLogIntent = new Intent(MainActivity.this, Main_Error_Log.class);
                startActivity(MainErrorLogIntent);
            }
        });

//        Emergency Stop Button
        final Drawable EStopBackground = buttonHidden.getBackground();
        EStopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String EStopStatus = dataSnapshot.getValue(String.class);
                if (EStopStatus.equals("Stand By")) {
                    buttonEStop.setText("EMERGENCY STOP BUTTON");
                    buttonEStop.setBackground(EStopBackground);
                    EStopReady[0] = true;
//                    buttonEStop.setBackgroundColor(Color.TRANSPARENT);
                }
                else if (EStopStatus.equals("Triggered")){
                    buttonEStop.setText("EMERGENCY STOP BUTTON TRIGGERED");
                    buttonEStop.setBackgroundColor(Color.RED);
                    EStopReady[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonEStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EStopReady[0]) {
                    final AlertDialog.Builder builderTemp = new AlertDialog.Builder(MainActivity.this);
                    builderTemp.setTitle("Emergency Stop");
                    builderTemp.setMessage("STOP the machine?");

                    // Set up the buttons
                    builderTemp.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EStopRef.setValue("Triggered");
                        }
                    });
                    builderTemp.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builderTemp.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialogTemp = builderTemp.create();
                    alertDialogTemp.setCanceledOnTouchOutside(true);
                    alertDialogTemp.show();
                }
                else{
                    final AlertDialog.Builder builderTemp = new AlertDialog.Builder(MainActivity.this);
                    builderTemp.setTitle("Emergency Stop is TRIGGERED!");
                    builderTemp.setMessage("Check the machine for error(s).");

                    // Set up the buttons
                    builderTemp.setNeutralButton ("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialogTemp = builderTemp.create();
                    alertDialogTemp.setCanceledOnTouchOutside(true);
                    alertDialogTemp.show();
                }
            }
        });

    }
}
