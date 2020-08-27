package com.mrwinston.guitarburst.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Serenade for Guitar: III.  Infinite Canon,Lou Harrison,9,7,XIII,Medium,Modern,,Peer International Corp.,"This is a version of Harrison's great modern work for guitar (or guitar and percussion), written in tablature, which makes the reading difficulties relatively moot if one reads tab.  The tablature is pretty necessary if one is to follow Harrison's tuning of D-A-D-D-A-D.This movement is much more difficult to play and the tablature is indecipherable at times.  Regardless, there are a lot of finger contortions and position shifts needed."
 */
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