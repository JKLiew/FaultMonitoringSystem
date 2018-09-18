package com.example.jkliew.jk_fms;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Temp_Error_Log extends AppCompatActivity {

    public class FirebaseHelper {
        DatabaseReference db;
        ArrayList<Error> errors = new ArrayList<>();
        ListView mListView;
        Context c;

        /*
       let's receive a reference to our FirebaseDatabase
       */
        public FirebaseHelper(DatabaseReference db, Context context, ListView mListView) {
            this.db = db;
            this.c = context;
            this.mListView = mListView;
            this.retrieve();
        }

        /*
        Retrieve and Return them clean data in an arraylist so that they just bind it to ListView.
         */
        public ArrayList<Error> retrieve() {
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    errors.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Error error = ds.getValue(Error.class);
                            String param = error.getParameter();
                            if (param.equals("Temperature")){
                                errors.add(error);
                            }
                        }
                        adapter = new CustomAdapter(c, errors);
                        mListView.setAdapter(adapter);

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(errors.size());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("mTAG", databaseError.getMessage());
                    Toast.makeText(c, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            return errors;
        }

    }

    /**********************************CUSTOM ADAPTER START************************/
    class CustomAdapter extends BaseAdapter {
        Context c;
        ArrayList<Error> errors;

        public CustomAdapter(Context c, ArrayList<Error> errors) {
            this.c = c;
            this.errors = errors;
        }

        @Override
        public int getCount() {
            return errors.size();
        }

        @Override
        public Object getItem(int position) {
            return errors.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.list_temp_error_log, parent, false);
            }

            TextView textVTempParam = convertView.findViewById(R.id.tvTempParamList);
            TextView textVTempResultsList = convertView.findViewById(R.id.tvTempResultsList);
            TextView textVTempTarParamList = convertView.findViewById(R.id.tvTempTarParamList);
            TextView textVTempAvgParamList = convertView.findViewById(R.id.tvTempAvgParamList);
            TextView textVTempDateList = convertView.findViewById(R.id.tvTempDateList);
            TextView textVTempTimeList = convertView.findViewById(R.id.tvTempTimeList);

            final Error s = (Error) this.getItem(position);

            textVTempParam.setText(s.getParameter());
            textVTempResultsList.setText(s.getResult());
            textVTempTarParamList.setText(s.getTarget_Parameter());
            textVTempAvgParamList.setText(s.getAverage_Parameter());
            textVTempDateList.setText(s.getDate());
            textVTempTimeList.setText(s.getTime());

            //ONITECLICK
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, s.getResult(), Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }

    /**********************************MAIN ACTIVITY CONTINUATION************************/
    //instance fields
    FirebaseHelper helper;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp__error__log);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Error = database.getReference("Error");

        ListView tempListView = findViewById(R.id.lvTempErrorLog);
        helper = new Temp_Error_Log.FirebaseHelper(Error, this, tempListView);

        helper.retrieve();
    }
}