package com.bahakuzudisli.todolist.repo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bahakuzudisli.todolist.R

class MyWorkerBildirim(var appContext: Context, workerParams: WorkerParameters) : Worker(appContext,workerParams) {
    override fun doWork(): Result {
        println("worker classının içi")
        val getData = inputData

       // val dataIcerik = getData.getString("cKey")
       // val dataId = getData.getInt("idKey",1000)
        val data = getData.getStringArray("dataKey")
       val dataIcerik = data?.get(1)
        val dataId = data?.get(0)?.toInt()
        if (dataIcerik != null) {
            bildirimOlustur(appContext,dataIcerik,dataId!!)
        }
        else{
            bildirimOlustur(appContext,"Bir Göreviniz Var!",dataId!!)
        }


        return Result.success()
    }

    fun bildirimOlustur(context: Context,data:String,dataId:Int){
        println("bildirim içerisindeyizzz")
        val builder: NotificationCompat.Builder
        val bildirimYoneticisi = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val kanalId = "kanalId"
            val kanalAd = "kanalAd"
            val kanalTanitim = "kanalTanitim"
            val kanalOnceligi = NotificationManager.IMPORTANCE_HIGH

            var kanal: NotificationChannel? = bildirimYoneticisi.getNotificationChannel(kanalId)

            if (kanal == null){
                kanal = NotificationChannel(kanalId,kanalAd,kanalOnceligi)
                kanal.description = kanalTanitim
                bildirimYoneticisi.createNotificationChannel(kanal)
            }

            builder = NotificationCompat.Builder(context,kanalId)

            builder.setContentTitle("To-Do")
                .setContentText(data)
                .setSmallIcon(R.drawable.resim_1)
                .setAutoCancel(true)

        }else{
            builder = NotificationCompat.Builder(context)

            builder.setContentTitle("To-Do")
                .setContentText(data)
                .setSmallIcon(R.drawable.resim_1)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH
        }

        bildirimYoneticisi.notify(dataId,builder.build())
        // bildirimYoneticisi.cancel(1)
    }
}