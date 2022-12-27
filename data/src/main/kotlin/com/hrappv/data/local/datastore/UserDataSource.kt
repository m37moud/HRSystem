package com.hrappv.data.local.datastore
import com.hrappv.User
import kotlinx.coroutines.flow.Flow
interface UserDataSource {

    suspend fun getSingleUser(id: Long): User?
    suspend fun getUser(username: String): User?

    fun getAllUsers(): List<User>

    suspend fun deleteAdmin(id: Long)

    suspend fun upsertUser(id: Long? = null, user: String, password: String,playRole:String)
}