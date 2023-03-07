package ru.nickbesk.myapplication;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import ru.nickbesk.myapplication.ButtonReceiver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

public class MainActivity extends AppCompatActivity {
    private final String channelID = "ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        EditText editText = findViewById(R.id.CodeInput);
        editText.setGravity(Gravity.TOP);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Уведомление", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
    // Создание канала уведомлений
    private void createNotificationChannel() {
        CharSequence name = "Основное";
        String description = "Основные уведомления";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID, name, importance);
        channel.setDescription(description);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
    // Обработчик нажатия на кнопку запуска приложения
    @SuppressLint("LaunchActivityFromNotification")
    public void sendNote(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Запустить программу?").setCancelable(false)
                .setPositiveButton("Да", (dialog, which) -> {
                    Context context = getApplicationContext();
                    Intent intent = new Intent();
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    Intent snoozeIntent = new Intent(this, ButtonReceiver.class);
                    snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 101);
                    PendingIntent snoozePendingIntent =
                            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID);
                    builder
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Запуск кода")
                            .setContentText("Пока что программа не запускается, поэтому вот Вам такое вот уведомление")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Пока что программа не запускается, поэтому вот Вам такое вот уведомление"))
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .addAction(R.drawable.ic_launcher_background, getString(R.string.snooze), snoozePendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_DEFAULT);
                    Notification note = builder.build();
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(101, note);
                }).setNegativeButton("Нет", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Диалоговое окно");
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

