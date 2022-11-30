package com.HrAppV.ui.feature.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.HrAppV.App
import com.HrAppV.ui.value.HrAppVTheme
import com.HrAppV.ui.value.R
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
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
            .background(Color.LightGray)
            .scrollable(scrollState, Orientation.Horizontal)
    ) {
        NavMenu(Modifier.weight(1f), viewModel)
        HomeContent(Modifier.weight(4f))


    }

//    }

}


@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize().padding(8.dp),
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
        modifier.padding(top = 12.dp)
            .background(Color.White),
        elevation = 4.dp
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("My Profile", style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold))

            Column(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource("drawables/ic_user_placeholder.png"),
                    modifier = Modifier.size(100.dp)
                        .clip(CircleShape)
                        .border(shape = CircleShape, border = BorderStroke(2.dp, Color.Black)),
                    contentDescription = "Logo",
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
            .verticalScroll(rememberScrollState())
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
    Card(modifier

//        modifier = Modifier.background(MaterialTheme.colors.onPrimary)
        , elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
                .background(MaterialTheme.colors.onPrimary)
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
                     colorFilter = ColorFilter.tint(Color.White)

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
    Card(modifier, elevation = 4.dp) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                imageVector = icon,
                modifier = Modifier.size(50.dp),
                contentDescription = "Logo"
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
    Column(
        modifier = modifier.fillMaxHeight()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp)
//                .background(Color.White)
            ,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text("${App.appArgs.appName} ", style = MaterialTheme.typography.subtitle1, color = Color.Black)

            Spacer(modifier = Modifier.weight(1f))

            Image(
                imageVector = Icons.Default.Menu,
                modifier = Modifier.size(25.dp),
                contentDescription = null
            )


        }

        Spacer(modifier = Modifier.height(16.dp))
        NavItem(Icons.Default.Home, "Home", viewModel) {

        }

        Spacer(modifier = Modifier.height(8.dp))
        NavItem(Icons.Default.Person, "Add Employee", viewModel) {

        }

        Spacer(modifier = Modifier.height(8.dp))
        NavItem(Icons.Default.Edit, "Register Attends", viewModel) {
            viewModel.startEmpResultScreen()
        }

        Spacer(modifier = Modifier.height(8.dp))
        NavItem(Icons.Default.Settings, "Settings", viewModel) {

        }
        Spacer(modifier = Modifier.height(8.dp))
        NavItem(Icons.Default.Info, "About", viewModel) {

        }


    }

}

@Composable
private fun NavItem(icon: ImageVector, name: String, viewModel: MainViewModel, click: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp).background(Color.White)
            .clickable { click() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            imageVector = icon,
            modifier = Modifier.size(25.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))


        Text(name, style = MaterialTheme.typography.subtitle2, color = Color.Black)
        Spacer(modifier = Modifier.width(50.dp))


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


