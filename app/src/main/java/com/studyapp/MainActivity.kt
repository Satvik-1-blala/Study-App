package com.studyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectAsStateWithLifecycle

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

@Composable
fun HomeScreen(viewModel: FocusViewModel = androidx.activity.viewModels()) {
    val remaining by viewModel.remaining.collectAsStateWithLifecycle()
    val running by viewModel.running.collectAsStateWithLifecycle()
    val total by remember { mutableStateOf(25 * 60) }
    val progress = 1f - (remaining.toFloat() / total)
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Study App", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text("Focus session", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(220.dp)) {
            Canvas(Modifier.size(220.dp)) {
                drawArc(Color(0xFF1F2937), -90f, 360f, false, style = Stroke(12.dp.toPx(), cap = StrokeCap.Round), size = Size(size.width, size.height), topLeft = Offset.Zero)
                drawArc(Color(0xFF00E676), -90f, 360f * progress, false, style = Stroke(12.dp.toPx(), cap = StrokeCap.Round), size = Size(size.width, size.height), topLeft = Offset.Zero)
            }
            Text(String.format("%02d:%02d", remaining / 60, remaining % 60), fontSize = 44.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
        Text("Today's goal: 3h  •  Tracked: 0h", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Next session: none scheduled", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { if (running) viewModel.pause() else viewModel.start() }, modifier = Modifier.weight(1f)) { Text(if (running) "Pause" else "Start Focus") }
            OutlinedButton(onClick = { viewModel.reset(total) }) { Text("Reset") }
        }
        Text("Strict Mode: off  •  Restricted apps: 0  •  Blocking: disabled", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
    }
}

@Composable
fun PlannerScreen() {
    var items by remember { mutableStateOf(listOf("Mon 09:00-10:00 Deep Work")) }
    var title by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Planner", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("New schedule (e.g. Mon 09:00-10:00 Subject)") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { if (title.isNotBlank()) { items = items + title; title = "" } }) { Text("Add schedule") }
        items.forEach { Text("• $it") }
        Text("Room persistence via ScheduleRepository to be wired.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
    }
}

@Composable
fun BlocksScreen() {
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val installed = remember { com.studyapp.util.AppEnumerator.list(ctx) }
    val selected = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        val db = com.studyapp.data.DatabaseProvider.get(ctx)
        selected.addAll(db.blockedAppDao().all().map { it.packageName })
    }
    var protection by remember { mutableStateOf("Disabled (NoOp engine)") }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Blocks", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Permission status: Usage Access not granted", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Protection: $protection", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Restricted apps (${selected.size} selected)", fontWeight = FontWeight.SemiBold)
        installed.take(20).forEach { app ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = selected.contains(app.packageName), onCheckedChange = { c ->
                    scope.launch {
                        val db = com.studyapp.data.DatabaseProvider.get(ctx)
                        if (c) { selected.add(app.packageName); db.blockedAppDao().upsert(com.studyapp.data.BlockedAppEntity(app.packageName, app.label)) }
                        else { selected.remove(app.packageName); db.blockedAppDao().remove(app.packageName) }
                    }
                })
                Text(app.label, modifier = Modifier.weight(1f))
            }
        }
        Text("Blocking engine: NoOp (degraded). Real foreground detection not implemented.", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
    }
}

@Composable
fun InsightsScreen() { ScreenPlaceholder("Insights", "Usage stats, charts, blocking triggers (Usage Access required)") }
@Composable
fun GroupsScreen() { ScreenPlaceholder("Focus Groups", "Real backend required; honest setup state shown until configured") }
@Composable
fun MusicScreen() { ScreenPlaceholder("Music & Themes", "Media3 ambient tracks, themes, animated backgrounds") }
@Composable
fun SettingsScreen() { ScreenPlaceholder("Settings", "Permissions, onboarding revisit, privacy, data deletion") }
@Composable
fun ScreenPlaceholder(title: String, subtitle: String) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
