package com.bahakuzudisli.todolist

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bahakuzudisli.todolist.entity.Notes
import com.bahakuzudisli.todolist.viewmodelfactory.NoteDetayViewModelFactory
import com.bahakuzudisli.todolistapp.viewmodel.NoteDetayViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteDetaySayfa(note: Notes,navController: NavController) {

    val tfNote = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val viewModel: NoteDetayViewModel = viewModel(
        factory = NoteDetayViewModelFactory(context.applicationContext as Application)
    )
    val localFocusManager = LocalFocusManager.current

    val textWithState = remember {
        mutableStateOf("HATIRLATICI")
    }
    LaunchedEffect(key1 = true) {
        tfNote.value = note.note
        textWithState.value=" Saat ${note.time}'de/da ${note.date}"
    }



    //date ve time state kodları

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    var flag = remember {
        mutableStateOf(0)
    }



    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "GÖREV GÜNCELLE",
                    color = Color.White,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )
            },navigationIcon = {
                IconButton(onClick = {
                    if(note.is_done==0)
                    navController.navigate("Anasayfa")
                    else
                        navController.navigate("NoteDoneSayfa")
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_resim),
                        contentDescription = "", tint = Color.White
                    )
                }
            },

            backgroundColor = colorResource(
                id = R.color.orangeLight
            )

        )
    },backgroundColor = colorResource(id = R.color.orangeDark),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                TextField(
                    modifier = Modifier.padding(all = 30.dp), value = tfNote.value,
                    onValueChange = { tfNote.value = it },
                    label = {
                        Text(text = "Not", color = colorResource(id = R.color.white),
                        fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                    , textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        textColor = colorResource(id = R.color.white),
                        backgroundColor = colorResource(id = R.color.orange)
                    ),

                )
                Card(
                    modifier = Modifier
                        .width(280.dp),
                    backgroundColor = colorResource(id = R.color.orange)
                ) {
                    Row(

                    ) {

                    IconButton(onClick = {
                        dateDialogState.show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.bell_2),
                            contentDescription = "", tint = colorResource(id = R.color.white)
                        )
                    }

                    TextButton(modifier = Modifier.width(180.dp),
                        onClick = {
                        dateDialogState.show()
                    }
                    ) {
                        Text(
                            text = textWithState.value,
                            color = colorResource(id = R.color.white),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    if (flag.value == 1) {
                        IconButton(onClick = {
                            flag.value = 0
                            textWithState.value = "HATIRLATICI"
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_resim),
                                contentDescription = "", tint = Color.White
                            )
                        }
                    }
                }


                }



                Button(modifier = Modifier.padding(all = 30.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orangeLight)),
                    onClick = {
                        val noteTexti = tfNote.value

                        viewModel.update(note.note_id,noteTexti,note.is_done,note.notification_id,note.date,note.time)


                        textWithState.value = "HATIRLATICI"
                        localFocusManager.clearFocus()

                        if(note.is_done==1)
                        navController.navigate("NoteDoneSayfa")
                        else
                            navController.navigate("Anasayfa")

                    }
                ) {
                    Text(
                        text = "GÜNCELLE", color = Color.White, fontSize = 20.sp
                    )
                }
            }
        })

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Tamam") {
                timeDialogState.show()
            }
            negativeButton(text = "İptal")
        }, backgroundColor = colorResource(id = R.color.orange)
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Tarih Belirle"
        ) {
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Tamam") {
                flag.value = 1
                textWithState.value = " Saat $formattedTime'de/da $formattedDate"
            }
            negativeButton(text = "İptal")
        }, backgroundColor = colorResource(id = R.color.orange)
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Zaman Belirle"
        ) {
            pickedTime = it
        }
    }


}