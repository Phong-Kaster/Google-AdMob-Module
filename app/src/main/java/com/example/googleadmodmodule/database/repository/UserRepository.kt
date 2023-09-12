package com.example.googleadmodmodule.database.repository

import com.example.googleadmodmodule.database.dao.UserDao
import com.example.googleadmodmodule.database.entity.UserEntity
import com.example.googleadmodmodule.database.relation.UserRelation
import javax.inject.Inject

class UserRepository
@Inject
constructor(private val userDao: UserDao) {

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    suspend fun delete(userEntity: UserEntity) {
        userDao.delete(userEntity)
    }

    suspend fun update(userEntity: UserEntity) {
        userDao.update(userEntity)
    }

    suspend fun findById(uid: Int): UserEntity {
        return userDao.findById(uid)
    }

    suspend fun getUserInfo(uid: Int): UserRelation{
        return userDao.getUserInfo(uid)
    }
}