package com.studyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = "onboarding") {
                        composable("onboarding") { OnboardingScreen(onFinish = { nav.navigate("home") { popUpTo("onboarding") { inclusive = true } } }) }
                        composable("home") { HomeScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    val total = 6
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        LinearProgressIndicator(progress = { (step + 1f) / total }, modifier = Modifier.fillMaxWidth())
        when (step) {
            0 -> {
                Text("Rescue Your Time", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Study App helps you focus without digital distractions. It needs Usage Access for insights and optional Accessibility for blocking.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = { step++ }) { Text("Set up my focus") }
                TextButton(onClick = onFinish) { Text("Not now") }
            }
            1 -> {
                Text("Focus goal", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                val goals = listOf("School/Boards","JEE","NEET","UPSC","Coding","Reading","Writing","Deep Work","General Study")
                var selected by remember { mutableStateOf<String?>(null) }
                goals.chunked(3).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { g ->
                            FilterChip(selected = selected == g, onClick = { selected = g }, label = { Text(g) })
                        }
                    }
                }
                Button(onClick = { step++ }, enabled = selected != null) { Text("Continue") }
            }
            2 -> {
                Text("Distractions", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                val apps = listOf("YouTube","Instagram","TikTok","Snapchat","Reddit","Facebook","Gaming","Streaming")
                val selected = remember { mutableStateListOf<String>() }
                apps.forEach { a ->
                    FilterChip(selected = selected.contains(a), onClick = { if (selected.contains(a)) selected.remove(a) else selected.add(a) }, label = { Text(a) })
                }
                Button(onClick = { step++ }) { Text("Continue") }
            }
            3 -> {
                Text("Daily study target", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                var hours by remember { mutableStateOf(3f) }
                Text("${hours.toInt()} hours", fontSize = 40.sp, fontFamily = FontFamily.Monospace)
                Slider(value = hours, onValueChange = { hours = it }, valueRange = 0.5f..8f, steps = 14)
                Button(onClick = { step++ }) { Text("Continue") }
            }
            4 -> {
                Text("Permissions", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text("Usage Access: insights and foreground detection. Accessibility: only if blocking requires it. Notifications: reminders. Each explained separately.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = { step++ }) { Text("Continue") }
            }
            5 -> {
                Text("Review & begin", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text("Distractions, goal, and daily target saved. Blocking capabilities as enabled.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = onFinish) { Text("Enter my focus dashboard") }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var remaining by remember { mutableStateOf(25 * 60) }
    var running by remember { mutableStateOf(false) }
    LaunchedEffect(running) {
        while (running && remaining > 0) {
            kotlinx.coroutines.delay(1000)
            remaining--
        }
        if (remaining == 0) running = false
    }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Study App", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text("Focus session", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(String.format("%02d:%02d", remaining / 60, remaining % 60), fontSize = 72.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { running = !running }) { Text(if (running) "Pause" else "Start focus") }
            OutlinedButton(onClick = { running = false; remaining = 25 * 60 }) { Text("Reset") }
        }
        Text("Strict Mode: off  •  Restricted apps: 0", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
