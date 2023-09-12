package com.example.googleadmodmodule.database.repository

import com.example.googleadmodmodule.database.dao.NationalityDao
import com.example.googleadmodmodule.database.entity.NationalityEntity
import javax.inject.Inject

class NationalityRepository
@Inject
constructor(
    private val nationalityDao: NationalityDao
) {
    suspend fun insert(nationalityEntity: NationalityEntity): Long {
        return nationalityDao.insert(nationalityEntity)
    }

    suspend fun update(nationalityEntity: NationalityEntity) {
        return nationalityDao.update(nationalityEntity)
    }

    suspend fun delete(nationalityEntity: NationalityEntity) {
        return nationalityDao.delete(nationalityEntity)
    }

    suspend fun findById(uid: Int): NationalityEntity {
        return nationalityDao.findById(uid)
    }
}