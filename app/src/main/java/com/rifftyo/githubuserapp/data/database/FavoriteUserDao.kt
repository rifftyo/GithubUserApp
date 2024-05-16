package com.rifftyo.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(favoriteUser: FavoriteUser)

    @Delete
    fun deleteFavUser(favoriteUser: FavoriteUser)

    @Query("SELECT  * FROM favoriteuser where isFavUser = 1")
    fun getFavUser(): LiveData<List<FavoriteUser>>
}