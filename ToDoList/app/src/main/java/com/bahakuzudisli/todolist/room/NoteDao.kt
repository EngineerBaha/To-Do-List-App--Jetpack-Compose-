package com.bahakuzudisli.todolistapp.room

import androidx.room.*
import com.bahakuzudisli.todolist.entity.Notes

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes where is_done = 0")
    suspend fun allNotes():List<Notes>

    @Query("SELECT * FROM notes where is_done = 1")
    suspend fun doneNotes():List<Notes>

    @Insert
    suspend fun addNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes WHERE note like '%' || :searchingNote || '%' AND is_done = 0")
    suspend fun searchNote(searchingNote:String):List<Notes>

    @Query("SELECT * FROM notes WHERE note like '%' || :searchingDoneNote || '%' AND is_done = 1")
    suspend fun searchDoneNote(searchingDoneNote:String):List<Notes>

}