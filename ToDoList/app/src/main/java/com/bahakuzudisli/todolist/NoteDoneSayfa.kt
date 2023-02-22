package com.bahakuzudisli.todolist

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bahakuzudisli.todolist.ui.theme.ToDoListTheme
import com.bahakuzudisli.todolist.viewmodel.NoteDoneViewModel
import com.bahakuzudisli.todolist.viewmodelfactory.NoteDoneViewModelFactory
import com.google.gson.Gson

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteDoneSayfa(navController: NavController){


    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val menuAcikMi = remember {
        mutableStateOf(false)
    }

    val tf = remember {
        mutableStateOf("")
    }



    val context = LocalContext.current
    val viewModel: NoteDoneViewModel = viewModel(
        factory = NoteDoneViewModelFactory(context.applicationContext as Application)
    )

    val noteList = viewModel.noteListesi.observeAsState(listOf())




    LaunchedEffect(key1 = true) {
        viewModel.downloadNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                if (aramaYapiliyorMu.value) {
                    TextField(
                        value = tf.value, onValueChange = {
                            tf.value = it
                            //Room arama işlemi
                            viewModel.search(it)

                        },
                        label = { Text(text = "ARA") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedLabelColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            textColor = Color.White
                        )
                    )
                } else
                    Text(text = "BİTEN GÖREVLER", color = Color.White)
            }, backgroundColor = colorResource(id = R.color.orangeLight),
                actions = {
                    if (!aramaYapiliyorMu.value) {


                        IconButton(onClick = {
                            aramaYapiliyorMu.value = true

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arama_resim),
                                contentDescription = "", tint = Color.White
                            )
                        }

                        IconButton(onClick = {
                            menuAcikMi.value = true

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.more_resim),
                                contentDescription = "", tint = Color.White
                            )
                        }
                        DropdownMenu(expanded = menuAcikMi.value, onDismissRequest = { menuAcikMi.value=false
                        },
                            Modifier.background(color = colorResource(id = R.color.orangeDark))) {
                           DropdownMenuItem(onClick = {

                               navController.navigate("Anasayfa")
                               menuAcikMi.value=false
                           },
                               Modifier.background(color = colorResource(id = R.color.orangeDark))) {

                               Text(text = "Anasayfa", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)

                           }
                        }




                    } else {
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = false
                            tf.value = ""

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_resim),
                                contentDescription = "", tint = Color.White
                            )

                        }
                    }



                }

            )
        },
        backgroundColor = colorResource(id = R.color.orangeDark),
        content = {
            LazyColumn {
                items(count = noteList.value!!.size, itemContent = {

                    val note = noteList.value!![it]

                    Card(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth(),
                        backgroundColor = colorResource(id = R.color.orange)
                    ) {
                        Row(modifier = Modifier.clickable {
                            val noteJson = Gson().toJson(note)
                            navController.navigate("NoteDetaySayfa/$noteJson")
                        }) {
                            Row(
                                modifier = Modifier
                                    .padding(all = 10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Checkbox(checked = true, onCheckedChange = {

                                    viewModel.updateStutation(note.note_id,note.note,0,note.notification_id,note.date,note.time)


                                }, colors = CheckboxDefaults.colors(
                                    checkedColor = colorResource(id = R.color.orange)
                                )
                                )

                                Column( modifier = Modifier
                                    .width(250.dp)
                                    .fillMaxHeight(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center) {

                                    Text(text = "${note.note}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                                    if(note.time.equals("")){

                                    }
                                    else {
                                        Text(
                                            text = "${note.date} - ${note.time} ",
                                            color = colorResource(id = R.color.orangeLight2),
                                            fontSize = 15.sp,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }


                                IconButton(onClick = {
                                    viewModel.delete(note.note_id)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete_resim),
                                        contentDescription = "", tint = colorResource(id = R.color.orange)
                                    )
                                }

                            }


                        }

                    }
                })
            }
        }
    )

}

