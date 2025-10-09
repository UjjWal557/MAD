package com.example.jetpack.screen

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.screen.components.FormField
import com.example.jetpack.ui.theme.Pink40

@Composable
fun LoginUI()
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Spacer(modifier = Modifier.height(25.dp))
        Image(
            painter = painterResource(R.drawable.guni_pink_logo),
            contentDescription = "Logo",
            modifier = Modifier.height(150.dp).align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                FormField(
                    label = "Email", textState = email,
                    onTextChange = { email = it })
                FormField(
                    label = "Password", textState = password,
                    onTextChange = { password = it }, isPasswordField = true
                )
                Text(
                    text = "Forgot Password",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 50.dp).align(Alignment.End)
                )
                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
                {
                    Text("Login", fontSize = 18.sp)
                }
            }
        }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account?", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(onClick = {}) {
                    Text("SIGN UP", color = Pink40, fontSize = 18.sp)
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginActivityPreview()
{
    LoginUI()
}