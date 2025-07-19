package com.kotlinx.learn.throttle_first.rx

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ThrottleFirstRxScreen(viewModel: ThrottleFirstRxViewModel = viewModel()) {
    val data = viewModel.data.collectAsState(SnapshotStateList<Bitmap>())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            repeat(data.value.size) { index ->
                item(key = index) {
                    Spacer(modifier = Modifier.height(25.dp))
                    Image(
                        bitmap = data.value[index].asImageBitmap(),
                        contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    HorizontalDivider(color = Color.Black, thickness = 2.dp)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val disposable = viewModel.loadBitmaps(5000)

//        onDispose {
//            Log.e("DISPOSE", "Dispose")
//
//            disposable.dispose()
//        }
    }
}