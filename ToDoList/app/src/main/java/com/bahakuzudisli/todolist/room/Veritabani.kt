package com.bahakuzudisli.todolistapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bahakuzudisli.todolist.entity.Notes


@Database(entities = [Notes::class], version = 1)
abstract class Veritabani : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        var INSTANCE: Veritabani? = null

        fun veritabaniErisim(context: Context): Veritabani? {

            if (INSTANCE == null) {
                synchronized(Veritabani::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext,
                            Veritabani::class.java,
                            name = "todo2.sqlite"
                        ).createFromAsset("todo2.sqlite").build()
                }
            }
            return INSTANCE
        }
    }

}