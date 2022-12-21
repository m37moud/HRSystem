package com.hrappv.data.di.module

import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.local.datastoreimp.UserDataSourceImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

//@InstallIn(ApplicationComponent::class)
//@Module
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideSqlDelightProvider(): SqlDriver {
	val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    HrAppDb.Schema.create(driver)
	
        return driver
		//JdbcSqliteDriver (
//            schema = HrAppDb.Schema,
//            context = app,
//            name = "hr.db"
          //  JdbcSqliteDriver.IN_MEMORY
       // )
    }
	


    @Provides
    @Singleton
    fun provideUsersDataSource(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): UserDataSource {
        return UserDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
}