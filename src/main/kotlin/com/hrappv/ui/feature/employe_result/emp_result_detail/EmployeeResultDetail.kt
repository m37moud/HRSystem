package com.hrappv.ui.feature.employe_result.emp_result_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.EmployeeResult

@Composable
fun EmpResultDetails(
    viewModel: EmployeeResultDetailViewModel,
    empResult: EmployeeResult,
    onDaysClick: () ->Unit,
    onAbsentDaysClick : () ->Unit,
    content: @Composable () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar =
    {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            title = {
                Text(text = "Employee Result")
            },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.onBackPress()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
            }
        )
    },
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 10.dp
                    ) {
                        Row(modifier = Modifier.padding(10.dp)) {
                            Image(
                                painter = painterResource("drawables/ic_user_placeholder.png"),
                                modifier = Modifier.size(100.dp)
                                    .clip(CircleShape)
                                    .border(
                                        shape = CircleShape,
                                        border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
                                    ),
                                contentDescription = "User Logo",
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                EmpText(empResult.name, width = 450.dp)
                                Spacer(modifier = Modifier.height(10.dp))
                                EmpText(empResult.department, width = 450.dp)
                            }

                        }

                    }


                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {
                                                 onDaysClick()
                        }, modifier = Modifier.weight(1f)) {
                            Text("Days")
                        }
                        OutlinedButton(onClick = {onAbsentDaysClick()}, modifier = Modifier.weight(1f)) {
                            Text("Absent Days")
                        }
                    }

                    content()
                }
            }

        })
}


@Composable
fun EmpText(text: String = "Name", width: Dp = 200.dp) {
    val modifierText = Modifier.padding(5.dp).height(35.dp)

    Card(
        elevation = 10.dp,
//        backgroundColor = Color.LightGray,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            maxLines = 1,
            textAlign = TextAlign.Center,
            text = text,

            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold),
            modifier = modifierText.width(width),
        )
    }
}
