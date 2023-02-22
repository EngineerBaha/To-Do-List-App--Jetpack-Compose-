package com.bahakuzudisli.todolistapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bahakuzudisli.todolist.entity.Notify
import com.bahakuzudisli.todolist.repo.MyWorkerBildirim
import com.bahakuzudisli.todolist.repo.NoteDaoRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class NoteKayitViewModel(application: Application): AndroidViewModel(application) {

    var nrepo = NoteDaoRepository(application)


    fun save(note:String,date:LocalDate,time:LocalTime, context: Context,flag:Int){

        if(flag==0){
            println("flag 0 dir")
            nrepo.noteSave(note,0,"","")
        }
        else{
            println("flag 1 dir")
            var num = notifIdGetir(context)
            nrepo.noteSave(note,num,date.toString(),time.toString())
            bildirimOlustur(num,date,time,note,context)
        }


    }

    fun notifIdGetir(context:Context):Int{
        val sharedPreferences = context.getSharedPreferences("com.bahakuzudisli.todolistapp",Context.MODE_PRIVATE)
        var mySharedNumber =sharedPreferences.getInt("myNumber",1)
        mySharedNumber=mySharedNumber+1
        sharedPreferences.edit().putInt("myNumber",mySharedNumber).apply()
        println("artan sayi = $mySharedNumber")
        return mySharedNumber
    }

    fun bildirimOlustur(numId:Int,pickedDate:LocalDate,pickedTime:LocalTime,cont:String,context:Context){

        println("NoteKayitViewModel, bildirim olustur sayfası, datalar")
        println("$numId - $pickedDate $pickedTime $cont")

        println("content is $cont")
        val now = LocalDateTime.now()
        val scheduledTime = LocalDateTime.of(pickedDate, pickedTime)
        val secondsUntilAlarm = ChronoUnit.SECONDS.between(now, scheduledTime)

       // val dataIcerik = Data.Builder().putString("cKey",cont).build()
       // val dataId = Data.Builder().putInt("idKey",numId).build()
        var array = arrayOf<String>(numId.toString(), cont)

        val data = Data.Builder().putStringArray("dataKey",array).build()
        val alarmRequest = OneTimeWorkRequestBuilder<MyWorkerBildirim>()
            .setInputData(data)
            .setInitialDelay(secondsUntilAlarm, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(alarmRequest)



    }

}