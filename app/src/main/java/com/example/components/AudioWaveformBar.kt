package com.example.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.StudioSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold

@Composable
fun AudioWaveformBar(
    title: String,
    durationStr: String,
    waveformPoints: List<Float>,
    modifier: Modifier = Modifier,
    accentColor: Color = ElectricPurple
) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0.35f) }

    val infiniteTransition = rememberInfiniteTransition(label = "wave_anim")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(StudioSurfaceVariant)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Play/Pause button
            IconButton(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (isPlaying) WarmGold else accentColor)
                    .testTag("audio_play_button")
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause audio" else "Play audio sample",
                    tint = if (isPlaying) Color.Black else Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                    Text(
                        text = if (isPlaying) "Playing • $durationStr" else durationStr,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPlaying) WarmGold else TextMuted,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Waveform bars
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clickable {
                            isPlaying = !isPlaying
                        }
                ) {
                    waveformPoints.forEachIndexed { index, heightFactor ->
                        val barProgress = index.toFloat() / waveformPoints.size.toFloat()
                        val isPassed = barProgress <= progress
                        val barMultiplier = if (isPlaying) {
                            if (index % 2 == 0) pulse else (2.1f - pulse)
                        } else 1.0f

                        val barHeight = (heightFactor * 22f * barMultiplier).coerceIn(4f, 22f).dp

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(barHeight)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    when {
                                        isPlaying && isPassed -> WarmGold
                                        isPassed -> accentColor
                                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}
