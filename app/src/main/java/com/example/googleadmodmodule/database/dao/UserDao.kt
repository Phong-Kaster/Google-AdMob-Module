package com.example.googleadmodmodule.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.googleadmodmodule.database.entity.UserEntity
import com.example.googleadmodmodule.database.relation.UserRelation

@Dao
interface UserDao {
    @Query("SELECT * FROM table_user")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM table_user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserEntity>

    @Query(
        "SELECT * " +
                "FROM TABLE_USER " +
                "WHERE first_name " +
                "LIKE :first " +
                "AND last_name " +
                "LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): UserEntity

    @Insert
    fun insertAll(vararg users: UserEntity)

    //For uniqueness violations, deletes other rows that would cause the violation before executing this statement
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    @Query("SELECT * FROM table_user WHERE uid LIKE :uid")
    fun findById(uid: Int): UserEntity

    /**
     * this query returns all records relate to this user object.
     * It not only returns user object but also returns all records from other tables' record, it includes
     * Nationality and Playlist that belongs to user*/
    @Transaction
    @Query("SELECT * FROM table_user WHERE uid LIKE :uid")
    fun getUserInfo(uid: Int): UserRelation
}