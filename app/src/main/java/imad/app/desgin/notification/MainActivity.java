package imad.app.desgin.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String NOTIF_ID = "notif_id" ;
    Button notif ;
    Intent i  ;
    PendingIntent pi  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notif = findViewById(R.id.notifiation);

        //
        i = new Intent(this , MainActivity.class) ;
        //  بقصد PendingIntent اي تطبيق خارج التطبيق يريد التواصل معه فعليك استعمال PendingIntent
        pi =PendingIntent.getActivities(this, 0  , new Intent[]{i}, 0) ;


        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayNotification() ;     // دالة اضهار الاشعارات

            }
        });
    }

    private void displayNotification() {

        // Build.VERSION.SDK_INT          الاصدار الجديد الخاص بتطبيق
        // Build.VERSION_CODES.O                   26    الاصدار القديم

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

                  // channel dispaly name  اسم القناة
                    // NOTIF_ID   id الخاص بها
                    NotificationChannel channel=new NotificationChannel(NOTIF_ID , "channel dispaly name"  ,
                    NotificationManager.IMPORTANCE_DEFAULT ); // IMPORTANCE_DEFAULT اهمية ضهور الاشعارات في التطبيق



                    channel.setDescription("my channel description"); //

                    // دالة الادارة   NotificationManager
                    NotificationManager  nm  = getSystemService(NotificationManager.class) ;

                    nm.createNotificationChannel(channel);      //  انشاء channel

                }
                //
        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.ic_replay_24);

        /**
         * اداكان الاصدار اكبر من 26 سيتم ربط id بي channel
         * ادا كان الاصدار اقل لا يتم ربطه
         */
        NotificationCompat.Builder bd  = new NotificationCompat.Builder( getBaseContext()  ,NOTIF_ID  );
          bd.setSmallIcon(R.drawable.ic_notifications_24 );      //
          bd.setContentTitle("my title ") ;
          bd.setContentText("السلام عليكم اتمنا ان يعجبك التطبيق الخاص بنا ") ;  //
          bd.setPriority(NotificationCompat.PRIORITY_HIGH ); //  الاولية

        // bd.setStyle(new NotificationCompat.BigTextStyle().bigText("my data"));

          // دالة  addAction يقصد بها عندا الضغط عل Replay  يقوم بحدث معين
        bd.addAction( R.drawable.ic_replay_24  , "Get Up "  , pi) ;
        bd.addAction(R.drawable.ic_replay_24 , "Option"   , pi );
              // اضهار الصور الجانبية
        //bd.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
      //  .setLargeIcon(null);
        bd.setLargeIcon(bitmap) ;

        bd.setStyle(new NotificationCompat.InboxStyle().addLine("line 1")
         .addLine("Line 2")
        .addLine("Line 3")) ;
          bd.setContentIntent(pi) ; //  عندا الضغط عل notification يقوم بحدث معين

          NotificationManagerCompat  mc =  NotificationManagerCompat.from(this) ;

          //  الرقم 1 يقصد بي  id المتعلق بي notification
          mc.notify(1 , bd.build());         //           عرض notification

    }
}