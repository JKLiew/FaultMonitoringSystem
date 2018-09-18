package com.example.jkliew.jk_fms;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Calculate_Temp extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference TarTempRef = database.getReference("Target Values").child("Target Temperature");
    DatabaseReference AvgTempRef = database.getReference("Summary").child("Average Temperature");
    DatabaseReference AvgTempRange = database.getReference("Summary").child("Temperature Range");
    DatabaseReference Temp1Ref = database.getReference("Sensors").child("Sensor_1").child("Temperature Latest Value");
    DatabaseReference Temp2Ref = database.getReference("Sensors").child("Sensor_2").child("Temperature Latest Value");
    DatabaseReference Temp3Ref = database.getReference("Sensors").child("Sensor_3").child("Temperature Latest Value");
    DatabaseReference Temp4Ref = database.getReference("Sensors").child("Sensor_4").child("Temperature Latest Value");
    DatabaseReference HeaterRef = database.getReference("Actuators").child("Heater Relay");
    DatabaseReference EStopRef = database.getReference("Emergency Stop");
    DatabaseReference RefreshRef = database.getReference("Refresh Control");

    TextView textVAvgTempIn, textVTemp1, textVTemp2, textVTemp3, textVTemp4, editTTempIn;
    Button  ButtonSetTempIn, ButtonAvgTempIn, ButtonRTemp1, ButtonRTemp2, ButtonRTemp3, ButtonRTemp4, buttonErrorLogTempIn, buttonEStopTempIn;//, buttonHiddenTempIn;
    ToggleButton TogButHeaterIn;
    String TargetTemp, AvgTemp, Temp1, Temp2, Temp3, Temp4, HeaterStatus;
    final boolean[] EStopReady = new boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_calculate__temp);

        editTTempIn = findViewById(R.id.editTempIn);
        textVTemp1 = findViewById(R.id.tvTemp1);
        textVTemp2 = findViewById(R.id.tvTemp2);
        textVTemp3 = findViewById(R.id.tvTemp3);
        textVTemp4 = findViewById(R.id.tvTemp4);
        textVAvgTempIn = findViewById(R.id.tvRAvgTempIn);

        ButtonRTemp1 = findViewById(R.id.butRTemp1);
        ButtonRTemp2 = findViewById(R.id.butRTemp2);
        ButtonRTemp3 = findViewById(R.id.butRTemp3);
        ButtonRTemp4 = findViewById(R.id.butRTemp4);
        ButtonAvgTempIn = findViewById(R.id.butRAvgTempIn);
        ButtonSetTempIn = findViewById(R.id.butSetTempIn);
        TogButHeaterIn = findViewById(R.id.togbutHeaterIn);
        buttonErrorLogTempIn = findViewById(R.id.butErrorLogTempIn);
        buttonEStopTempIn = findViewById(R.id.butEStopTempIn);
//        buttonHiddenTempIn.findViewById(R.id.butHiddenTempIn);

