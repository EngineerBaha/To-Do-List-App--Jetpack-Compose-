package com.bahakuzudisli.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bahakuzudisli.todolist.entity.Notes
import com.bahakuzudisli.todolist.repo.NoteDaoRepository

class NoteDoneViewModel (application: Application) : AndroidViewModel(application){
    var nrepo = NoteDaoRepository(application)
    var noteListesi = MutableLiveData<List<Notes>>()


    init {
        downloadNotes()
        noteListesi=nrepo.bringDoneNotes()
    }
    fun downloadNotes(){
        nrepo.takeDoneNotes()
    }

    fun search(word:String){
        nrepo.noteDoneSearch(word)
    }

    fun delete(note_id:Int){
        nrepo.noteDelete(note_id)
    }

    fun updateStutation(note_id:Int,note:String,is_done:Int,notification_id: Int,date: String,time: String){
        nrepo.noteUpdate(note_id,note,is_done,notification_id,date,time)
    }

}