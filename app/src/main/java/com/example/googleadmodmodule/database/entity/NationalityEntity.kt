package com.example.googleadmodmodule.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_nationality")
data class NationalityEntity
constructor(
    @PrimaryKey(autoGenerate = true) val uid: Int? = 0,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "nation") val nation: String?
)


