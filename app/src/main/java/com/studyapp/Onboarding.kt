package com.studyapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    val total = 6
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        LinearProgressIndicator(progress = { (step + 1f) / total }, modifier = Modifier.fillMaxWidth())
        when (step) {
            0 -> {
                Text("Rescue Your Time", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Study App helps you focus without digital distractions. Usage Access for insights; Accessibility only if blocking requires it.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = { step++ }) { Text("Set up my focus") }
                TextButton(onClick = onFinish) { Text("Not now") }
            }
            1 -> {
                Text("Focus goal", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                val goals = listOf("School/Boards","JEE","NEET","UPSC","Coding","Reading","Writing","Deep Work","General Study")
                var selected by remember { mutableStateOf<String?>(null) }
                goals.forEach { g -> FilterChip(selected = selected == g, onClick = { selected = g }, label = { Text(g) }) }
                Button(onClick = { step++ }, enabled = selected != null) { Text("Continue") }
            }
            2 -> {
                Text("Distractions", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                val apps = listOf("YouTube","Instagram","TikTok","Snapchat","Reddit","Facebook","Gaming","Streaming")
                val selected = remember { mutableStateListOf<String>() }
                apps.forEach { a -> FilterChip(selected = selected.contains(a), onClick = { if (selected.contains(a)) selected.remove(a) else selected.add(a) }, label = { Text(a) }) }
                Button(onClick = { step++ }) { Text("Continue") }
            }
            3 -> {
                Text("Daily study target", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                var hours by remember { mutableStateOf(3f) }
                Text("${hours.toInt()} hours", fontSize = 40.sp, fontWeight = FontWeight.Bold)
                Slider(value = hours, onValueChange = { hours = it }, valueRange = 0.5f..8f, steps = 14)
                Button(onClick = { step++ }) { Text("Continue") }
            }
            4 -> {
                Text("Permissions", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text("Usage Access, Accessibility (if needed), Notifications, Overlay (if used). Each explained separately.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = { step++ }) { Text("Continue") }
            }
            5 -> {
                Text("Review & begin", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text("Distractions, goal, daily target saved. Blocking capabilities as enabled.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = onFinish) { Text("Enter my focus dashboard") }
            }
        }
    }
}
