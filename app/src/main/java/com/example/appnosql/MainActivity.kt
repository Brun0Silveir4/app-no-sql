import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appnosql.ViewCourses
import com.example.appnosql.ui.theme.AppNoSqlTheme
import com.example.appnosql.ui.theme.PurpleGrey40 as MyPurpleGrey40

class MainActivity : ComponentActivity() {

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
                                        text = "GFG",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    ) {
                        addDataToDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addDataToDatabase(context: Context) {
    val activity = context as Activity

    var courseName by remember { mutableStateOf("") }
    var courseDuration by remember { mutableStateOf("") }
    var courseTracks by remember { mutableStateOf("") }
    var courseDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var dbHandler: DBHandler by remember { mutableStateOf(DBHandler(context)) }

        Text(
            text = "SQLite Database in Android",
            color = MyPurpleGrey40,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = courseName,
            onValueChange = { courseName = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = courseDuration,
            onValueChange = { courseDuration = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = courseTracks,
            onValueChange = { courseTracks = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = courseDescription,
            onValueChange = { courseDescription = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks)
                Toast.makeText(context, "Course Added to Database", Toast.LENGTH_SHORT).show()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks)
                Toast.makeText(context, "Course Added to Database", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Add Course to Database", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val i = Intent(context, ViewCourses::class.java)
                context.startActivity(i)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Read Courses from Database", color = Color.White)
        }
    }
}
