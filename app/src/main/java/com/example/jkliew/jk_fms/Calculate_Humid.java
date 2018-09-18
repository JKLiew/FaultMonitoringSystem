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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class Calculate_Humid extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference TarHumidRef = database.getReference("Target Values").child("Target Humidity");
    DatabaseReference AvgHumidRef = database.getReference("Summary").child("Average Humidity");
    DatabaseReference AvgHumidRange = database.getReference("Summary").child("Humidity Range");
    DatabaseReference ActuatorsRef = database.getReference("Actuators");
    DatabaseReference Humid1Ref = database.getReference("Sensors").child("Sensor_1").child("Humidity Latest Value");
    DatabaseReference Humid2Ref = database.getReference("Sensors").child("Sensor_2").child("Humidity Latest Value");
    DatabaseReference Humid3Ref = database.getReference("Sensors").child("Sensor_3").child("Humidity Latest Value");
    DatabaseReference Humid4Ref = database.getReference("Sensors").child("Sensor_4").child("Humidity Latest Value");
    DatabaseReference HumidifierRef = database.getReference("Actuators").child("Humidifier Relay");
    DatabaseReference ValveRef = database.getReference("Actuators").child("Valve Relay");
    DatabaseReference EStopRef = database.getReference("Emergency Stop");
    DatabaseReference RefreshRef = database.getReference("Refresh Control");

    TextView textVAvgHumidIn, textVHumid1, textVHumid2, textVHumid3, textVHumid4, editTHumidIn;
    Button  ButtonSetHumidIn, ButtonAvgHumidIn, ButtonRHumid1, ButtonRHumid2, ButtonRHumid3, ButtonRHumid4, buttonErrorLogHumidIn, buttonEStopHumidIn;//, buttonHiddenHumidIn;
    ToggleButton TogButHumidifierIn, buttonSetValve;
    String TargetHumid, AvgHumid, Humid1, Humid2, Humid3, Humid4, HumidifierStatus, ValveStatus;
    String valveString = "Valve:";
    final boolean[] EStopReady = new boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_calculate__humid);

        editTHumidIn = findViewById(R.id.editHumidIn);
        textVHumid1 = findViewById(R.id.tvHumid1);
        textVHumid2 = findViewById(R.id.tvHumid2);
        textVHumid3 = findViewById(R.id.tvHumid3);
        textVHumid4 = findViewById(R.id.tvHumid4);
        textVAvgHumidIn = findViewById(R.id.tvRAvgHumidIn);

        ButtonRHumid1 = findViewById(R.id.butRHumid1);
        ButtonRHumid2 = findViewById(R.id.butRHumid2);
        ButtonRHumid3 = findViewById(R.id.butRHumid3);
        ButtonRHumid4 = findViewById(R.id.butRHumid4);
        ButtonAvgHumidIn = findViewById(R.id.butRAvgHumidIn);
        ButtonSetHumidIn = findViewById(R.id.butSetHumidIn);
        TogButHumidifierIn = findViewById(R.id.togbutHumidifierIn);
        buttonSetValve = findViewById(R.id.butSetValve);
        buttonErrorLogHumidIn = findViewById(R.id.butErrorLogHumidIn);
        buttonEStopHumidIn = findViewById(R.id.butEStopHumidIn);
//        buttonHiddenHumidIn.findViewById(R.id.butHiddenHumidIn);

