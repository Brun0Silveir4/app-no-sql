package com.example.appnosql

import DBHandler
import MainActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appnosql.model.CourseModel
import com.example.appnosql.ui.theme.AppNoSqlTheme
import kotlinx.coroutines.launch

class ViewCourses : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNoSqlTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Sample Text",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        val intent = Intent(this@ViewCourses, MainActivity::class.java)
                                        startActivity(intent)
                                    }) {
                                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                }
                            )
                        }
                    ) {
                        ViewCoursesContent(LocalContext.current)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCoursesContent(context: Context) {
    val dbHandler = remember { DBHandler(context) }
    var courseList by remember { mutableStateOf(listOf<CourseModel>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        courseList = dbHandler.readCourses() ?: emptyList()
    }

    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(56.dp))
        }
        itemsIndexed(courseList) { index, course ->
            Card(
                modifier = Modifier.padding(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = course.courseName,
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Course Tracks : ${course.courseTracks}",
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Course Duration : ${course.courseDuration}",
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Description : ${course.courseDescription}",
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    val success = dbHandler.deleteCourse(course.courseName)
                                    if (success) {
                                        Toast.makeText(context, "Curso excluido com sucesso!", Toast.LENGTH_SHORT).show()

                                    }
                                }
                            }
                        ) {
                            Text("Delete")
                        }

                        Button(
                            onClick = {
                                val intent = Intent(context, UpdateCourses::class.java)
                                intent.putExtra("courseName", course.courseName)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("Update")
                        }
                    }
                }
            }
        }
    }
}
