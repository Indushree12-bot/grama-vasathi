package com.example.businesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

/* ================= NAVIGATION ================= */

@Composable
fun App() {

    var screen by remember { mutableStateOf("welcome") }

    when (screen) {

        "welcome" -> WelcomeScreen { screen = "login" }

        "login" -> LoginScreen(
            onLogin = { screen = "home" },
            onRegister = { screen = "register" }
        )

        "register" -> RegisterScreen(
            onDone = { screen = "home" },
            onBack = { screen = "login" }
        )

        "home" -> FarmApp()
    }
}

/* ================= WELCOME ================= */

@Composable
fun WelcomeScreen(onNext: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("🌿 Grama Vasathi", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Rural Hospitality App")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onNext) {
            Text("Get Started")
        }
    }
}

/* ================= LOGIN ================= */

@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Login", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(password, { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onLogin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        TextButton(onClick = onRegister) {
            Text("Create Account")
        }
    }
}

/* ================= REGISTER ================= */

@Composable
fun RegisterScreen(onDone: () -> Unit, onBack: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Create Account", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(password, { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onDone() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        TextButton(onClick = onBack) {
            Text("Back")
        }
    }
}

/* ================= HOME APP ================= */

@Composable
fun FarmApp() {

    val farms = listOf(
        FarmStay("Green Village Stay", "Mysore", "Cow Milking", R.drawable.farm1),
        FarmStay("Nature Farm House", "Mandya", "Bird Watching", R.drawable.farm2),
        FarmStay("Village Roots", "Chikmagalur", "Local Cooking", R.drawable.farm3)
    )

    LazyColumn(
        contentPadding = PaddingValues(12.dp)
    ) {
        items(farms) { farm ->
            FarmCard(farm)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

/* ================= DATA ================= */

/* ================= FARM CARD ================= */

@Composable
fun FarmCard(farm: FarmStay) {

    var availableRooms by remember { mutableStateOf(5) }
    var bookedRooms by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    var bookingDate by remember { mutableStateOf("") }

    val score = 75

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column {

            Image(
                painter = painterResource(farm.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {

                Text(farm.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Village: ${farm.village}")
                Text("Activity: ${farm.activity}")

                Spacer(modifier = Modifier.height(10.dp))

                Text("Available Rooms: $availableRooms", fontWeight = FontWeight.Bold)
                Text("Booked Rooms: $bookedRooms", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = bookingDate,
                    onValueChange = { bookingDate = it },
                    label = { Text("Booking Date") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                        if (availableRooms > 0) {
                            availableRooms--
                            bookedRooms++
                            message = "Thank you for booking the room 🌿"
                        } else {
                            message = "Sorry! No rooms available ❌"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Book Now")
                }

                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        message,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}