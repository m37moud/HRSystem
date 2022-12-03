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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight


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
//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {

    Row(
        modifier = modifier
            .fillMaxSize()
//            .background(Color.LightGray)
            .scrollable(scrollState, Orientation.Horizontal)
    ) {
        NavMenu(
//            modifier.weight(1f),
            viewModel = viewModel
        )
        HomeContent(
//            modifier.weight(4f)
        )


    }

//    }

}


@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        LeftPart(Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp))


        RightPart(Modifier.weight(3f))
        Spacer(modifier = Modifier.width(8.dp))

    }


}


@Composable
private fun LeftPart(modifier: Modifier = Modifier) {

    Card(
        modifier.padding(top = 12.dp),
//            .background(Color.White)
        elevation = 6.dp,
//        border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "My Profile",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold)
            )

            Column(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
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
        modifier = modifier.fillMaxSize().padding(top = 12.dp)
//            .verticalScroll(rememberScrollState())
    )
    {
        //Dashboard
        Spacer(modifier = Modifier.height(8.dp))
        Dashboard(Modifier.weight(1f))
        Spacer(modifier = Modifier.height(12.dp))
        //Report
        Spacer(modifier = Modifier.height(8.dp))
        Report(Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(1f))


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
//            .border(
//                shape = CircleShape,
//                border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary))
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.onPrimary,
//        border = BorderStroke(2.dp, MaterialTheme.colors.onSecondary),


    ) {
        Row(
            modifier = Modifier.padding(8.dp)
//                .background(MaterialTheme.colors.onPrimary)
        ) {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                Row() {
                    Text(
                        "welcome : ",
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.subtitle1
                    )
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
        Spacer(modifier = Modifier.height(8.dp))
        Row() {
            ReportCard(Modifier.weight(1f), Icons.Default.Person, "Employ Number", "120")
            Spacer(modifier = Modifier.width(8.dp))

            ReportCard(Modifier.weight(1f), Icons.Default.Person, "Total Salary", "12")
            Spacer(modifier = Modifier.width(8.dp))

            ReportCard(Modifier.weight(1f), Icons.Default.Person, "Total Motivation", "74")
            Spacer(modifier = Modifier.width(8.dp))

            ReportCard(Modifier.weight(1f), Icons.Default.Person, "Total Additional", "45")

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
            modifier = Modifier.padding(8.dp),
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
private fun NavMenu(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    var isMenuPressed by remember { mutableStateOf(true) }

    Card(
        modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessHigh
                )
            ),
//            .background(Color.White)
        elevation = 6.dp,
//        border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = 12.dp)
//                .border(shape = RectangleShape, border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary))

//            .background(Color.White)
        ) {
            Row(
                modifier = modifier
//                    .fillMaxWidth()
                    .padding(4.dp)

//                .background(Color.White)
                ,
                verticalAlignment = Alignment.CenterVertically

            ) {

                if (isMenuPressed) {
                    Text(
                        "${App.appArgs.appName} ",
                        style = MaterialTheme.typography.subtitle1,
//                color = Color.Black
                    )

//                    Spacer(modifier = modifier.weight(1f))
                    Spacer(modifier = Modifier.width(150.dp))
                } else {
                    Spacer(modifier = modifier.width(8.dp))
                }

                Image(
                    imageVector = Icons.Default.Menu,
                    modifier = Modifier.size(25.dp).clickable { isMenuPressed = !isMenuPressed },
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)

                )


            }

            Spacer(modifier = modifier.height(16.dp))
            NavItem(modifier=modifier,icon = Icons.Default.Home, name = "Home", isMenuPressed = isMenuPressed) {

            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(modifier=modifier,icon = Icons.Default.Person, name = "Add Employee", isMenuPressed = isMenuPressed) {

            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(modifier=modifier,icon = Icons.Default.Edit, name = "Register Attends", isMenuPressed = isMenuPressed) {
                viewModel.startEmpResultScreen()
            }

            Spacer(modifier = modifier.height(8.dp))
            NavItem(modifier=modifier,icon = Icons.Default.Settings, name = "Settings", isMenuPressed = isMenuPressed) {

            }
            Spacer(modifier = modifier.height(8.dp))
            NavItem(modifier=modifier,icon = Icons.Default.Info, name = "About", isMenuPressed = isMenuPressed) {

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
            Spacer(modifier = modifier.weight(1f,false))
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


