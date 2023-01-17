package com.examples.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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
    Button toastBtn, customToastBtn, progressBtn;

    /// for progress notification
    NotificationManager manager;
    Notification notification;
    NotificationCompat.Builder builder;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// find buttons by id
        toastBtn = findViewById(R.id.toast_btn);
        customToastBtn = findViewById(R.id.custom_toast_btn);
        progressBtn = findViewById(R.id.progress_btn);

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

        /// showing custom toast button
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

        /// showing progress notification
        String CHANNEL_ID = "Progress Notification";
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(MainActivity.this);
        }
        builder.setContentTitle("Notification Title");
        builder.setSmallIcon(R.drawable.icons);
        builder.setContentText("Notification Progressing");

        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// if we are asked to set a progress bar we will use the 'Thread'
                //
                /// otherwise remove this part
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for (i = 0; i <= 100; i += 5) {
                            builder.setProgress(100, i, false);
                            manager.notify(id, builder.build());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        builder.setContentText("Progress Done").setProgress(0, 0, false);
                        manager.notify(id, builder.build());
                    }
                }).start();

                /// if we remove the thread we have to uncomment this part to show notification
//                manager.notify(id, builder.build());
            }
        });
    }
}