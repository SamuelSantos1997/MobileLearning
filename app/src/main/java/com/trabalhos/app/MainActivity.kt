package com.trabalhos.app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trabalhos.app.ui.theme.TrabalhosTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrabalhosTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(onNavigateToModernArt = { navController.navigate("modern_art") })
        }
        composable("modern_art") {
            ModernArtScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToModernArt: () -> Unit) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    var title by remember { mutableStateOf("") }
    var statusDone by remember { mutableStateOf(false) }
    var priority by remember { mutableStateOf("Medium") }
    var dateText by remember { mutableStateOf(dateFormat.format(calendar.time)) }
    var timeText by remember { mutableStateOf(timeFormat.format(calendar.time)) }

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            snackbarMessage = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trabalhos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Botão Modern Art ──────────────────────────────────────
            Button(
                onClick = onNavigateToModernArt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
            ) {
                Icon(Icons.Default.Palette, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Modern Art UI", color = Color.White)
            }

            // ── Title ─────────────────────────────────────────────────
            Text("Title", color = Color.White, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Enter Title", color = Color(0xFF888888)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF444444),
                    unfocusedBorderColor = Color(0xFF444444)
                )
            )
            HorizontalDivider(
                color = Color(0xFF444444),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // ── Status ────────────────────────────────────────────────
            Text("Status", color = Color.White, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = statusDone,
                    onClick = { statusDone = true },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF33B5E5))
                )
                Text("Done:", color = Color.White, modifier = Modifier.padding(end = 16.dp))
                RadioButton(
                    selected = !statusDone,
                    onClick = { statusDone = false },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF33B5E5))
                )
                Text("Not Done", color = Color(0xFF33B5E5))
            }
            Spacer(Modifier.height(16.dp))

            // ── Priority ──────────────────────────────────────────────
            Text("Priority:", color = Color.White, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                listOf("Low", "Medium", "High").forEach { option ->
                    RadioButton(
                        selected = priority == option,
                        onClick = { priority = option },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF33B5E5))
                    )
                    Text(
                        option,
                        color = if (priority == option) Color(0xFF33B5E5) else Color.White,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            // ── Time and Date ─────────────────────────────────────────
            Text("Time and Date", color = Color.White, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(dateText, color = Color.White)
                Text(timeText, color = Color(0xFFFF4444))
            }
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, y, m, d ->
                                calendar.set(y, m, d)
                                dateText = dateFormat.format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666))
                ) { Text("Choose Date", color = Color.White) }

                Button(
                    onClick = {
                        TimePickerDialog(
                            context,
                            { _, h, min ->
                                calendar.set(Calendar.HOUR_OF_DAY, h)
                                calendar.set(Calendar.MINUTE, min)
                                calendar.set(Calendar.SECOND, 0)
                                timeText = timeFormat.format(calendar.time)
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666))
                ) { Text("Choose Time", color = Color.White) }
            }

            Spacer(Modifier.height(32.dp))

            // ── Botões inferiores ─────────────────────────────────────
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { /* finish equivalente — sem-op em Compose nav */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666))
                ) { Text("Cancel", color = Color.White) }

                Button(
                    onClick = {
                        title = ""
                        statusDone = false
                        priority = "Medium"
                        calendar.timeInMillis = System.currentTimeMillis()
                        dateText = dateFormat.format(calendar.time)
                        timeText = timeFormat.format(calendar.time)
                        snackbarMessage = "Formulário resetado"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666))
                ) { Text("Reset", color = Color.White) }

                Button(
                    onClick = {
                        if (title.isBlank()) {
                            snackbarMessage = "Por favor, insira um título"
                        } else {
                            val status = if (statusDone) "Done" else "Not Done"
                            snackbarMessage =
                                "Trabalho: $title\nStatus: $status\nPrioridade: $priority\nData: $dateText\nHora: $timeText"
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666))
                ) { Text("Submit", color = Color.White) }
            }
        }
    }
}
