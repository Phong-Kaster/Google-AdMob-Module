package com.example.googleadmodmodule.datainjection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.googleadmodmodule.core.Constant
import com.example.googleadmodmodule.database.ApplicationDatabase
import com.example.googleadmodmodule.database.dao.NationalityDao
import com.example.googleadmodmodule.database.dao.UserDao
import com.example.googleadmodmodule.database.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        val database = Room
            .databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                Constant.APPLICATION_DATABASE
            )
            .fallbackToDestructiveMigration()
            .addCallback(callback = object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO table_nationality (language, nation, uid) VALUES('Vietnamese','Vietnam', 0)")

                    db.execSQL("INSERT INTO table_user( first_name, last_name, nationality_id) VALUES('Karl','Heisenberg', 0)")
                    db.execSQL("INSERT INTO table_user( first_name, last_name, nationality_id) VALUES('Phong','Kaster', 0)")
                    db.execSQL("INSERT INTO table_user( first_name, last_name, nationality_id) VALUES('Thanh','Phong', 0)")

                    db.execSQL("INSERT INTO table_playlist (name, user_id) VALUES('My Love', 1)")
                    db.execSQL("INSERT INTO table_playlist (name, user_id) VALUES('My Valentine', 1)")
                }
            })
            .build()

        return database
    }

    @Provides
    @Singleton
    fun provideUserDao(database: ApplicationDatabase): UserDao {
        return database.userDao()
    }


    @Provides
    @Singleton
    fun provideNationalityDao(database: ApplicationDatabase): NationalityDao {
        return database.nationalityDao()
    }
}