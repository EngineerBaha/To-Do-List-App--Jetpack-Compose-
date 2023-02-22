package com.bahakuzudisli.todolist.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bahakuzudisli.todolist.viewmodel.NoteDoneViewModel

class NoteDoneViewModelFactory (var application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDoneViewModel(application) as T
    }
}