package com.hrappv.data.di.module

import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.*
import com.hrappv.data.local.datastoreimp.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dagger.Module
import dagger.Provides
import excel.ImportExcelFile
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
        return JdbcSqliteDriver(url = "jdbc:sqlite:database.db", Properties()).apply {
            HrAppDb.Schema.create(this)
        }
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

//
//    @Provides
//    @Singleton
//    fun provideDataBase(sqliteDriver: SqlDriver): HrAppDb {
//        val sqlCursor = sqliteDriver.executeQuery(null, "PRAGMA user_version;", 0, null)
//
//        var version: Int = sqlCursor.getLong(0)!!.toInt()
//
//        val currentVer = version
//        if (currentVer == 0) {
//            HrAppDb.Schema.create(sqliteDriver)
//            version = 1
//            sqliteDriver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
//            println("init: created tables, setVersion to 1")
//        } else {
//            val schemaVer: Int = HrAppDb.Schema.version
//            if (schemaVer > currentVer) {
//                HrAppDb.Schema.migrate(sqliteDriver, currentVer, schemaVer)
//                version = schemaVer
//                sqliteDriver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
//
//                println("init: migrated from $currentVer to $schemaVer")
//            } else {
//                println("init")
//            }
//        }
//        return HrAppDb(sqliteDriver)
//
//    }


    @Provides
    @Singleton
    fun provideUsersDataSource(
//        hrDb: HrAppDb,
        sqliteDriver: SqlDriver,

        dispatcher: CoroutineDispatcher
    ): UserDataSource {
        return UserDataSourceImpl(
//            hrDb, dispatcher
            HrAppDb(sqliteDriver), dispatcher
        )
    }

    @Provides
    @Singleton
    fun provideViewEmpDataSource(
        sqliteDriver: SqlDriver,

        dispatcher: CoroutineDispatcher
    ): ViewEmpDataSource {
        return ViewEmpDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )
    }


    @Provides
    @Singleton
    fun provideDepartmentDataSource(
        sqliteDriver: SqlDriver,

//        hrDb: HrAppDb,
        dispatcher: CoroutineDispatcher
    ): DepartmentDataSource {
        return DepartmentDataSourceImpl(
            HrAppDb(sqliteDriver),

//            hrDb,
            dispatcher
        )
    }

    @Provides
    @Singleton
    fun provideDayRegisterDataSource(
        sqliteDriver: SqlDriver,

//        hrDb: HrAppDb,
        dispatcher: CoroutineDispatcher
    ): DayRegisterDataSource {
        return DayRegisterDataSourceImpl(
            HrAppDb(sqliteDriver),
            dispatcher
        )
    }

    @Provides
    @Singleton
    fun provideCamRegister(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): CamRegisterDataSource {
        return CamRegisterDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )

    }

    @Provides
    @Singleton
    fun provideDayDetails(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): DayDetailsDataSource {
        return DayDetailsDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )

    }


    @Provides
    @Singleton
    fun provideAbsentDay(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): AbsentDayDataSource {
        return AbsentDayDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )

    }


    @Provides
    @Singleton
    fun provideEmpResult(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): EmpResultDataSource {
        return EmpResultDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )

    }

//    fun getImporter(): ImportExcelFile = ImportExcelFile()


    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.Main
}