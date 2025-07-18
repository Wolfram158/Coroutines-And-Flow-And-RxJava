package com.kotlinx.learn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kotlinx.learn.throttle_first.rx.ThrottleFirstRxScreen
import com.kotlinx.learn.ui.theme.CoroutinesAndFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CoroutinesAndFlowTheme {
                ThrottleFirstRxScreen()
            }
        }
    }
}
