package com.example.homeautomation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    ToggleButton fanBTN, lightBTN, tubeBTN;
    FirebaseDatabase db;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fanBTN = findViewById(R.id.fanBTN);
        lightBTN = findViewById(R.id.lightBTN);
        tubeBTN = findViewById(R.id.tubeBTN);

        db = FirebaseDatabase.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

         ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                switchesState state= dataSnapshot.getValue(switchesState.class);
                updateSwitch(state);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        dbRef.addValueEventListener(postListener);

        fanBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dbRef.child("fan").setValue(0+"").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else{
                    dbRef.child("fan").setValue(1+"").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

            }
        });

        lightBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dbRef.child("light").setValue(0+"").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else{
                    dbRef.child("light").setValue(1+"").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        tubeBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dbRef.child("tubelight").setValue(0+"").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else{
                    dbRef.child("tubelight").setValue(1+"").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void updateSwitch(switchesState state) {
        Log.i("states",state.fan);
        Log.i("states",state.light);
        Log.i("states",state.tubelight);

        if (state.fan.equals("0")){
            fanBTN.setChecked(true);
        }else{
            fanBTN.setChecked(false);
        }

        if (state.light.equals("0")){
            lightBTN.setChecked(true);
        }else{
            lightBTN.setChecked(false);
        }

        if (state.tubelight.equals("0")){
            tubeBTN.setChecked(true);
        }else{
            tubeBTN.setChecked(false);
        }

    }

    private static class switchesState{
        private String fan;
        private String light;
        private String tubelight;

        switchesState() {}


        public String getFan() {
            return fan;
        }

        public void setFan(String fan) {
            this.fan = fan;
        }

        public String getLight() {
            return light;
        }

        public void setLight(String light) {
            this.light = light;
        }

        public String getTubelight() {
            return tubelight;
        }

        public void setTubelight(String tubelight) {
            this.tubelight = tubelight;
        }
    }
}
