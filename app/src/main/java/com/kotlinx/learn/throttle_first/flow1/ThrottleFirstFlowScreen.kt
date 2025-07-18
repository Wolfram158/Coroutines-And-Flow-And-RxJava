package com.kotlinx.learn.throttle_first.flow1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlinx.learn.throttle_first.flow1.ThrottleFirstFlowViewModel

@Composable
fun ThrottleFirstFlowScreen(viewModel: ThrottleFirstFlowViewModel = viewModel()) {
    val throttle = viewModel.throttle.collectAsState(false)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.padding(innerPadding),
                onClick = {
                    viewModel.click(3000)
                },
                enabled = !throttle.value
            ) {
                Text(text = "Click")
            }
        }
    }
}