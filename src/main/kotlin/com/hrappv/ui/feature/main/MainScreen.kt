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
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
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
        modifier = modifier
//                .fillMaxSize()
//            .background(Color.LightGray)
//                .scrollable(scrollState, Orientation.Horizontal)
    ) {
        NavMenu(
            isMenuPressed = isMenuPressed,
//            modifier.weight(1f),
            viewModel = viewModel
        ) {
            coroutineScope.launch {
//                    scaffoldState.drawerState.open()
                isMenuPressed = !isMenuPressed


            }
        }
        HomeContent(
//            modifier.weight(4f)
        )


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
            image =Icons.Default.Settings,
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
private fun HomeContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        LeftPart(
//            modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))


        RightPart(
//            modifier.weight(3f)
        )
        Spacer(modifier = Modifier.width(8.dp))

    }


}


@Composable
private fun LeftPart(modifier: Modifier = Modifier) {

    Card(
        modifier.padding(top = 10.dp),
//            .background(Color.White)
        elevation = 6.dp,
//        border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
    ) {

        Column(
            modifier = modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "My Profile",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold)
            )

            Column(
                modifier = modifier.padding(start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource("drawables/ic_user_placeholder.png"),
                    modifier = Modifier.size(100.dp)
                        .clip(CircleShape)
                        .border(
                            shape = CircleShape,
                            border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
                        ),
                    contentDescription = "User Logo",
//                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                )

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    "Mr-Mahmoud Aly",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    maxLines = 1, style = MaterialTheme.typography.subtitle1
                )

            }

            Spacer(modifier = Modifier.height(300.dp))


        }


    }


}


@Composable
private fun RightPart(modifier: Modifier = Modifier) {
//    Surface (modifier = modifier.padding(top = 12.dp)) {
    Column(
        modifier = modifier.padding(top = 12.dp, end = 12.dp)
//            .verticalScroll(rememberScrollState())
    )
    {
        //Dashboard
        Spacer(modifier = modifier.height(8.dp))
        Dashboard(
//            modifier.weight(1f)
        )
        Spacer(modifier = modifier.height(12.dp))
        //Report
        Spacer(modifier = modifier.height(8.dp))
        Report(
//            modifier.weight(1f)
        )

//        Spacer(modifier = modifier.weight(1f))


    }
//    }
}

@Composable
private fun Dashboard(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 20.dp)) {

        Text("Dashboard", style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold))
        Spacer(modifier = Modifier.height(8.dp))
        DashboardCard(text = "Mr/mahmoud")

    }

}

@Composable
private fun DashboardCard(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier,
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.onPrimary,
//        border = BorderStroke(2.dp, MaterialTheme.colors.onSecondary),


    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                Row() {
                    Text(
                        "welcome : ",
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text, color = MaterialTheme.colors.onSecondary, style = MaterialTheme.typography.subtitle1)

                }
                Row() {
                    Text(
                        "have a nice day at work ",
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.subtitle2
                    )

                }

            }
            Spacer(modifier = Modifier.weight(1f))

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Spacer(modifier = Modifier.height(70.dp))
                Image(
//                    painter = painterResource("drawables/logo.png"),
                    imageVector = Icons.TwoTone.Favorite,
                    modifier = Modifier.size(70.dp),
                    contentDescription = "Logo",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSecondary)

                )
                Spacer(modifier = Modifier.height(8.dp))

            }


        }


    }
}

@Composable
private fun Report(modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(start = 20.dp)) {
        Text("Report", style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold))
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ReportCard(modifier.weight(1f), Icons.Default.Person, "Employ Number", "120")
//            Spacer(modifier = modifier.width(8.dp))

            ReportCard(modifier.weight(1f), Icons.Default.Person, "Total Salary", "12")
//            Spacer(modifier = modifier.width(8.dp))

            ReportCard(modifier.weight(1f), Icons.Default.Person, "Total Motivation", "74")
//            Spacer(modifier = modifier.width(8.dp))

            ReportCard(modifier.weight(1f), Icons.Default.Person, "Total Additional", "45")

        }
    }

}

@Composable
private fun ReportCard(modifier: Modifier = Modifier, icon: ImageVector, name: String, number: String) {
    Card(
        modifier, elevation = 6.dp,
//        border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
    ) {
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                imageVector = icon,
                modifier = Modifier.size(50.dp),
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(name, maxLines = 1, style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(4.dp))

            Text(number, maxLines = 1, style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(12.dp))


        }
    }
}

@Composable
private fun NavMenu(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    isMenuPressed: Boolean,
    onNavIconClick: () -> Unit
) {
//    var isMenuPressed by remember { mutableStateOf(true) }

    Card(
        Modifier
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

                    Spacer(modifier = Modifier.width(150.dp))
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
//        MainScreen2()

    }
}


