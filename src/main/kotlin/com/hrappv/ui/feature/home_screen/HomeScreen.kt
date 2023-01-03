package com.hrappv.ui.feature.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
 fun HomeContent(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        LeftPart(
            "name"
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
private fun LeftPart(name: String, modifier: Modifier = Modifier) {

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
                "$name",
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
                    "Mr-$name",
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
