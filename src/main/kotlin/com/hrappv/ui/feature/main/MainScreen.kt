package com.hrappv.ui.feature.main

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hrappv.App
import com.hrappv.ui.value.HrAppVTheme
import com.hrappv.ui.value.R
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.security.UserAuthSate
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.DoorOpen
import compose.icons.fontawesomeicons.solid.Key
import compose.icons.fontawesomeicons.solid.SignOutAlt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
    viewModel: MainViewModel,
//    loginViewModel: LoginViewModel,
    userAuthSate: UserAuthSate,
    content : @Composable ()->Unit
) {
    val scrollState = rememberScrollState(0)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
//    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    var isMenuPressed by remember { mutableStateOf(false) }
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

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        NavMenu(
//                modifier = Modifier
//                    .weight(0.15f),
            viewModel = viewModel,
            isMenuPressed = isMenuPressed
//                navController
        ) {
            coroutineScope.launch {
//                    scaffoldState.drawerState.open()
                isMenuPressed = !isMenuPressed


            }
        }
        Box(
//                modifier = Modifier.fillMaxHeight()
//                    .weight(0.85f)
        ) {
            content()
//            HomeContent(
//                name = userAuthSate.username,
//            )
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
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // user's email
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
                text = "hermione@email.com",
                fontWeight = FontWeight.Normal,
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
            fontWeight = FontWeight.Medium,
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
private fun NavMenu(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    isMenuPressed: Boolean,
//    loginViewModel: LoginViewModel,
    onNavIconClick: () -> Unit
) {
//    var isMenuPressed by remember { mutableStateOf(true) }

    Card(
        modifier
            .animateContentSize(),
        elevation = 6.dp,
    ) {

        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = 20.dp)
//                .border(shape = RectangleShape, border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary))

        ) {
            Row(
                modifier = modifier
//                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {

                if (isMenuPressed) {
                    Text(
                        "${App.appArgs.appName} ",
                        style = MaterialTheme.typography.subtitle1,
                    )

                    Spacer(modifier = modifier.width(150.dp))
                } else {
                    Spacer(modifier = modifier.width(8.dp))
                }

                Image(
                    imageVector = Icons.Default.Menu,
                    modifier = Modifier.size(25.dp).clickable {
                        onNavIconClick()
                    },
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)

                )


            }

            Spacer(modifier = modifier.height(16.dp))
            NavItem(modifier = modifier, icon = Icons.Default.Home, name = "Home", isMenuPressed = isMenuPressed) {
                viewModel.startHomeScreen()

            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(
                modifier = modifier,
                icon = Icons.Default.Person,
                name = "Add Employee",
                isMenuPressed = isMenuPressed
            ) {

            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(
                modifier = modifier,
                icon = Icons.Default.Edit,
                name = "Register Attends",
                isMenuPressed = isMenuPressed
            ) {
                viewModel.startEmpResultScreen()
            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(
                modifier = modifier,
                icon = Icons.Default.Settings,
                name = "Settings",
                isMenuPressed = isMenuPressed
            ) {

            }
            Spacer(modifier = modifier.height(8.dp))
            NavItem(modifier = modifier, icon = Icons.Default.Info, name = "About", isMenuPressed = isMenuPressed) {

            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(
                modifier = modifier,
                icon = FontAwesomeIcons.Solid.SignOutAlt,
                name = "Log Out",
                isMenuPressed = isMenuPressed
            ) {
//                loginViewModel.logOut()
            }


        }
    }


}

@Composable
private fun NavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    name: String,
    isMenuPressed: Boolean,
    click: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable { click() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = modifier.width(8.dp))
        Image(
            imageVector = icon,
            modifier = Modifier.size(25.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
        Spacer(modifier = modifier.width(8.dp))

        if (isMenuPressed) {
            Text(
                name, style = MaterialTheme.typography.subtitle2,
            )
            Spacer(modifier = modifier.weight(1f, false))
        }


    }

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

