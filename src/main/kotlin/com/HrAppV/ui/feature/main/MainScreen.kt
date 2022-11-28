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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.HrAppV.App
import com.HrAppV.ui.value.HrAppVTheme
import com.HrAppV.ui.value.R


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
    Row(modifier = modifier.fillMaxSize().background(Color.LightGray)) {
        NavMenu(modifier.weight(1f), viewModel)
        HomeContent(modifier.weight(4f))


    }
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
//            horizontalAlignment = Alignment.Start
        ) {
            Text("My Profile")

            Column(
                modifier = Modifier.padding(start = 24.dp,end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource("drawables/logo.png"),
                    modifier = Modifier.size(50.dp),
                    contentDescription = "Logo",


                    )

                Text("Mr-Mahmoud Aly", modifier = Modifier.align(Alignment.CenterHorizontally))

            }

            Spacer(modifier = Modifier.height(300.dp))


        }


    }


}


@Composable
private fun RightPart(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(4.dp)) {
        //Dashboard
        Spacer(modifier = Modifier.height(8.dp))
        Dashboard(Modifier.weight(1f))

        //Report
        Spacer(modifier = Modifier.height(8.dp))
        Report(Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(2f))


    }
}

@Composable
private fun Dashboard(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 20.dp)) {
        Text("Dashboard")
        Spacer(modifier = Modifier.height(8.dp))
        DashboardCard("Mr : mahmoud")

    }

}

@Composable
private fun DashboardCard(text: String) {
    Card(modifier = Modifier.background(Color.Black), elevation = 4.dp) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(4.dp).weight(2f)) {
                Row() {
                    Text("welcome : ", style = MaterialTheme.typography.subtitle1)
                    Text(text, style = MaterialTheme.typography.subtitle1.copy())

                }
                Row() {
                    Text("have a nice day at work ")

                }

            }
            Column(modifier = Modifier.padding(4.dp).weight(1f)) {
                Spacer(modifier = Modifier.height(70.dp))
                Image(
//                    painter = painterResource("drawables/logo.png"),
                    imageVector = Icons.TwoTone.Favorite,
                    modifier = Modifier.size(70.dp),
                    contentDescription = "Logo",

                    )
            }


        }


    }
}

@Composable
private fun Report(modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(start = 20.dp)) {
        Text("Report")
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

            Text(name, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))

            Text(number, maxLines = 1)
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
                .background(Color.White),
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

@Composable
@Preview
fun FavoriteCollectionCardPreview() {
    val darkTheme by remember { mutableStateOf(false) }
    HrAppVTheme(darkTheme) {
//        MainScreen2()

    }
}