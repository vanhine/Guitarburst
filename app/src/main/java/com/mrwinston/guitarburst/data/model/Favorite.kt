package com.mrwinston.guitarburst.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorite(@ColumnInfo(name = "fave_uid") val fave_uid: Int) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
}