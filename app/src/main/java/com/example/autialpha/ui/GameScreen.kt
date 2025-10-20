package com.example.autialpha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autialpha.R
import com.example.autialpha.data.Item
import com.example.autialpha.data.ItemsRepository

@Composable
fun GameScreen(onSpeak: (String) -> Unit) {
    val repo = remember { ItemsRepository() }
    var items by remember { mutableStateOf(listOf<Item>()) }
    var index by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        items = repo.loadItems(context = androidx.compose.ui.platform.LocalContext.current)
    }

    if (items.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Загрузка...") }
        return
    }

    val current = items[index]
    var picked by remember { mutableStateOf("") }
    val letters = remember(current) { current.word.uppercase().toList().shuffled() }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val resId = R.drawable.ic_placeholder
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = current.word,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(240.dp)
                )
            }
        }

        Text(
            text = picked,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { picked = "" }) { Text(stringResource(id = R.string.clear)) }
            Button(onClick = { onSpeak(current.word) }) { Text(stringResource(id = R.string.speak)) }
            Button(onClick = { index = (index + 1) % items.size; picked = "" }) { Text(stringResource(id = R.string.next)) }
        }

        LettersGrid(letters = letters, onPick = { c ->
            if (picked.length < current.word.length) picked += c
        })
    }
}

@Composable
private fun LettersGrid(letters: List<Char>, onPick: (Char) -> Unit) {
    val rows = letters.chunked(8)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { c ->
                    Box(
                        Modifier
                            .weight(1f)
                            .background(Color(0xFFEFEFEF), RoundedCornerShape(8.dp))
                            .clickable { onPick(c) }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(c.toString(), fontSize = 24.sp)
                    }
                }
            }
        }
    }
}


