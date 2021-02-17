package com.hno2.speechshowing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        TextView result = (TextView)findViewById(R.id.trans);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        //TextView result = (TextView)findViewById(R.id.trans);
                        result.setText("Listening...\n");
                        Map<String, Object> data = new HashMap<>();
                        data.put("transcription_result", " ");

                    db.collection("data").document("result").set(data);

                        final DocumentReference docRef = db.collection("data").document("result");
                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    result.setText("Failed");
                                    return;
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    result.setText(result.getText() + "\n" + snapshot.getData().get("transcription_result").toString());
                                }
                            }
                        });
                }
            }
        });

    }
}
