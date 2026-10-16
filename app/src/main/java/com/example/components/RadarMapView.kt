package com.example.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Musician
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricCoral
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.WarmGold
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun RadarMapView(
    musicians: List<Musician>,
    maxRadiusKm: Float,
    selectedMusician: Musician?,
    onSelectMusician: (Musician) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar_sweep")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(StudioCardBg)
            .border(1.dp, StudioBorder, RoundedCornerShape(20.dp))
            .testTag("radar_map_container")
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()
        val centerX = widthPx / 2f
        val centerY = heightPx / 2f
        val radarRadiusPx = (minOf(widthPx, heightPx) / 2f) - 36f

        // Draw radar grid, concentric rings, and scanning beam
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background grid crosshairs
            drawLine(
                color = StudioBorder.copy(alpha = 0.5f),
                start = Offset(centerX, 16f),
                end = Offset(centerX, heightPx - 16f),
                strokeWidth = 1f
            )
            drawLine(
                color = StudioBorder.copy(alpha = 0.5f),
                start = Offset(16f, centerY),
                end = Offset(widthPx - 16f, centerY),
                strokeWidth = 1f
            )

            // Concentric rings (1/3, 2/3, full radius)
            val rings = listOf(0.33f, 0.66f, 1.0f)
            rings.forEach { ratio ->
                drawCircle(
                    color = ElectricPurple.copy(alpha = 0.25f),
                    radius = radarRadiusPx * ratio,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 1.2f)
                )
            }

            // Radar sweep gradient beam
            val sweepRad = Math.toRadians(sweepAngle.toDouble())
            val sweepEnd = Offset(
                x = (centerX + radarRadiusPx * cos(sweepRad)).toFloat(),
                y = (centerY + radarRadiusPx * sin(sweepRad)).toFloat()
            )
            drawLine(
                brush = Brush.radialGradient(
                    colors = listOf(ElectricPurple.copy(alpha = 0.8f), Color.Transparent),
                    center = Offset(centerX, centerY),
                    radius = radarRadiusPx
                ),
                start = Offset(centerX, centerY),
                end = sweepEnd,
                strokeWidth = 2.5f
            )

            // User center position pulse
            drawCircle(
                color = WarmGold.copy(alpha = 0.3f),
                radius = 16f,
                center = Offset(centerX, centerY)
            )
            drawCircle(
                color = WarmGold,
                radius = 6f,
                center = Offset(centerX, centerY)
            )
        }

        // Distance indicators overlay
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Your current studio position",
                        tint = WarmGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Your Studio (Center)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 11.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Coverage: ${maxRadiusKm.roundToInt()} km radius",
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontSize = 10.sp
            )
        }

        // Musician Markers plotted on Radar
        musicians.forEach { musician ->
            if (musician.distanceKm <= maxRadiusKm) {
                val distanceRatio = (musician.distanceKm / maxRadiusKm).coerceIn(0.15f, 0.95f)
                val distancePx = radarRadiusPx * distanceRatio
                val angleRad = Math.toRadians(musician.angleDegrees.toDouble())
                val pinX = (centerX + distancePx * cos(angleRad)).toFloat()
                val pinY = (centerY + distancePx * sin(angleRad)).toFloat()

                val isSelected = selectedMusician?.id == musician.id
                val markerColor = when {
                    musician.primaryInstrument.contains("Tabla", ignoreCase = true) -> WarmGold
                    musician.primaryInstrument.contains("Vocal", ignoreCase = true) -> ElectricPurple
                    musician.primaryInstrument.contains("Guitar", ignoreCase = true) -> CyanGlow
                    musician.primaryInstrument.contains("Producer", ignoreCase = true) -> NeonGreen
                    else -> ElectricCoral
                }

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                (pinX - 22.dp.toPx()).roundToInt(),
                                (pinY - 22.dp.toPx()).roundToInt()
                            )
                        }
                        .size(44.dp)
                        .clickable { onSelectMusician(musician) }
                        .testTag("radar_pin_${musician.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer pulse ring if selected
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(markerColor.copy(alpha = 0.35f))
                        )
                    }

                    // Inner Marker Pill
                    Surface(
                        color = if (isSelected) markerColor else StudioCardBg,
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 2.dp else 1.5.dp,
                            color = markerColor
                        ),
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(if (isSelected) 32.dp else 26.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = musician.avatarInitials,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = if (isSelected) 11.sp else 9.sp
                            )
                        }
                    }
                }
            }
        }

        // Radar bottom radar legend
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = StudioCardBg.copy(alpha = 0.9f),
                shape = RoundedCornerShape(6.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, StudioBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(NeonGreen)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Live Sonar Active",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
