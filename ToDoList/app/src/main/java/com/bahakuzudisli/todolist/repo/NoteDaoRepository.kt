package com.bahakuzudisli.todolist.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bahakuzudisli.todolist.entity.Notes
import com.bahakuzudisli.todolistapp.room.Veritabani
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteDaoRepository (application: Application){

    var noteListesi = MutableLiveData<List<Notes>>()
    var noteListesi2 = MutableLiveData<List<Notes>>()
    var vt:Veritabani

    init {
        vt = Veritabani.veritabaniErisim(application)!!
        noteListesi = MutableLiveData()
    }

    fun bringNotes():MutableLiveData<List<Notes>>{
        return noteListesi
    }

    fun takeAllNotes(){
        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            noteListesi.value=vt.noteDao().allNotes()
        }
    }

    fun bringDoneNotes():MutableLiveData<List<Notes>>{
        return noteListesi2
    }

    fun takeDoneNotes(){
        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            noteListesi2.value=vt.noteDao().doneNotes()
        }
    }


    fun noteSave(note:String,notification_id:Int,date:String,time:String){

        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            val theNote = Notes(0,note,0,notification_id,date,time)
            vt.noteDao().addNote(theNote)
        }

    }

    fun noteSearch(searching_word:String){

        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            noteListesi.value=vt.noteDao().searchNote(searching_word)
        }

    }

    fun noteDoneSearch(searching_word:String){

        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            noteListesi2.value=vt.noteDao().searchDoneNote(searching_word)
        }

    }

    fun noteUpdate(note_id:Int,note:String,is_done:Int,notification_id: Int,date: String,time: String){

        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            val theNote = Notes(note_id,note,is_done,notification_id,date,time)
            vt.noteDao().updateNote(theNote)
            //burası değişti->
            takeAllNotes()
            takeDoneNotes()
        }

    }

    fun noteDelete(note_id:Int){

        val job:Job= CoroutineScope(Dispatchers.Main).launch {
            val theNote = Notes(note_id,"",0,0," ", " ")
            vt.noteDao().deleteNote(theNote)
            takeAllNotes()
        }

    }

}