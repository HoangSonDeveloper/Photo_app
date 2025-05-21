package com.example.eraassignment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    title: String = "",
    body: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarColors(
                    containerColor = Color(0xff007aff),
                    scrolledContainerColor = Color(0xffffffff),
                    navigationIconContentColor = Color(0xff303233),
                    titleContentColor = Color(0xffffffff),
                    actionIconContentColor = Color(0xff303233)
                )
            )
        }
    ) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            body()
        }
    }
}