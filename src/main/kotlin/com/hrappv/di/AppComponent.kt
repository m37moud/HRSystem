package com.hrappv.di

import com.hrappv.ui.feature.EmployeResult.EditEmployeScreenComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.splash.SplashScreenComponent
import dagger.Component
import excel.ImportExcelFile
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
    fun inject(mainScreenComponent: MainScreenComponent)
    fun inject(employResultScreenComponent: EmployResultScreenComponent)
    fun inject(addEmployeScreenComponent: AddEmployeScreenComponent)
    fun inject(editEmployeScreenComponent: EditEmployeScreenComponent)

    fun getImporter() :ImportExcelFile
}