package com.HrAppV.di

import com.HrAppV.ui.feature.EmployeResult.EmployResultScreenComponent
import com.HrAppV.ui.feature.main.MainScreenComponent
import com.HrAppV.ui.feature.splash.SplashScreenComponent
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
    fun inject(employeScreenComponent: EmployResultScreenComponent)

    fun getImporter() :ImportExcelFile
}