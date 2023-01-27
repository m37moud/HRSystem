package com.hrappv.ui.navigation

import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.hrappv.di.AppComponent
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.arkivanov.decompose.*
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.department.DefaultDepartmentComponent
import com.hrappv.ui.feature.employees.DefaultViewEmpComponent
import com.hrappv.ui.feature.home_screen.HomeComponent
import com.hrappv.ui.feature.main.MainScreen2
import com.hrappv.ui.feature.settings.SettingsComponent
import com.hrappv.ui.feature.employees.view_employees.ViewEmployeesComponent
import kotlinx.coroutines.launch

/**
 * All navigation decisions are made from here
 */
class NavHostComponent(
//    private val authenticated: UserAuthSate,
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {


    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    /**
     * Router configuration
     */
//    private val router = router<Config, Component>(
//        initialConfiguration = Config.Home,
//        childFactory = ::createScreenComponent
//    )
    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        key = "Stack",
        initialConfiguration = Config.Home,
        childFactory = ::createScreenComponent
    )


    /**
     * When a new navigation request made, the screen will be created by this method.
     */
    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component {
        return when (config) {
//            is Config.Splash -> SplashScreenComponent(
//                appComponent = appComponent,
//                componentContext = componentContext,
//                onSplashFinished = ::onSplashFinished,
//            )

            is Config.Home -> HomeComponent(
                appComponent = appComponent,
                componentContext = componentContext,
//                userAuthState = config.userAuthState,
                onBackPress = ::onBackPress

            )

            is Config.MainDepartment -> DefaultDepartmentComponent(
                appComponent = appComponent,
                componentContext = componentContext,
//                userAuthState = config.userAuthState,
//                onClickAddEmployee = ::addEmployeScreenStart,
                onBackPress = ::onBackPress

            )

//            is Config.AddEmployee -> AddEmployeScreenComponent(
//                appComponent = appComponent,
//                componentContext = componentContext,
////                userAuthState = config.userAuthState,
////                onClickAddEmployee = ::addEmployeScreenStart,
//                onBackPress = ::onBackPress
//
//            )

            is Config.Employees -> DefaultViewEmpComponent(
                appComponent = appComponent,
                componentContext = componentContext,
//                userAuthState = config.userAuthState,
//                onClickAddEmployee = ::addEmployeScreenStart,
                onBackPress = ::onBackPress

            )

            is Config.RegisterAttends -> EmployResultScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress
            )

            is Config.Settings -> SettingsComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )

            is Config.About -> AboutComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )

            is Config.Main -> MainScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                userAuthState = config.userAuthState,
//                onClickAddEmployee = ::addEmployeScreenStart,
                onClickHome = ::startHomeScreen,
                onClickEmpResult = ::startRegisterAttendsScreen

            )


        }
    }


    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val coroutineScope = rememberCoroutineScope()

        var isMenuPressed by remember { mutableStateOf(false) }
        val childStack: Value<ChildStack<*, Component>> = stack
        val child = childStack.subscribeAsState()
        val activeComponent = child.value.active.instance


//        Children(
//            routerState = router.state,
//            animation = crossfadeScale()
//        ) { child ->
//            child.instance.render()
//        }
        MainScreen2(
//                modifier = Modifier
//                    .weight(0.15f),
            activeComponent = activeComponent,
            onNavIconClick = {
                coroutineScope.launch {
//                    scaffoldState.drawerState.open()
                    isMenuPressed = !isMenuPressed


                }
            },
            onHomeClick = { startHomeScreen() },
            onDepartmentClick = { startDepartmentScreen() },
//            onAddEmployeeClick = ::startAddEmployeeScreen,
            onViewEmployeesClick = ::startViewEmployeeScreen,
            onEmployeeResultClick = ::startRegisterAttendsScreen,
            onSettingsClick = ::startSettingsScreen,
            onAboutClick = ::startAboutScreen,
            onLogoutClick = {},
        ) {
            Children(
                stack = stack,
                animation = tabAnimation() //stackAnimation(), //fade() + scale()
            ) { child ->

                child.instance.render()

            }
        }

    }

    /**
     * Invoked when splash finish data sync
     */
//    private fun onSplashFinished() {
//        router.replaceCurrent(Config.Main)
////        navigation.push(Config.Login)
//    }

//    private fun onUserAuthentcated(
//        userAuthState: UserAuthSate
//    ) {
////        router.replaceCurrent(Config.Main(userAuthState))
//        router.replaceCurrent(Config.Main())
////        navigation.push(Config.Main(userAuthState))
//    }
    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    private fun tabAnimation(): StackAnimation<Any, Component> =
        stackAnimation { child, otherChild, direction ->
            val index = child.instance.index
            val otherIndex = otherChild.instance.index
            val anim = slide()
            if ((index > otherIndex) == direction.isEnter) anim else anim.flipSide()
        }

    private val Component.index: Int
        get() =
            when (this) {
                is HomeComponent -> 0
                is DefaultDepartmentComponent -> 1
//                is AddEmployeScreenComponent -> 2
                is ViewEmployeesComponent -> 2
                is EmployResultScreenComponent -> 3
                is SettingsComponent -> 4
                is AboutComponent -> 5
                is MainScreenComponent -> 6
                else -> {0}
            }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun StackAnimator.flipSide(): StackAnimator =
        StackAnimator { direction, onFinished, content ->
            invoke(
                direction = direction.flipSide(),
                onFinished = onFinished,
                content = content,
            )
        }

    @Suppress("OPT_IN_USAGE")
    private fun Direction.flipSide(): Direction =
        when (this) {
            Direction.ENTER_FRONT -> Direction.ENTER_BACK
            Direction.EXIT_FRONT -> Direction.EXIT_BACK
            Direction.ENTER_BACK -> Direction.ENTER_FRONT
            Direction.EXIT_BACK -> Direction.EXIT_FRONT
        }

//    private fun addEmployeScreenStart() {
//        navigation.bringToFront(Config.AddEmployee)
////        navigation.push(Config.EmployeResult)
//    }

    private fun startHomeScreen() {
//        router.replaceCurrent(Config.Home)
        navigation.bringToFront(Config.Home)
    }

    private fun startDepartmentScreen() {
//        router.replaceCurrent(Config.Home)
        navigation.bringToFront(Config.MainDepartment)
    }

//    private fun startAddEmployeeScreen() {
////        router.replaceCurrent(Config.Home)
//        navigation.bringToFront(Config.AddEmployee)
//    }

    private fun startViewEmployeeScreen() {
//        router.replaceCurrent(Config.Home)
        navigation.bringToFront(Config.Employees)
    }

    private fun startRegisterAttendsScreen() {
//        router.replaceCurrent(Config.EmployeResult)
        navigation.bringToFront(Config.RegisterAttends)
    }

    private fun startSettingsScreen() {
//        router.replaceCurrent(Config.EmployeResult)
        navigation.bringToFront(Config.Settings)
    }

    private fun startAboutScreen() {
//        router.replaceCurrent(Config.EmployeResult)
        navigation.bringToFront(Config.About)
    }

    private fun onBackPress() {
        navigation.pop()
//        router.replaceCurrent(Config.Main)

    }

    lateinit var m: () -> Unit

    fun logout(onLogOut: () -> Unit) {
        m = onLogOut
    }
}
//
//@Composable
//fun rememberNavController(
//    context: ApplicationContext,
//    startDestination: Screens,
//    backStackScreens: MutableSet<Screens> = mutableSetOf()
//): MutableState<NavController> = rememberSaveable {
//    mutableStateOf(NavController(context, startDestination, backStackScreens))
//}