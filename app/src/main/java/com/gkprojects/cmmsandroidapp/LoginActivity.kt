package com.gkprojects.cmmsandroidapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.api.Login.ApiLoginViewModel
import com.gkprojects.cmmsandroidapp.api.Login.AuthState
import java.util.UUID
import com.gkprojects.cmmsandroidapp.LoginPage as LoginPage1

class LoginActivity: AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var useRemoteDBState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("SettingsApp", Context.MODE_PRIVATE)
        initializeIdDevice()
        setContent {

           LoginScreen(this@LoginActivity,useRemoteDBState.value)

        }
    }
    private fun initializeIdDevice(){
        sharedPreferences = getSharedPreferences("SettingsApp", Context.MODE_PRIVATE)

        if (!sharedPreferences.contains("device_id")) {
            val editor = sharedPreferences.edit()
            val uniqueID = UUID.randomUUID().toString()
            editor.putString("device_id", uniqueID)
            editor.apply()
        }
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = getSharedPreferences("SettingsApp", Context.MODE_PRIVATE)
        useRemoteDBState.value = useRemoteDB()
    }
    private fun useRemoteDB(): Boolean {
        return sharedPreferences.getBoolean("useRemoteDB", false)
    }

}


@Composable
fun LoginScreen(context: Context, useRemoteDB: Boolean) {
    val sharedPreferences = context.getSharedPreferences("SettingsApp", Context.MODE_PRIVATE)
    //val useRemoteDB = sharedPreferences.getBoolean("useRemoteDB", false)
    val verifyModel : ApiLoginViewModel = viewModel()
    val authState by verifyModel.authResponse.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {

//        if (useRemoteDB) {
//
//            LoginPage1({ username, password ->
//                Log.d("ApiSentData", "$username $password")
//                verifyModel.authenticate(context, username, password)
//            })
//        } else {
//            Button(onClick = {
//                val intent = Intent(context, MainActivity::class.java)
//                context.startActivity(intent)
//            }) {
//                Text("Start Application")
//            }
//        }
        if (useRemoteDB) {
            LoginPage1({ username, password ->
                Log.d("ApiSentData", "$username $password")
                verifyModel.authenticate(context, username, password)
            }, Modifier.align(Alignment.Center))
        } else {
            Button(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }, modifier = Modifier.align(Alignment.Center)) {
                Text("Start Application")
            }
        }
        Button(
            onClick = { val intent = Intent(context, AppSettingsActivity::class.java)
                context.startActivity(intent) },
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        )
            {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }


    }



    when (authState) {
        is AuthState.Loading -> {
            // Show loading indicator
            CircularProgressIndicator()
        }

        is AuthState.Success -> {
            val response = (authState as AuthState.Success).response
            AppData.userId = response.UserID
            Log.d("verifyTest","$response")

            // Start MainActivity
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        is AuthState.Failure -> {
            val message = (authState as AuthState.Failure).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        is AuthState.NoConnection -> {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        }

        null -> {
            // Do nothing
        }
    }

}

@Composable
fun LoginPage(onLogin: (String, String) -> Unit, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Column {
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
        Button(onClick = { onLogin(username, password) } ,Modifier.align(Alignment.End)) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}