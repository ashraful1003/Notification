package com.examples.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /// initialize the buttons
    Button toastBtn, customToastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// find buttons by id
        toastBtn = findViewById(R.id.toast_btn);
        customToastBtn = findViewById(R.id.custom_toast_btn);

        /// set onClick listener to handle button click
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// make the toast
                Toast toast = Toast.makeText(MainActivity.this, "Simple Toast", Toast.LENGTH_SHORT);
                /// you can set the gravity to where you want to show the toast.
                //
                /// if there's necessary comment out use otherwise remove from the line setGravity only
                //toast.setGravity(Gravity.TOP | Gravity.RIGHT, 100, 250);

                toast.show();
            }
        });

        customToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                TextView textView = layout.findViewById(R.id.text_view);
                textView.setText("Custom Toast Notification");
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}