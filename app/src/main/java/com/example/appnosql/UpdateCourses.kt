package com.example.appnosql

import DBHandler
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appnosql.ui.theme.AppNoSqlTheme
import com.example.appnosql.ui.theme.PurpleGrey40

class UpdateCourses : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.M)
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
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                                title = {
                                    Text(
                                        text = "GFG",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    ) {
                        UpdateDataInDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDataInDatabase(context: Context) {
    val courseNameFromIntent = (context as Activity).intent.getStringExtra("courseName")
    var courseDuration by remember { mutableStateOf(TextFieldValue()) }
    var courseTracks by remember { mutableStateOf(TextFieldValue()) }
    var courseDescription by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val dbHandler = DBHandler(context)

        Text(
            text = "SQLite Database in Android",
            color = PurpleGrey40,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = courseDuration,
            onValueChange = { courseDuration = it },
            placeholder = { Text(text = "Enter your course duration") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = courseTracks,
            onValueChange = { courseTracks = it },
            placeholder = { Text(text = "Enter your course tracks") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = courseDescription,
            onValueChange = { courseDescription = it },
            placeholder = { Text(text = "Enter your course description") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val courseDurationText = courseDuration.text
            val courseDescriptionText = courseDescription.text
            val courseTracksText = courseTracks.text

            val success = dbHandler.updateCourse(
                courseNameFromIntent ?: "",
                courseDurationText,
                courseDescriptionText,
                courseTracksText
            )

            if (success) {
                Toast.makeText(context, "Course Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to Update Course", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Update Course", color = Color.White)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val intent = Intent(context, ViewCourses::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Read Courses to Database", color = Color.White)
        }
    }
}
