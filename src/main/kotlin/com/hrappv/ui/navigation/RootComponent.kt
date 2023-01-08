package com.hrappv.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.hrappv.ui.feature.EmployeResult.EditEmployeScreenComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
import com.hrappv.ui.feature.home_screen.HomeComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onHomeTabClicked()
    fun onAddEmployeeTabClicked()
    fun onEditEmployeeTabClicked()
    fun onEmployeeResultTabClicked()

    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class AddEmployeeChild(val component: AddEmployeScreenComponent) : Child()
        class EditEmployeeChild(val component: EditEmployeScreenComponent) : Child()
        class EmployeeResultChild(val component: EmployResultScreenComponent) : Child()
    }
    @Composable
    fun render()
}