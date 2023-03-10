package com.hrappv.di

import com.hrappv.data.di.module.DatabaseModule
import com.hrappv.ui.feature.employe_result.EditEmployeScreenComponent
import com.hrappv.ui.feature.employe_result.register_attends.EmployResultScreenComponent
import com.hrappv.ui.feature.login.LoginComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.splash.SplashScreenComponent
import dagger.Component
import javax.inject.Singleton

import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.department.add_department.AddDepartmentComponent
import com.hrappv.ui.feature.department.DefaultDepartmentComponent
import com.hrappv.ui.feature.department.show_departments.DepartmentComponent
import com.hrappv.ui.feature.employe_result.emp_result_detail.EmployeeResultDetailComponent
import com.hrappv.ui.feature.employe_result.emp_result_detail.details_days.DaysDetailComponent
import com.hrappv.ui.feature.employe_result.emp_result_detail.absent_days.AbsentDaysDetailComponent
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
    fun inject(employeeResultDetailComponent: EmployeeResultDetailComponent)
    fun inject(daysDetailComponent: DaysDetailComponent)
    fun inject(absentDaysDetailComponent: AbsentDaysDetailComponent)
    fun inject(aboutComponent: AboutComponent)

//    fun getImporter() :ImportExcelFile

    fun getLoginViewModel() :LoginViewModel
    fun getMainViewModel() : MainViewModel


}