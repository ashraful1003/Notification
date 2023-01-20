package com.examples.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
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

        /// progress notification
        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification();
            }
        });
    }

    private void addNotification() {
        createNotificationChannel();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "here")
                        .setSmallIcon(R.drawable.icons)
                        .setContentTitle("Progress Notifications")
                        .setContentText("This is a progress notification")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i += 5) {
                    builder.setProgress(100, i, false);
                    manager.notify(0, builder.build());
                    try {
                        /// in every 500 milliseconds progress will update
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                builder.setContentText("Download completed")
                        .setProgress(0, 0, false);
                manager.notify(0, builder.build());
            }
        }).start();
    }

    /// for permission
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("here", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}