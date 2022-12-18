package com.hrappv.di

import com.hrappv.HrAppDb
import com.hrappv.ui.feature.EmployeResult.EditEmployeScreenComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
import com.hrappv.ui.feature.login.LoginComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.splash.SplashScreenComponent
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.theapache64.cyclone.core.Application
import dagger.Component
import dagger.Provides
import excel.ImportExcelFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
//        ImportExcelFile::class
        // Add your modules here
    ]
)
interface AppComponent {
    fun inject(splashScreenComponent: SplashScreenComponent)
    fun inject(loginhScreenComponent: LoginComponent)
    fun inject(mainScreenComponent: MainScreenComponent)
    fun inject(employResultScreenComponent: EmployResultScreenComponent)
    fun inject(addEmployeScreenComponent: AddEmployeScreenComponent)
    fun inject(editEmployeScreenComponent: EditEmployeScreenComponent)

    fun getImporter() :ImportExcelFile

    @Provides
    @Singleton
    fun provideSqlDelightProvider(app: Application): SqlDriver {
        return JdbcSqliteDriver(
//            schema = HrAppDb.Schema,
//            context = app,
//            name = "hr.db"
            JdbcSqliteDriver.IN_MEMORY
        )
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): NotesDataSource {
        return NotesDataSourceImpl(
            HrAppDb(sqliteDriver), dispatcher
        )
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
}