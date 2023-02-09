package com.hrappv.ui.feature.main

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrappv.App
import com.hrappv.ui.security.UserAuthSate
import com.hrappv.ui.value.HrAppVTheme
import com.hrappv.ui.value.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import com.hrappv.ui.components.AppMenuHeader
import com.hrappv.ui.components.NavigationMenuItem
import com.hrappv.ui.feature.employe_result.register_attends.EmployResultScreenComponent
import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.department.DefaultDepartmentComponent
import com.hrappv.ui.feature.employees.DefaultViewEmpComponent
import com.hrappv.ui.feature.home_screen.HomeComponent
import com.hrappv.ui.feature.settings.SettingsComponent
import com.hrappv.ui.navigation.Component


@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val welcomeText by viewModel.welcomeText.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = welcomeText,
                style = MaterialTheme.typography.h3
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Button(
                onClick = {
                    viewModel.onClickMeClicked()
                }
            ) {
                Text(text = R.string.ACTION_MAIN_CLICK_ME)
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Button(
                onClick = {
                    viewModel.startEmpResultScreen()
                }
            ) {
                Text(text = R.string.ACTION_EMPLOYEE_RESULT)
            }
        }
    }
}

@Preview
@Composable
fun MainScreen2(
    modifier: Modifier = Modifier,
    userAuthSate: UserAuthSate = UserAuthSate(),
    activeComponent: Component,
    onNavIconClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDepartmentClick: () -> Unit,
//    onAddEmployeeClick: () -> Unit,
    onViewEmployeesClick: () -> Unit,
    onEmployeeResultClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLogoutClick: () -> Unit,

    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState(0)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
//    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    var isMenuPressed by remember { mutableStateOf(false) }
//    val navMenu = modifier.weight(0.2f)
//    val navContent = modifier.weight(0.8f)
//    Surface(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//    , elevation = 6.dp
//    ) {

//    Scaffold(
//        modifier
////            .animateContentSize(
////                animationSpec = spring(
////                    dampingRatio = Spring.DampingRatioHighBouncy,
////                    stiffness = Spring.StiffnessHigh
////                )
////            )
//        , scaffoldState = scaffoldState,
////        topBar = {
////            MyTopAppBar {
////                coroutineScope.launch {
//////                    scaffoldState.drawerState.open()
////                }
////            }
////        },
////        drawerContent = {
////            DrawerContent { itemLabel ->
////
////                coroutineScope.launch {
////                    // delay for the ripple effect
////                    delay(timeMillis = 250)
////                    scaffoldState.drawerState.close()
////                }
////            }
////        }
//
////        drawerContent = {
////            Text("Drawer title", modifier = Modifier.padding(16.dp))
////            Divider()
////
////            // Drawer items
////        }
//        drawerGesturesEnabled = false
//    ) {
    Surface {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            Card(
                modifier.shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                )
                    .animateContentSize(),
                shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp), elevation = 10.dp,

                )
            {
                NavMenu(
//                modifier = Modifier
//                    .weight(0.15f),
                    isMenuPressed = isMenuPressed,
                    activeComponent = activeComponent,
                    onNavIconClick = {
//                coroutineScope.launch {
//                    scaffoldState.drawerState.open()
                        isMenuPressed = !isMenuPressed


//                }
                    },
                    onHomeClick = onHomeClick,
                    onDepartmentClick = onDepartmentClick,
//            onAddEmployeeClick = onAddEmployeeClick,
                    onViewEmployeesClick = onViewEmployeesClick,
                    onEmployeeResultClick = onEmployeeResultClick,
                    onSettingsClick = onSettingsClick,
                    onAboutClick = onAboutClick,
                    onLogoutClick = onLogoutClick
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

//            Box(
////                modifier = Modifier.fillMaxHeight()
////                    .weight(0.85f)
//            ) {
                Surface{

                    content()

                }
//            }
        }
    }


//    }

}

@Composable
private fun MyTopAppBar(onNavIconClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "SemicolonSpace") },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Navigation Drawer"
                )
            }
        }
    )
}

