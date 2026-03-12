package com.trabalhos.app

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernArtScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("More Information") },
            text = { Text("Inspired by the works of Piet Mondrian.\n\nVisit the MoMA to learn more.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.moma.org"))
                    context.startActivity(intent)
                }) { Text("Visit MoMA") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Not Now") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Modern Art UI") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Info, contentDescription = "More Info")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3F51B5),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->

        // Reproduz exatamente o layout XML: dois rows com blocks coloridos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(4.dp)
        ) {
            // ── Top Row (weight 3) ────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            ) {
                // Bloco roxo (esquerda, ocupa toda a altura da row)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(3.dp)
                        .background(Color(0xFF7B83AB))
                )

                // Coluna direita: vermelho + cinza
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(3.dp)
                            .background(Color(0xFFB71C1C))
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(3.dp)
                            .background(Color(0xFFD9D9D9))
                    )
                }
            }

            // ── Bottom Row (weight 2) ─────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                // Rosa
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(3.dp)
                        .background(Color(0xFFD45B9E))
                )
                // Azul (weight 1.3)
                Box(
                    modifier = Modifier
                        .weight(1.3f)
                        .fillMaxHeight()
                        .padding(3.dp)
                        .background(Color(0xFF1A3B9C))
                )
            }
        }
    }
}
