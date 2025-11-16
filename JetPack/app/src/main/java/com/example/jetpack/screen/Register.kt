package com.example.jetpack.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetpack.R
import com.example.jetpack.screen.components.FormField


@Composable
fun RegisterUI(navController: NavController)
{
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cnfpassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Spacer(modifier = Modifier.height(25.dp))
        Image(
            painter = painterResource(R.drawable.guni_pink_logo),
            contentDescription = "Logo",
            modifier = Modifier.height(150.dp).align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(6.dp)
            ) {

                FormField(
                    label = "Name",
                    textState = name,
                    onTextChange = { name = it })
                FormField(
                    label = "Phone Number",
                    textState = phone,
                    onTextChange = { phone = it },
                    isNumber = true)
                FormField(
                    label = "City",
                    textState = city,
                    onTextChange = { city = it })
                FormField(
                    label = "Email", textState = email,
                    onTextChange = { email = it })
                FormField(
                    label = "Password",
                    textState = password,
                    onTextChange = { password = it },
                    isPasswordField = true)
                FormField(
                    label = "Confirm Password",
                    textState = cnfpassword,
                    onTextChange = { cnfpassword = it },
                    isPasswordField = true)
                Button(
                    onClick = {Toast.makeText(context, "Register" +
                            " Successful!", Toast.LENGTH_SHORT).show()
                              },
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).padding(top = 25.dp)
                )
                {
                    Text("Register",
                        fontSize = 18.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account?", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(onClick = {navController.navigate("LoginScreen")}) {
                Text("LOGIN",
                    color = Color(0xFFE91E63),
                    fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterActivityPreview()
{
    val navControl = rememberNavController()
    RegisterUI(navControl)
}