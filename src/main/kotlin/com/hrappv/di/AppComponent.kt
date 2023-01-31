package com.hrappv.di

import com.hrappv.data.di.module.DatabaseModule
import com.hrappv.ui.feature.EmployeResult.EditEmployeScreenComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.login.LoginComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.splash.SplashScreenComponent
import dagger.Component
import excel.ImportExcelFile
import javax.inject.Singleton

import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.department.add_department.AddDepartmentComponent
import com.hrappv.ui.feature.department.DefaultDepartmentComponent
import com.hrappv.ui.feature.department.show_departments.DepartmentComponent
import com.hrappv.ui.feature.home_screen.HomeComponent
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.feature.main.MainViewModel
import com.hrappv.ui.feature.settings.SettingsComponent
import com.hrappv.ui.feature.employees.view_employees.ViewEmployeesComponent
import com.hrappv.ui.feature.employees.add_Employee.AddEmployeScreenComponent
import com.hrappv.ui.feature.employees.employee_details.EmployeeDetailsComponent

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
    fun inject(employeeDetailsComponent: EmployeeDetailsComponent)

    fun inject(employResultScreenComponent: EmployResultScreenComponent)
    fun inject(addEmployeScreenComponent: AddEmployeScreenComponent)
    fun inject(defaultDepartmentComponent: DefaultDepartmentComponent)
    fun inject(departmentComponent: DepartmentComponent)
    fun inject(addDepartmentComponent: AddDepartmentComponent)
    fun inject(viewEmployeesComponent: ViewEmployeesComponent)
    fun inject(editEmployeScreenComponent: EditEmployeScreenComponent)
    fun inject(settingsComponent: SettingsComponent)
    fun inject(aboutComponent: AboutComponent)

//    fun getImporter() :ImportExcelFile

    fun getLoginViewModel() :LoginViewModel
    fun getMainViewModel() : MainViewModel


}