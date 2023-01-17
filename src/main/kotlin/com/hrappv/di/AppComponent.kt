package com.hrappv.di

import com.hrappv.HrAppDb
import com.hrappv.data.di.module.DatabaseModule
import com.hrappv.ui.feature.EmployeResult.EditEmployeScreenComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
import com.hrappv.ui.feature.login.LoginComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.splash.SplashScreenComponent
import com.squareup.sqldelight.db.SqlDriver
import com.theapache64.cyclone.core.Application
import dagger.Component
import dagger.Provides
import excel.ImportExcelFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.local.datastoreimp.UserDataSourceImpl
import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.add_department.DepartmentComponent
import com.hrappv.ui.feature.home_screen.HomeComponent
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.feature.main.MainViewModel
import com.hrappv.ui.feature.settings.SettingsComponent
import com.hrappv.ui.feature.view_employees.ViewEmployeesComponent

@Singleton
@Component(
    modules = [
        DatabaseModule::class
        // Add your modules here
    ]
)
interface AppComponent {
    fun inject(splashScreenComponent: SplashScreenComponent)
    fun inject(loginScreenComponent: LoginComponent)
    fun inject(mainScreenComponent: MainScreenComponent)
    fun inject(homeComponent: HomeComponent)
    fun inject(employResultScreenComponent: EmployResultScreenComponent)
    fun inject(addEmployeScreenComponent: AddEmployeScreenComponent)
    fun inject(departmentComponent: DepartmentComponent)
    fun inject(viewEmployeesComponent: ViewEmployeesComponent)
    fun inject(editEmployeScreenComponent: EditEmployeScreenComponent)
    fun inject(settingsComponent: SettingsComponent)
    fun inject(aboutComponent: AboutComponent)

    fun getImporter() :ImportExcelFile

    fun getLoginViewModel() :LoginViewModel
    fun getMainViewModel() : MainViewModel


}