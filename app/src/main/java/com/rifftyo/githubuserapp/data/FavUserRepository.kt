package com.rifftyo.githubuserapp.data

import androidx.lifecycle.LiveData
import com.rifftyo.githubuserapp.data.database.FavoriteUser
import com.rifftyo.githubuserapp.data.database.FavoriteUserDao
import com.rifftyo.githubuserapp.utils.AppExecutors

class FavUserRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
){

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getFavUser()
    }

    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute {
            favoriteUserDao.insertFavUser(favoriteUser)
        }
    }

    fun removeFavoriteUser(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute {
            favoriteUserDao.deleteFavUser(favoriteUser)
        }
    }

    companion object {
        private var instance: FavUserRepository? = null

        fun getInstance(favoriteUserDao: FavoriteUserDao, appExecutors: AppExecutors): FavUserRepository {
            if (instance == null) {
                synchronized(FavUserRepository::class.java) {
                    instance = FavUserRepository(favoriteUserDao, appExecutors)
                }
            }
            return instance!!
        }
    }
}
