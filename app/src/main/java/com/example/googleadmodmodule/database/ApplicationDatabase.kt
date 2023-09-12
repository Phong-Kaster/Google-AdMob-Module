package com.example.googleadmodmodule.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googleadmodmodule.database.dao.NationalityDao
import com.example.googleadmodmodule.database.dao.UserDao
import com.example.googleadmodmodule.database.entity.NationalityEntity
import com.example.googleadmodmodule.database.entity.PlaylistEntity
import com.example.googleadmodmodule.database.entity.UserEntity


@Database(
    entities = [
        UserEntity::class,
        NationalityEntity::class,
        PlaylistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun nationalityDao(): NationalityDao
}