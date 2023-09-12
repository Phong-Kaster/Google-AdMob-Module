package com.example.googleadmodmodule.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.googleadmodmodule.database.entity.NationalityEntity
import com.example.googleadmodmodule.database.entity.UserEntity
import com.example.googleadmodmodule.database.relation.UserRelation

@Dao
interface NationalityDao {

    //For uniqueness violations, deletes other rows that would cause the violation before executing this statement
    // OnConflictStrategy.REPLACE will replace the current entity with the old entity if they have the
    // same attributes.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nationalityEntity: NationalityEntity): Long

    @Delete
    fun delete(nationalityEntity: NationalityEntity)

    @Update
    fun update(nationalityEntity: NationalityEntity)

    @Query("SELECT * FROM table_nationality WHERE uid LIKE :uid")
    fun findById(uid: Int): NationalityEntity
}