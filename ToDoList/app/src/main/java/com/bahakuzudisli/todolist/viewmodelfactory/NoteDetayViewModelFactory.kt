package com.bahakuzudisli.todolist.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bahakuzudisli.todolistapp.viewmodel.NoteDetayViewModel

class NoteDetayViewModelFactory (var application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetayViewModel(application) as T
    }
}