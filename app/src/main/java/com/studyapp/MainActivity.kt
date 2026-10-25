package com.studyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
fun AppRoot() {
    val nav = rememberNavController()
    val items = listOf("home","planner","blocks","insights","groups","music","settings")
    val labels = mapOf("home" to "Home","planner" to "Planner","blocks" to "Blocks","insights" to "Insights","groups" to "Groups","music" to "Music","settings" to "Settings")
    val icons = mapOf("home" to Icons.Filled.Home,"planner" to Icons.Filled.DateRange,"blocks" to Icons.Filled.Block,"insights" to Icons.Filled.BarChart,"groups" to Icons.Filled.Group,"music" to Icons.Filled.MusicNote,"settings" to Icons.Filled.Settings)
    Scaffold(bottomBar = {
        NavigationBar {
            val back by nav.currentBackStackEntryAsState()
            val cur = back?.destination?.route
            items.forEach { r ->
                NavigationBarItem(selected = cur == r, onClick = {
                    nav.navigate(r) { popUpTo(nav.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true }
                }, icon = { Icon(icons[r]!!, contentDescription = labels[r]) }, label = { Text(labels[r]!!) })
            }
        }
    }) { pad ->
        NavHost(navController = nav, startDestination = "home", modifier = Modifier.padding(pad)) {
                composable("onboarding") { OnboardingScreen(onFinish = { nav.navigate("home") { popUpTo("onboarding") { inclusive = true } } }) }
            composable("home") { HomeScreen() }
            composable("planner") { PlannerScreen() }
            composable("blocks") { BlocksScreen() }
            composable("insights") { InsightsScreen() }
            composable("groups") { GroupsScreen() }
            composable("music") { MusicScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

@Composable fun HomeScreen() {
    var remaining by remember { mutableStateOf(25 * 60) }
    var running by remember { mutableStateOf(false) }
    LaunchedEffect(running) { while (running && remaining > 0) { kotlinx.coroutines.delay(1000); remaining-- }; if (remaining == 0) running = false }
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
@Composable fun PlannerScreen() { ScreenPlaceholder("Planner", "Weekly timetable, schedules, tasks") }
@Composable fun BlocksScreen() { ScreenPlaceholder("Blocks", "App selection, profiles, blocking engine") }
@Composable fun InsightsScreen() { ScreenPlaceholder("Insights", "Usage stats, charts, blocking triggers") }
@Composable fun GroupsScreen() { ScreenPlaceholder("Focus Groups", "Real backend required; honest setup state shown until configured") }
@Composable fun MusicScreen() { ScreenPlaceholder("Music & Themes", "Media3 ambient tracks, themes, animated backgrounds") }
@Composable fun SettingsScreen() { ScreenPlaceholder("Settings", "Permissions, onboarding revisit, privacy, data deletion") }
@Composable fun ScreenPlaceholder(title: String, subtitle: String) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