//        ButtonTemp1 code here
        ButtonRTemp1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_1").setValue("1");
            }
        });
        Temp1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Temp1= dataSnapshot.getValue(String.class);
                textVTemp1.setText(Temp1);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVTemp1.setText("?");
            }
        });
        RefreshRef.child("Sensor_1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVTemp1.setText("Refreshing...");
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

//        ButtonRTemp2 code here
        ButtonRTemp2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_2").setValue("1");
            }
        });
        Temp2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Temp2= dataSnapshot.getValue(String.class);
                textVTemp2.setText(Temp2);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVTemp2.setText("?");
            }
        });
        RefreshRef.child("Sensor_2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVTemp2.setText("Refreshing...");
//                else{
//                    Temp2Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp2.setText(temp);
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

//        ButtonRTemp3 code here
        ButtonRTemp3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_3").setValue("1");
            }
        });
        Temp3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Temp3= dataSnapshot.getValue(String.class);
                textVTemp3.setText(Temp3);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVTemp3.setText("?");
            }
        });
        RefreshRef.child("Sensor_3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVTemp3.setText("Refreshing...");
//                else{
//                    Temp3Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp3.setText(temp);
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

//        ButtonRTemp4 code here
        ButtonRTemp4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("Sensor_4").setValue("1");
            }
        });
        Temp4Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Temp4= dataSnapshot.getValue(String.class);
                textVTemp4.setText(Temp4);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVTemp4.setText("?");
            }
        });
        RefreshRef.child("Sensor_4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVTemp4.setText("Refreshing...");
//                else{
//                    Temp4Ref.getParent().child("Temperature").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String temp = dataSnapshot.getValue(String.class);
//                            textVTemp4.setText(temp);
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

//        Average Temp code here
        ButtonAvgTempIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RefreshRef.child("All Sensor").setValue("1");
            }
        });
        AvgTempRange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AvgTemp = dataSnapshot.getValue(String.class);
                if (AvgTemp.equals("Temperature is too HIGH") || AvgTemp.equals("Temperature is too LOW"))  textVAvgTempIn.setTextColor(Color.RED);
                else textVAvgTempIn.setTextColor(Color.GRAY);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AvgTempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AvgTemp = dataSnapshot.getValue(String.class);
                textVAvgTempIn.setText(AvgTemp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                textVAvgTempIn.setText("?");
            }
        });
        RefreshRef.child("All Sensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.getValue(String.class);
                if (flag.equals("1"))   textVAvgTempIn.setText("Refreshing...");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//         Error Log for Temperature
        buttonErrorLogTempIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Temp_Error_Log = new Intent(Calculate_Temp.this, Temp_Error_Log.class);
                startActivity(Temp_Error_Log);
            }
        });

        final Drawable EStopBackground = buttonErrorLogTempIn.getBackground();
        EStopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String EStopStatus = dataSnapshot.getValue(String.class);
                if (EStopStatus.equals("Stand By")) {
                    buttonEStopTempIn.setText("EMERGENCY STOP BUTTON");
                    buttonEStopTempIn.setBackground(EStopBackground);
                    EStopReady[0] = true;
//                    buttonEStop.setBackgroundColor(Color.TRANSPARENT);
                }
                else if (EStopStatus.equals("Triggered")){
                    buttonEStopTempIn.setText("EMERGENCY STOP BUTTON TRIGGERED");
                    buttonEStopTempIn.setBackgroundColor(Color.RED);
                    EStopReady[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        For switching heater on or off
        TogButHeaterIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (EStopReady[0]){
                    if (isChecked) {
                        HeaterRef.setValue("ON");
                        TogButHeaterIn.setTextOn("Heater ON");
                    }
                    else  {
                        HeaterRef.setValue("OFF");
                        TogButHeaterIn.setTextOn("Heater OFF");
                    }
                }
                else{
                    TogButHeaterIn.setTextOff("Heater N.A.");
                    TogButHeaterIn.setTextOn("Heater N.A.");
                }
            }
        });
        HeaterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (EStopReady[0]){
                    HeaterStatus = dataSnapshot.getValue(String.class);
                    if (HeaterStatus.equals("OFF")){
                        TogButHeaterIn.setChecked(false);
                        TogButHeaterIn.setTextOn("Heater ON");
                    }
                    else if (HeaterStatus.equals("ON")) {
                        TogButHeaterIn.setChecked(true);
                        TogButHeaterIn.setTextOn("Heater OFF");
                    }
                }
                else{
                    TogButHeaterIn.setTextOff("Heater N.A.");
                    TogButHeaterIn.setTextOn("Heater N.A.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Set target temperature range
        ButtonSetTempIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater liTemp= LayoutInflater.from(Calculate_Temp.this);
                View humidView = liTemp.inflate(R.layout.set_target_temp, null);
                final AlertDialog.Builder builderTemp = new AlertDialog.Builder(Calculate_Temp.this);
                builderTemp.setTitle("Set Target Humidity");
                builderTemp.setMessage("Set the range of humidity here.");
                builderTemp.setView(humidView);

                final EditText inputTempMax = humidView.findViewById(R.id.editTextTempMax);
                final EditText inputTempMin = humidView.findViewById(R.id.editTextTempMin);

                // Set up the buttons
                builderTemp.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maxTemp = inputTempMax.getText().toString();
                        String minTemp = inputTempMin.getText().toString();
                        TarTempRef.child("Maximum").setValue(maxTemp, new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference firebase) {
//                                if (databaseError != null)  Toast.makeText(getApplicationContext(), "Update Maximum Target Temperature FAIL", Toast.LENGTH_LONG).show();
//                                else    Toast.makeText(getApplicationContext(), "Update Maximum Target Temperature SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        });
                        TarTempRef.child("Minimum").setValue(minTemp, new DatabaseReference.CompletionListener(){
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

        TarTempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String max = dataSnapshot.child("Maximum").getValue(String.class);
                String min = dataSnapshot.child("Minimum").getValue(String.class);
                String value = min + " - " + max;
                editTTempIn.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


//        Emergency Stop Button
        buttonEStopTempIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EStopReady[0]) {
                    final AlertDialog.Builder builderTemp = new AlertDialog.Builder(Calculate_Temp.this);
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
                    final AlertDialog.Builder builderTemp = new AlertDialog.Builder(Calculate_Temp.this);
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
