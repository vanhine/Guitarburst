package com.mrwinston.guitarburst.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "fave_uid") val fave_uid: Int,
    @ColumnInfo(name = "fave_title") val fave_title: String
)