@Composable
private fun DrawerContent(
    gradientColors: List<Color> = listOf(Color(0xFFF70A74), Color(0xFFF59118)),
    itemClick: (String) -> Unit
) {

    val itemsList = prepareNavigationDrawerItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {

        item {

            // user's image
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource("drawables/logo.png"),
                contentDescription = "Profile Image"
            )

            // user's name
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = "Hermione",
                fontSize = 26.sp,
//                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // user's email
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
                text = "hermione@email.com",
//                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        items(itemsList) { item ->
            NavigationListItem(item = item) {
                itemClick(item.label)
            }
        }
    }
}


@Composable
private fun NavigationListItem(
    item: NavigationDrawerItem,
    unreadBubbleColor: Color = Color(0xFF0FFF93),
    itemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                itemClick()
            }
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // icon and unread bubble
        Box {

            Icon(
                modifier = Modifier
                    .padding(all = if (item.showUnreadBubble && item.label == "Messages") 5.dp else 2.dp)
                    .size(size = if (item.showUnreadBubble && item.label == "Messages") 24.dp else 28.dp),
                imageVector = item.image,
                contentDescription = null,
                tint = Color.White
            )

            // unread bubble
            if (item.showUnreadBubble) {
                Box(
                    modifier = Modifier
                        .size(size = 8.dp)
                        .align(alignment = Alignment.TopEnd)
                        .background(color = unreadBubbleColor, shape = CircleShape)
                )
            }
        }

        // label
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = item.label,
            fontSize = 20.sp,
//            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Composable
private fun prepareNavigationDrawerItems(): List<NavigationDrawerItem> {
    val itemsList = arrayListOf<NavigationDrawerItem>()

    itemsList.add(
        NavigationDrawerItem(
            image = Icons.Default.Home,
            label = "Home"
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = Icons.Default.Person,
            label = "Add Employee",
            showUnreadBubble = true
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = Icons.Default.Edit,
            label = "Register Attends",
            showUnreadBubble = true
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = Icons.Default.Settings,
            label = "Settings"
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = Icons.Default.Info,
            label = "About"
        )
    )
//
//    itemsList.add(
//        NavigationDrawerItem(
//            image = painterResource("drawables/logo.png"),
//            label = "Settings"
//        )
//    )

    itemsList.add(
        NavigationDrawerItem(
            image = Icons.TwoTone.AccountCircle,
            label = "Logout"
        )
    )

    return itemsList
}

data class NavigationDrawerItem(
    val image: ImageVector,
    val label: String,
    val showUnreadBubble: Boolean = false
)


