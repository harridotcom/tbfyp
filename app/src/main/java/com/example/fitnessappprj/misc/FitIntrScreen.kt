import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import kotlinx.coroutines.delay

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var startAnimation by remember { mutableStateOf(false) }
    var startExitAnimation by remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation && !startExitAnimation) 1f
        else if (startExitAnimation) 0f
        else 0f,
        animationSpec = tween(
            durationMillis = 200,
            easing = if (startExitAnimation) FastOutLinearInEasing else LinearOutSlowInEasing
        ),
        finishedListener = {
            if (startExitAnimation) {
                // Navigate only after fade-out is complete
                navController.navigate("home") {
                    popUpTo("IntroScreen") {
                        inclusive = true
                    }
                }
            }
        }
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500L) // Show screen for 1.5 seconds
        startExitAnimation = true // Begin exit animation
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .alpha(alphaAnim.value),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.heart),
                contentDescription = "Logo",
                modifier = Modifier.size(60.dp)
            )
            Text(
                text = "MyFitnessPal",
                fontWeight = FontWeight.Bold
            )
        }
    }
}