package com.project2.orchid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project2.orchid.Activity.ProductActivity;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "1";
    private NotificationChannel channel;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            final String customer = data.get("customer");
            String image = data.get("image");
            final String product = data.get("product");

            Glide.with(this).asBitmap().load(image).circleCrop().into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Intent intent = new Intent(NotificationService.this, ProductActivity.class);
                    intent.putExtra("Ten", product);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    android.app.Notification notification = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setColor(ContextCompat.getColor(NotificationService.this, R.color.colorRed))
                            .setContentTitle(customer)
                            .setContentText("Đã bình luận về sản phẩm của bạn")
                            .setLargeIcon(resource)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build();
                    notificationManagerCompat.notify((int) System.currentTimeMillis(), notification);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });


        }
    }

    private void createNotificationChannel() {
        notificationManagerCompat = NotificationManagerCompat.from(NotificationService.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Orchid Noti";
            String description = "Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
