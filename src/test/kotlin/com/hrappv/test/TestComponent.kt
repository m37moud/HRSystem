package com.hrappv.test

import com.hrappv.data.di.module.DatabaseModule
import com.hrappv.data.di.module.MyModule
import com.hrappv.data.repo.MyRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MyModule::class,
      DatabaseModule::class
        // Add your modules here
    ]
)
interface TestComponent {
    fun myRepo(): MyRepo
}