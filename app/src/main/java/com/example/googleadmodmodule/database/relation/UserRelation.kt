package com.example.googleadmodmodule.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.googleadmodmodule.database.entity.NationalityEntity
import com.example.googleadmodmodule.database.entity.PlaylistEntity
import com.example.googleadmodmodule.database.entity.UserEntity

/**
 * Because SQLite is a relational database, you can define relationships between entities.
 * Instead of writing by hand JOIN query between two tables, the code below
 * shows an approach that helps us to find all related records from other tables.
 */
data class UserRelation
constructor(
    @Embedded val userEntity: UserEntity,

    /**
     * this sentence represents the one-to-one relationships between user and nationality.
     * for example, at birth, one person has one nationality.
     * - nationality_id from UserEntity
     * - uid from NationalityEntity*/
    @Relation(parentColumn = "nationality_id", entityColumn = "uid")
    val nationalityAtBirth: NationalityEntity,


    /**
     * this sentence represents the one-to-many relationship between user and playlist.
     * for example, one person has more than one playlist of songs.
     * - uid from UserEntity
     * - user_id from PlaylistEntity*/
    @Relation(parentColumn = "uid", entityColumn = "user_id")
    val playlists: List<PlaylistEntity>
) {
}