//        ButtonHumid1 code here
        ButtonRHumid1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_1").setValue("1");
            }
        });
        Humid1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Humid1= dataSnapshot.getValue(String.class);
                textVHumid1.setText(Humid1);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVHumid1.setText("?");
            }
        });
        RefreshRef.child("Sensor_1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVHumid1.setText("Refreshing...");
//                else{
//                    Temp1Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp1.setText(temp);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ButtonRHumid2 code here
        ButtonRHumid2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_2").setValue("1");
            }
        });
        Humid2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Humid2= dataSnapshot.getValue(String.class);
                textVHumid2.setText(Humid2);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVHumid2.setText("?");
            }
        });
        RefreshRef.child("Sensor_2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVHumid2.setText("Refreshing...");
//                else{
//                    Temp1Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp1.setText(temp);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ButtonRHumid3 code here
        ButtonRHumid3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_3").setValue("1");
            }
        });
        Humid3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Humid3= dataSnapshot.getValue(String.class);
                textVHumid3.setText(Humid3);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVHumid3.setText("?");
            }
        });
        RefreshRef.child("Sensor_3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVHumid3.setText("Refreshing...");
//                else{
//                    Temp1Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp1.setText(temp);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ButtonRHumid4 code here
        ButtonRHumid4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_4").setValue("1");
            }
        });
        Humid4Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Humid4= dataSnapshot.getValue(String.class);
                textVHumid4.setText(Humid4);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVHumid4.setText("?");
            }
        });
        RefreshRef.child("Sensor_4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVHumid4.setText("Refreshing...");
//                else{
//                    Temp1Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp1.setText(temp);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Average Humid code here
        ButtonAvgHumidIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("All Sensor").setValue("1");
            }
        });
        AvgHumidRange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AvgHumid = dataSnapshot.getValue(String.class);
                if (AvgHumid.equals("Humidity is too HIGH") || AvgHumid.equals("Humidity is too LOW"))  textVAvgHumidIn.setTextColor(Color.RED);
                else textVAvgHumidIn.setTextColor(Color.GRAY);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AvgHumidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AvgHumid = dataSnapshot.getValue(String.class);
                textVAvgHumidIn.setText(AvgHumid);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgHumidIn.setText("?");
            }
        });
        RefreshRef.child("All Sensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVAvgHumidIn.setText("Refreshing...");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//         Error Log for Humidity
        buttonErrorLogHumidIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Humid_Error_Log = new Intent(Calculate_Humid.this, Humid_Error_Log.class);
                startActivity(Humid_Error_Log);
            }
        });

        final Drawable EStopBackground = buttonErrorLogHumidIn.getBackground();
        EStopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String EStopStatus = dataSnapshot.getValue(String.class);
                if (EStopStatus.equals("Stand By")) {
                    buttonEStopHumidIn.setText("EMERGENCY STOP BUTTON");
                    buttonEStopHumidIn.setBackground(EStopBackground);
                    EStopReady[0] = true;
//                    buttonEStop.setBackgroundColor(Color.TRANSPARENT);
                }
                else if (EStopStatus.equals("Triggered")){
                    buttonEStopHumidIn.setText("EMERGENCY STOP BUTTON TRIGGERED");
                    buttonEStopHumidIn.setBackgroundColor(Color.RED);
                    EStopReady[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        For switching Humidifier on or off
        TogButHumidifierIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (EStopReady[0]){
                    if (isChecked){
                        HumidifierRef.setValue("ON");
                        TogButHumidifierIn.setTextOn("Humidifier ON");
                    }
                    else{
                        HumidifierRef.setValue("OFF");
                        TogButHumidifierIn.setTextOff("Humidifier OFF");
                    }
                }
                else{
                    TogButHumidifierIn.setTextOn("Humidifier N.A.");
                    TogButHumidifierIn.setTextOff("Humidifier N.A.");
                }
            }
        });

        HumidifierRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (EStopReady[0]){
                    HumidifierStatus = dataSnapshot.getValue(String.class);
                    if (HumidifierStatus.equals("OFF")){
                        TogButHumidifierIn.setChecked(false);
                        TogButHumidifierIn.setTextOff("Humidifier OFF");
                    }
                    else if (HumidifierStatus.equals("ON")){
                        TogButHumidifierIn.setChecked(true);
                        TogButHumidifierIn.setTextOn("Humidifier ON");
                    }
                }
                else {
                    TogButHumidifierIn.setTextOn("Humidifier N.A.");
                    TogButHumidifierIn.setTextOff("Humidifier N.A.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Set target Humidity range
        ButtonSetHumidIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater liHumid= LayoutInflater.from(Calculate_Humid.this);
                View humidView = liHumid.inflate(R.layout.set_target_humid, null);
                final AlertDialog.Builder builderHumid = new AlertDialog.Builder(Calculate_Humid.this);
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
                        TarHumidRef.child("Maximum").setValue(maxHumid, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Maximum Target Humidity FAIL", Toast.LENGTH_LONG).show();
//                                else    Toast.makeText(getApplicationContext(), "Update Maximum Target Humidity SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        });
                        TarHumidRef.child("Minimum").setValue(minHumid, new DatabaseReference.CompletionListener(){
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

        TarHumidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String max = dataSnapshot.child("Maximum").getValue(String.class);
                String min = dataSnapshot.child("Minimum").getValue(String.class);
                String value = min + " - " + max;
                editTHumidIn.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        Set valve value
        buttonSetValve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (EStopReady[0]){
                    if (isChecked)  {
                        ValveRef.setValue("ON");
                        buttonSetValve.setTextOn("Valve ON");
                    }
                    else  {
                        ValveRef.setValue("OFF");
                        buttonSetValve.setTextOff("Valve OFF");
                    }
                }
                else{
                    buttonSetValve.setTextOn("Valve N.A.");
                    buttonSetValve.setTextOff("Valve N.A.");
                }
            }
        });

            ValveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (EStopReady[0]){
                    ValveStatus = dataSnapshot.getValue(String.class);
                    if (ValveStatus.equals("OFF")) buttonSetValve.setChecked(false);
                    else if (ValveStatus.equals("ON")) buttonSetValve.setChecked(true);
                }
            }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//        Emergency Stop Button
        buttonEStopHumidIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EStopReady[0]) {
                    final AlertDialog.Builder builderTemp = new AlertDialog.Builder(Calculate_Humid.this);
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
                    final AlertDialog.Builder builderTemp = new AlertDialog.Builder(Calculate_Humid.this);
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

//    Set valve value
//        buttonSetValve.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                LayoutInflater liValve= LayoutInflater.from(Calculate_Humid.this);
//                View valveView = liValve.inflate(R.layout.set_valve, null);
//                final AlertDialog.Builder builderValve = new AlertDialog.Builder(Calculate_Humid.this);
//                builderValve.setTitle("Set Valve");
//                builderValve.setMessage("Set the valve in percentage(%).");
//                builderValve.setView(valveView);
//
//                final EditText inputValve = valveView.findViewById(R.id.editValve);
//
//                // Set up the buttons
//                builderValve.setPositiveButton("SET", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        inputHumidMax.onEditorAction(EditorInfo.IME_ACTION_DONE);
////                        inputHumidMin.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                        String valveValue = inputValve.getText().toString();
//                        ActuatorsRef.child("Valve Relay").setValue(valveValue, new DatabaseReference.CompletionListener(){
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
////                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Maximum Target Humidity FAIL", Toast.LENGTH_LONG).show();
////                                else    Toast.makeText(getApplicationContext(), "Update Maximum Target Humidity SUCCESS", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                });
//                builderValve.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                AlertDialog alertDialogValve = builderValve.create();
//                alertDialogValve.setCanceledOnTouchOutside(true);
//                alertDialogValve.show();
//
//            }
//        });
//
//        ActuatorsRef.child("Valve Relay").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ValveStatus = dataSnapshot.getValue(String.class);
//                String disValveString = valveString + ValveStatus + "%";
//                buttonSetValve.setText(disValveString);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });