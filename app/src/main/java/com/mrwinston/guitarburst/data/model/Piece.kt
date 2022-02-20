package com.mrwinston.guitarburst.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pieces_table")
data class Piece(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "composer") val composer: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "reading") val reading: Int,
    @ColumnInfo(name = "position") val position: String,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "era") val era: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "publisher") val publisher: String,
    @ColumnInfo(name = "notes") val notes: String
)