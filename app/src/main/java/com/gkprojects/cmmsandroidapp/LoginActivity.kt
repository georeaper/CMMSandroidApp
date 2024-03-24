package com.gkprojects.cmmsandroidapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gkprojects.cmmsandroidapp.api.ApiViewModel
import com.gkprojects.cmmsandroidapp.api.AuthState
import com.gkprojects.cmmsandroidapp.LoginPage as LoginPage1

class LoginActivity: AppCompatActivity() {
//    private lateinit var viewModelApi : ApiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
           LoginScreen(this@LoginActivity)
        }
    }
}


@Composable
fun LoginScreen(context: Context) {
//    val viewModel: ApiViewModel = viewModel() // Get a reference to the ViewModel
//    val authState by viewModel.authResponse.observeAsState()

    LoginPage1 { username, password ->
        // Handle login here
       // viewModel.authenticate(context)

        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

//    when (authState) {
//        is AuthState.Loading -> {
//            // Show loading indicator
//        }
//        is AuthState.Success -> {
//            val response = (authState as AuthState.Success).response
//
//            // Use the response
//            // ...
//            AppData.userId = response.userId.toString()
//            // Start MainActivity
//
//
//            // Start MainActivity
//            Log.d("Api12"," success:  $response")
//            val intent = Intent(context, MainActivity::class.java)
//            context.startActivity(intent)
//        }
//        is AuthState.Failure -> {
//            Log.d("Api12","Fai Status")
//        }
//        is AuthState.NoConnection -> {
//            Log.d("Api12","No Connection Status")
//        }
//        null -> {
//            Log.d("Api12","null")
//        }
//    }
}

@Composable
fun LoginPage(onLogin: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row {


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true

            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onLogin(username, password) }) {
            Text("Login")
        }
    }
}