//package com.hrappv.ui.navigation
//
//import androidx.compose.runtime.Composable
//import com.arkivanov.decompose.ComponentContext
//import com.arkivanov.decompose.ExperimentalDecomposeApi
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
//import com.arkivanov.decompose.router.stack.*
//import com.arkivanov.decompose.value.Value
//import com.arkivanov.essenty.parcelable.Parcelable
//import com.hrappv.di.AppComponent
//import com.hrappv.di.DaggerAppComponent
//import com.hrappv.ui.feature.EmployeResult.EditEmployeScreenComponent
//import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
//import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
//import com.hrappv.ui.feature.home_screen.HomeComponent
//import com.hrappv.ui.feature.main.MainScreenComponent
//import com.hrappv.ui.security.UserAuthSate
//
//class DefaultRootComponent(
////    private val authenticated: UserAuthSate,
//    private val componentContext: ComponentContext,
//) : RootComponent, ComponentContext by componentContext {
//
//    /**
//     * Available screensSelectApp
//     */
//    private sealed class Config : Parcelable {
////        object Splash : Config()
//
//        //        object Login : Config()
//        object Home : Config()
//        object AddEmployeeChild : Config()
//        object EditEmployeeChild : Config()
//        object EmployeeResultChild : Config()
//        data class Main(val userAuthState: UserAuthSate) : Config()
//
//        //        object Main : Config()
//        object EmployeResult : Config()
//
//    }
//
//    private val appComponent: AppComponent = DaggerAppComponent
//        .create()
//
//    /**
//     * Router configuration
//     */
////    private val router = router<Config, Component>(
////        initialConfiguration = Config.Home,
////        childFactory = ::createScreenComponent
////    )
//
//
//    private val navigation = StackNavigation<Config>()
//    private val stack = childStack(
//        source = navigation,
//        key = "Stack",
//        initialConfiguration = Config.Home,
////        initialStack = { listOf(Config.Home) },
//        childFactory = ::child
//    )
//
////    private val stack =
////        childStack(
////            source = navigation,
////            initialStack = {  listOf(Config.Home)  },
////            childFactory = ::child,
////        )
//
//    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack
//    /**
//     * When a new navigation request made, the screen will be created by this method.
//     */
//    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
//        when (config) {
//            is Config.Home -> RootComponent.Child.HomeChild(
//                HomeComponent(
//                    appComponent,
//                    componentContext = componentContext,
//                    onBackPress = ::onBackPress
//                )
//            )
//
//            is Config.AddEmployeeChild -> RootComponent.Child.AddEmployeeChild(
//                AddEmployeScreenComponent(
//                    appComponent,
//                    componentContext = componentContext,
//                    onBackPress = ::onBackPress
//                )
//            )
//
//            is Config.EditEmployeeChild -> RootComponent.Child.EditEmployeeChild(
//                EditEmployeScreenComponent(
//                    appComponent, componentContext = componentContext
//                )
//            )
//
//            is Config.EmployeeResultChild -> RootComponent.Child.EmployeeResultChild(
//                EmployResultScreenComponent(
//                    appComponent,
//                    componentContext = componentContext,
//                    onBackPress = ::onBackPress
//
//                )
//            )
//
//            Config.EmployeResult -> TODO()
//            is Config.Main -> TODO()
//        }
//
//
//
////    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component {
////        return when (config) {
//////            is Config.Splash -> SplashScreenComponent(
//////                appComponent = appComponent,
//////                componentContext = componentContext,
//////                onSplashFinished = ::onSplashFinished,
//////            )
////
////            is Config.Home -> HomeComponent(
////                appComponent = appComponent,
////                componentContext = componentContext,
//////                userAuthState = config.userAuthState,
//////                onClickAddEmployee = ::addEmployeScreenStart,
////                onBackPress = ::onBackPress
////
////            )
////
////            is Config.EmployeResult -> EmployResultScreenComponent(
////                appComponent = appComponent,
////                componentContext = componentContext,
////                onBackClickEmpResult = ::onBackPress
////            )
////
////            is Config.Main -> MainScreenComponent(
////                appComponent = appComponent,
////                componentContext = componentContext,
////                userAuthState = config.userAuthState,
//////                onClickAddEmployee = ::addEmployeScreenStart,
////                onClickHome = ::startHomeScreen,
////                onClickEmpResult = ::startEmployScreen
////
////            )
////
////
//////            Config.Login -> LoginComponent(
//////                appComponent = appComponent,
//////                componentContext = componentContext,
//////                onUserAuthentcated = ::onUserAuthentcated,
//////            )
////
////
////        }
////    }
//
//
//    @OptIn(ExperimentalDecomposeApi::class)
//    @Composable
//    override fun render() {
////        Children(
////            routerState = router.state,
////            animation = crossfadeScale()
////        ) { child ->
////            child.instance.render()
////        }
//        Children(
//            stack = stack,
//            animation = stackAnimation(fade() + scale()),
//        ) {
////            it.instance.render()
//            when (val child = it.instance) {
//                is RootComponent.Child.HomeChild -> HomeComponent()
//                is RootComponent.Child.AddEmployeeChild -> MultiPaneContent(component = child.component, modifier = Modifier.fillMaxSize())
//                is RootComponent.Child.EditEmployeeChild -> DynamicFeaturesContent(component = child.component, modifier = Modifier.fillMaxSize())
//                is RootComponent.Child.EmployeeResultChild -> CustomNavigationContent(component = child.component, modifier.fillMaxSize())
//            }
//        }
//    }
//
//    /**
//     * Invoked when splash finish data sync
//     */
////    private fun onSplashFinished() {
////        router.replaceCurrent(Config.Main)
//////        navigation.push(Config.Login)
////    }
//
////    private fun onUserAuthentcated(
////        userAuthState: UserAuthSate
////    ) {
//////        router.replaceCurrent(Config.Main(userAuthState))
////        router.replaceCurrent(Config.Main())
//////        navigation.push(Config.Main(userAuthState))
////    }
//
//    //    private fun addEmployeScreenStart() {
////        router.replaceCurrent(Config.AddEmployee)
//////        navigation.push(Config.EmployeResult)
////    }
//    private fun startHomeScreen() {
////        router.replaceCurrent(Config.Home)
//        navigation.push(Config.EmployeResult)
//    }
//
//    private fun startEmployScreen() {
////        router.replaceCurrent(Config.EmployeResult)
//        navigation.push(Config.EmployeResult)
//    }
//
//    private fun onBackPress() {
//        navigation.pop()
////        router.replaceCurrent(Config.Main)
//
//    }
//
//
//    override fun onHomeTabClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onAddEmployeeTabClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onEditEmployeeTabClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onEmployeeResultTabClicked() {
//        TODO("Not yet implemented")
//    }
//
//    private fun getInitialStack(deepLink: DeepLink): List<Config> =
//        when (deepLink) {
//            is DeepLink.None -> listOf(Config.Home)
//            is DeepLink.Web -> listOf(Config.EmployeResult)
//        }
//
//    sealed interface DeepLink {
//        object None : DeepLink
//        class Web(val path: String) : DeepLink
//    }
//}
////
////@Composable
////fun rememberNavController(
////    context: ApplicationContext,
////    startDestination: Screens,
////    backStackScreens: MutableSet<Screens> = mutableSetOf()
////): MutableState<NavController> = rememberSaveable {
////    mutableStateOf(NavController(context, startDestination, backStackScreens))
////}