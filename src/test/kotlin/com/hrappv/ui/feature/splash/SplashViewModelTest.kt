package com.hrappv.ui.feature.splash

import com.github.theapache64.expekt.should
import com.hrappv.test.MainCoroutineRule
import com.hrappv.test.MyDaggerMockRule
import com.hrappv.ui.feature.add_department.DepartmentViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import util.Constatnts


@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val daggerMockRule = MyDaggerMockRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val splashViewModel by lazy {
        SplashViewModel(mock()).apply {
            init(coroutineRule)
        }
    }

    private val departmentViewModel by lazy {
        DepartmentViewModel(mock()).apply {
            init(coroutineRule)
        }
    }

    @Test
    fun `Splash finished after delay`() {
        splashViewModel.isSplashFinished.value.should.`false` // Flag should be false before delay
//        coroutineRule.advanceTimeBy(SplashViewModel.SPLASH_DELAY)
        splashViewModel.isSplashFinished.value.should.`true` // Flag should be true after delay
    }

    @Test
    fun `insert`() {
//        val departmentList =  Constatnts.excelImporterDepartment("F:\\8").distinct()
//        d.insertDepart(
//            depart
//        )
//        departmentViewModel.insertDepartmentFromImporter(departmentList)
//        val items = departmentViewModel.
//        println("department ${items}")
    }

}