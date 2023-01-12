package com.hrappv.data.di.module

import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.local.datastore.ViewEmpDataSource
import com.hrappv.data.local.datastoreimp.UserDataSourceImpl
import com.hrappv.data.local.datastoreimp.ViewEmpDataSourceImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Singleton

//@InstallIn(ApplicationComponent::class)
//@Module
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideSqlDelightProvider(): SqlDriver {
        //JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY + filePath, Properties())
//   HrAppDb.Schema.create(driver)
        return JdbcSqliteDriver(url = "jdbc:sqlite:database.db", Properties())
        //JdbcSqliteDriver (
//            schema = HrAppDb.Schema,
//            context = app,
//            name = "hr.db"
        //  JdbcSqliteDriver.IN_MEMORY
        // )
    }

//    @Provides
//    @Singleton
//    fun provideDatabaseVersion(driver: SqlDriver): Int {
//        var version: Int = 0
//
//        val sqlCursor = driver.executeQuery(null, "PRAGMA user_version;", 0, null)
//        version = sqlCursor.getLong(0)!!.toInt()
//
//
//        driver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
//
//        return version
//    }

//    private var versio =
//        get() {
//            val sqlCursor = driver.executeQuery(null, "PRAGMA user_version;", 0, null)
//            sqlCursor.getLong(0)!!.toInt()
//        }
//
//
//private set(versio)
//{
//    driver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
//}


    @Provides
    @Singleton
    fun provideDataBase(sqliteDriver: SqlDriver): HrAppDb {
        val sqlCursor = sqliteDriver.executeQuery(null, "PRAGMA user_version;", 0, null)

        var version: Int = sqlCursor.getLong(0)!!.toInt()

        val currentVer = version
        if (currentVer == 0) {
            HrAppDb.Schema.create(sqliteDriver)
            version = 1
            sqliteDriver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
            println("init: created tables, setVersion to 1")
        } else {
            val schemaVer: Int = HrAppDb.Schema.version
            if (schemaVer > currentVer) {
                HrAppDb.Schema.migrate(sqliteDriver, currentVer, schemaVer)
                version = schemaVer
                sqliteDriver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)

                println("init: migrated from $currentVer to $schemaVer")
            } else {
                println("init")
            }
        }
        return HrAppDb(sqliteDriver)

    }


    @Provides
    @Singleton
    fun provideUsersDataSource(
        hrDb: HrAppDb,
        dispatcher: CoroutineDispatcher
    ): UserDataSource {
        return UserDataSourceImpl(
            hrDb, dispatcher
        )
    }

    @Provides
    @Singleton
    fun provideViewEmpDataSource(
        hrDb: HrAppDb,
        dispatcher: CoroutineDispatcher
    ): ViewEmpDataSource {
        return ViewEmpDataSourceImpl(
            hrDb, dispatcher
        )
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
}