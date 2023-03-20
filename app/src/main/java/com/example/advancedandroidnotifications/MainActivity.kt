package com.example.advancedandroidnotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find buttons from the xml file and assign them to variables
        val btnOne = findViewById<Button>(R.id.btn_one)
        val btnTwo = findViewById<Button>(R.id.btn_two)
        val btnThree = findViewById<Button>(R.id.btn_three)

        createNotificationChannel()

        btnOne.setOnClickListener {
            sendNotificationBasic()
        }
        btnTwo.setOnClickListener {
            sendNotificationWDesc()
        }
        btnThree.setOnClickListener {
            sendNotificationWImage()
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotificationBasic() {

        //Luodaan yksinkertainen ilmoitus

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)    //ilmoituksen ikoni
            .setContentTitle("Himoläski")                       //annetaan ilmoitukselle otsikko
            .setContentText("iha ok")                           //ilmoituksen teksti
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)   //annetaan ilmoitukselle priority

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())             //rekisteröidään ilmoitus
        }
    }

    private fun sendNotificationWDesc() {

        //Luodaan ilmoitus, johon mahtuu enemmän tekstiä eli kuvaus, pikku kuva ja napautustoiminto

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK            //luodaan napautus ominaisuus ilmoitukseen
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.himolaski) //luodaan bitmap ilmoutksen pikkukuvalle

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //ikoni
            .setContentTitle("Himoläski")                    //otsake
            .setLargeIcon(bitmap)                            //pikkukuva
                //laitetaan setStyle kohtaan iso tekstikenttä
            .setStyle(NotificationCompat.BigTextStyle().bigText("iha ok, mut ootteko kattonu simpsonit sarjasta jakson himo läski homer :D siinä esiintyy koko simpsonit perhe eli myös bart simpsons homer poika fanit saavat nauraa ja naurattaahan se tietty myös vaikka homerin läski kuteet ja muut :D kannattaa kattoo nopee"))
            .setContentIntent(pendingIntent)                 //clicki toiminto
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)//priority

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun sendNotificationWImage() {

        //tehdään ilmoitus isolla kuvalla ja napautus toiminnolla

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.himolaski)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Himoläski")
            .setLargeIcon(bitmap)
                //setStylessä määritellään tyyliksi iso kuva ja sijoitetaan yllä luotu bitmap kuvasta siihen
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}