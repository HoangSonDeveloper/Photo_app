import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )  {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xff007aff)
        )
    }
}