@Composable
fun NavMenu(
    modifier: Modifier = Modifier,
    isMenuPressed: Boolean,
    activeComponent: Component,
    onNavIconClick: () -> Unit,
    onHomeClick: () -> Unit,
    onDepartmentClick: () -> Unit,
//    onAddEmployeeClick: () -> Unit,
    onViewEmployeesClick: () -> Unit,
    onEmployeeResultClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLogoutClick: () -> Unit,

    ) {
    NavigationRail(
        modifier = modifier
//            .shadow(
//            elevation = 10.dp,
//            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
//        ) //.width(IntrinsicSize.Max)

//            .border(shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)//RectangleShape
//               , border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
//            ).padding(4.dp)

            .fillMaxHeight()
//            .padding(end = 6.dp)
            .animateContentSize(),
//        elevation = 10.dp,
        header = {
            Row(
                modifier = modifier
//                    .fillMaxWidth()
                    .padding(start = 6.dp,end = 6.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {

                if (isMenuPressed) {
                    Text(
                        "${App.appArgs.appName} ",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1,
                    )

                    Spacer(modifier = modifier.width(120.dp))
                } else {
                    Spacer(modifier = modifier.width(8.dp))
                }
                Crossfade(targetState = isMenuPressed) { isMenuPressed ->
                    if (isMenuPressed) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            modifier = Modifier.size(25.dp).clickable {
                                onNavIconClick()
                            },
                            contentDescription = null,
//                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                            tint = MaterialTheme.colors.onPrimary

                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            modifier = Modifier.size(25.dp).clickable {
                                onNavIconClick()
                            },
                            contentDescription = null,
//                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                            tint = MaterialTheme.colors.onPrimary

                        )
                    }
                }
//                Icon(
//                    imageVector = if(isMenuPressed) Icons.Outlined.ArrowForward else Icons.Outlined.ArrowBack,
//                    modifier = Modifier.size(25.dp).clickable {
//                        onNavIconClick()
//                    },
//                    contentDescription = null,
////                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
//                    tint = MaterialTheme.colors.onPrimary
//
//                )

                Spacer(modifier = Modifier.width(8.dp))


            }
        },
        content = {
//        Card(
//            modifier
//                .animateContentSize(),
//            elevation = 6.dp,
//        ) {

            AppMenuHeader()


//


            Column(
                modifier = modifier.width(IntrinsicSize.Max)
                    .fillMaxHeight()
//                    .padding(top = 16.dp)
//                .border(shape = RectangleShape, border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary))

            ) {
                Divider(
                    startIndent = 0.dp,
                    thickness = 2.dp,
                    color = MaterialTheme.colors.onPrimary,
                )
                Spacer(modifier = Modifier.height(8.dp))

//            Spacer(
//                modifier = Modifier
//                    .height(4.dp)
//                    .fillMaxWidth()
//                    .background(color = MaterialTheme.colors.onPrimary)
//                    .padding(bottom = 4.dp),
//                )
//                Spacer(modifier = modifier.height(16.dp))
                NavigationMenuItem(
                    selected = activeComponent is HomeComponent,
                    modifier = modifier.fillMaxWidth(),
                    icon = Icons.Default.Home,
                    label = "Home",
                    isMenuPressed = isMenuPressed
//                component.onHomeTabClicked()
                    ,
                    onClick = { onHomeClick() }
                )
                Spacer(modifier = modifier.height(8.dp))


                NavigationMenuItem(
                    selected = activeComponent is DefaultDepartmentComponent,
                    modifier = modifier.fillMaxWidth(),
                    icon = Icons.Default.FavoriteBorder,
                    label = "Department",
                    isMenuPressed = isMenuPressed
//                component.onHomeTabClicked()
                    ,
                    onClick = { onDepartmentClick() }
                )


//                Spacer(modifier = modifier.height(8.dp))
//                NavigationMenuItem(
//                    selected = activeComponent is AddEmployeScreenComponent,
//
//                    modifier = modifier.fillMaxWidth(),
//                    icon = Icons.Default.AddCircle,
//                    label = "Add Employee",
//                    isMenuPressed = isMenuPressed,
//                    onClick = { onAddEmployeeClick() }
//                )

                Spacer(modifier = modifier.height(8.dp))
                NavigationMenuItem(
                    selected = activeComponent is DefaultViewEmpComponent,

                    modifier = modifier.fillMaxWidth(),
                    icon = Icons.Default.Person,
                    label = "Employees",
                    isMenuPressed = isMenuPressed,
                    onClick = { onViewEmployeesClick() }
                )

                Spacer(modifier = modifier.height(8.dp))
                NavigationMenuItem(
                    selected = activeComponent is EmployResultScreenComponent,

                    modifier = modifier.fillMaxWidth(),
                    icon = Icons.Default.Edit,
                    label = "Register Attends",
                    isMenuPressed = isMenuPressed,
                    onClick = { onEmployeeResultClick() }

                )

                Spacer(modifier = modifier.height(8.dp))
                NavigationMenuItem(
                    selected = activeComponent is SettingsComponent,

                    modifier = modifier.fillMaxWidth(),
                    icon = Icons.Default.Settings,
                    label = "Settings",
                    isMenuPressed = isMenuPressed,
                    onClick = { onSettingsClick() }
                )
                Spacer(modifier = modifier.height(8.dp))
                NavigationMenuItem(
                    selected = activeComponent is AboutComponent,

                    modifier = modifier.fillMaxWidth(),
                    icon = Icons.Default.Info,
                    label = "About",
                    isMenuPressed = isMenuPressed,
                    onClick = { onAboutClick() })

//                Spacer(modifier = modifier.height(8.dp))
//                NavigationMenuItem(
//                    modifier = modifier,
//                    icon = FontAwesomeIcons.Solid.SignOutAlt,
//                    label = "Log Out",
//                    isMenuPressed = isMenuPressed, onClick = { onLogoutClick() }
//                )


            }
//        }

        }
    )

}


//@Composable
//fun spotifyGradient() = LinearGradient(spotifyGradient, startX = 0f, endX = 0f, startY = 0f, endY = 100f)
//


@Composable
@Preview
fun FavoriteCollectionCardPreview() {
    val darkTheme by remember { mutableStateOf(false) }
    HrAppVTheme(darkTheme) {

    }
}

