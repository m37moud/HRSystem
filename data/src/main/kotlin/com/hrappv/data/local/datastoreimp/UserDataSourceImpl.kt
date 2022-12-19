package com.hrappv.data.local.datastoreimp

import com.hrappv.HrAppDb
import com.hrappv.User
import com.hrappv.data.local.datastore.UserDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserDataSourceImpl(
    hrDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : UserDataSource {

    private val queries = hrDb.userQueries

    override suspend fun getSingleUser(id: Long) : User? {
        return withContext(dispatcher) {
            queries.getSingleUser(id).executeAsOneOrNull()
        }
    }

    override fun getAllUsers(): Flow<List<User>> {
        return queries.getAllUser().asFlow().mapToList()
    }

    override suspend fun deleteAdmin(id: Long) {
        withContext(dispatcher) {
            queries.deleteUser(id)
        }
    }

    override suspend fun upsertUser(id: Long?, user: String, password: String,playRole:String) {
        withContext(dispatcher) {
            queries.upsertUSer(id, user, password,playRole)
        }
    }
}