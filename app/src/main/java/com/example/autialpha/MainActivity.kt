@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.autialpha

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.autialpha.ui.GameScreen
import java.util.Locale

class MainActivity : ComponentActivity() {
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("ru")
            }
        }

        setContent {
            MaterialTheme {
                Surface {
                    GameScreen(
                        onSpeak = { word ->
                            tts?.speak(word, TextToSpeech.QUEUE_FLUSH, null, "wordId")
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}


