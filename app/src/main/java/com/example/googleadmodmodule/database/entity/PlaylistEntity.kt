package com.example.googleadmodmodule.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_playlist")
data class PlaylistEntity
constructor(
    @PrimaryKey(autoGenerate = true) val uid: Int? = 0,
    @ColumnInfo(name = "user_id") val userId: Int? = 0,
    @ColumnInfo(name = "name") val name: String?,
) {
}