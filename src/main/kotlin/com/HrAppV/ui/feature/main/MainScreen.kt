package com.HrAppV.ui.feature.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
fun MainScreen2(viewModel: MainViewModel,modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(Color.LightGray)) {
        Row(modifier = modifier.fillMaxSize()) {
            HomeContent(modifier)
            NavMenu(modifier)

        }
    }
}


@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    Row() {
        Spacer(modifier = Modifier.width(8.dp))

        LeftPart()
        Spacer(modifier = Modifier.width(12.dp))


        RightPart()
        Spacer(modifier = Modifier.width(8.dp))

    }


}

@Composable
private fun LeftPart() {

    Card (modifier = Modifier.size(100.dp , 250.dp)){

        Column(){
            Text("My Profile")

            Image(
                painter = painterResource("drawables/logo.png"),
                modifier = Modifier.size(50.dp),
                contentDescription = "Logo"
            )

            Text("My Profile")

        }

    }

}


@Composable
private fun RightPart() {
    Column() {
        Spacer(modifier = Modifier.height(8.dp))
        Dashboard()
        Spacer(modifier = Modifier.height(8.dp))
        Report()
        Spacer(modifier = Modifier.height(8.dp))


    }
}

@Composable
private fun Dashboard() {
    Column() {
        Text("Dashboard")
        Spacer(modifier = Modifier.height(8.dp))
        DashboardCard("Mr : mahmoud")

    }

}

@Composable
private fun DashboardCard(text: String) {
    Card() {
        Column() {
            Row() {
                Text("welcome : ")
                Text(text)
            }
            Row() {
                Text("welcome : ")
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource("drawables/logo.png"),
                    modifier = Modifier.size(100.dp),
                    contentDescription = "Logo"
                )

            }
        }


    }
}

@Composable
private fun Report() {

    Column() {
        Text("Report")
        Row() {
            Spacer(modifier = Modifier.width(20.dp))
            ReportCard()
            Spacer(modifier = Modifier.width(8.dp))
            ReportCard()
            Spacer(modifier = Modifier.width(8.dp))
            ReportCard()
            Spacer(modifier = Modifier.width(8.dp))
            ReportCard()
            Spacer(modifier = Modifier.width(20.dp))

        }
    }

}

@Composable
private fun ReportCard() {
    Card() {
        Column() {
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource("drawables/logo.png"),
                modifier = Modifier.size(100.dp),
                contentDescription = "Logo"
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text("welcome : ")
            Spacer(modifier = Modifier.height(4.dp))

            Text("welcome : ")
            Spacer(modifier = Modifier.height(12.dp))


        }
    }
}

@Composable

private fun NavMenu(modifier: Modifier = Modifier) {
    Column(modifier =  modifier.fillMaxHeight().width(100.dp)
        .background(Color.White)){

        Spacer(modifier = Modifier.height(8.dp))
        NavItem("Home" , "Home")

        Spacer(modifier = Modifier.height(8.dp))
        NavItem("Register Attends" , "Home")

        Spacer(modifier = Modifier.height(8.dp))
        NavItem("Add Employee" , "Home")

        Spacer(modifier = Modifier.height(8.dp))
        NavItem("Settings" , "Home")
        Spacer(modifier = Modifier.height(8.dp))
        NavItem("About" , "Home")


    }

}

@Composable
private fun NavItem(name:String , img :String){
    Row(modifier = Modifier.fillMaxWidth().padding(4.dp).background(Color.White)){
        Image(
            painter = painterResource("drawables/logo.png"),
            modifier = Modifier.size(25.dp),
            contentDescription = "Logo"
        )

        Text(name)
        Spacer(modifier = Modifier.width(50.dp))



    }

}