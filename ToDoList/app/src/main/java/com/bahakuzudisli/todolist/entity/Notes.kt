package com.bahakuzudisli.todolist.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "notes")
data class Notes(@PrimaryKey(autoGenerate = true)
                @ColumnInfo("note_id") @NotNull var note_id: Int,
                 @ColumnInfo("note") @NotNull var note: String,
                 @ColumnInfo("is_done") @NotNull var is_done: Int,
                 @ColumnInfo("notification_id") @NotNull var notification_id: Int,
                 @ColumnInfo("date") @NotNull var date: String,
                 @ColumnInfo("time") @NotNull var time: String)