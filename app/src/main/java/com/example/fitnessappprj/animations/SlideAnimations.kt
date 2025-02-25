package com.example.fitnessapp.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

object SlideAnimations {
    // Screen transition from right to left with slower timing
    fun enterFromRight(
        duration: Int = 800,  // Increased from 500ms to 800ms
        delay: Int = 0
    ) = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = delay,
            easing = EaseInOutQuart  // Added easing for smoother motion
        )
    )

    // For list items animation with slower timing
    fun itemFromRight(
        index: Int,
        baseDelay: Int = 150,  // Increased from 100ms to 150ms
        duration: Int = 700    // Increased from 500ms to 700ms
    ) = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = index * baseDelay,
            easing = EaseInOutQuart  // Added easing for smoother motion
        )
    )

    // Combined animation for smoother entrance
    private fun enterTransition(
        duration: Int = 800,
        delay: Int = 0
    ) = fadeIn(
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = delay,
            easing = EaseInOutQuart
        )
    ) + enterFromRight(duration, delay)

    // Wrapper composable for easy screen animation
    @Composable
    fun AnimatedScreen(
        duration: Int = 800,  // Added customizable duration
        content: @Composable () -> Unit
    ) {
        val isVisible = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isVisible.value = true
        }

        AnimatedVisibility(
            visible = isVisible.value,
            enter = enterTransition(duration = duration)
        ) {
            content()
        }
    }
}