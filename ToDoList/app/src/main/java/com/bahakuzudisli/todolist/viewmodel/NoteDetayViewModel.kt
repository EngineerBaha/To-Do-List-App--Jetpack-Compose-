package com.bahakuzudisli.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bahakuzudisli.todolist.repo.NoteDaoRepository

class NoteDetayViewModel(application: Application):AndroidViewModel(application) {

    var nrepo = NoteDaoRepository(application)

    fun update(note_id:Int,note:String,is_done:Int,notification_id: Int,date: String,time: String){
        nrepo.noteUpdate(note_id,note,is_done,notification_id,date,time)
    }

}