package com.example.eraassignment.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.eraassignment.model.Image

@Composable
fun DetailView(image: Image, onDismiss: () -> Unit) {
    var scale by remember { mutableFloatStateOf(1f) }

    Popup(properties = PopupProperties(focusable = true)) {
        Box {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .zIndex(4f)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(4.dp)
                    .clickable {
                        onDismiss()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close button",
                    modifier = Modifier
                        .size(16.dp),
                    tint = Color.White
                )
            }
            Surface(
                modifier = Modifier.fillMaxSize().zIndex(2f).alpha(0.7f),
                color = Color.Black
            ) {}
            AsyncImage(
                model = image.src.large,
                contentDescription = image.alt,
                modifier = Modifier.fillMaxWidth().padding(12.dp).align(Alignment.Center).zIndex(4f)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 3f)
                        }
                    }.pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (scale > 1f) {
                                    scale = 1f
                                } else {
                                    scale = 3f
                                }
                            }
                        )
                    }.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}