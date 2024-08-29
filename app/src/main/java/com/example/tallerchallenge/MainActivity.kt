package com.example.tallerchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tallerchallenge.ui.theme.TallerChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TallerChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {

    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var userLoggedIn by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf(false) }

    val viewModel = LoginViewModel()

    if (!userLoggedIn) {
        Column (
            modifier = modifier.padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(text = "Name")
                },
                placeholder = {
                    Text(text = "Enter your name")
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Enter your password")
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (error) {
                Text(text = "Invalid credentials.",
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            TextButton(onClick = {
                if (viewModel.validateUserCredentials(name, password)) {
                    if (viewModel.authenticateUserCredentials(name, password)) {
                        userLoggedIn = true
                        error = false
                    } else {
                        userLoggedIn = false
                        error = true
                    }
                } else {
                    userLoggedIn = false
                    error = true
                }
            },
                colors = ButtonColors(Color.Blue, Color.Blue, Color.Gray, Color.Gray),
                modifier = Modifier.fillMaxWidth()
                ) {
                Text(text = "Login", color = Color.White)
            }
        }
    } else {
        Text(
            text = "Welcome $name!",
            modifier = modifier.padding(16.dp)
        )
        BackHandler {
            userLoggedIn = false
        }
    }
}
