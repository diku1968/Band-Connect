package com.example.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.components.AudioWaveformBar
import com.example.data.AudioTrack
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DeepIndigo
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.StudioSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    var isOpenForJams by remember { mutableStateOf(true) }

    val myAudioTracks = listOf(
        AudioTrack("my_1", "Acoustic Fingerstyle in DADGAD", "1:32", "Acoustic Fusion", listOf(0.4f, 0.7f, 0.85f, 0.6f, 0.9f, 0.7f, 0.8f, 0.5f, 0.3f)),
        AudioTrack("my_2", "Ambient Electric Guitar Swells & Reverb", "2:04", "Prog Rock", listOf(0.3f, 0.5f, 0.8f, 0.95f, 0.9f, 0.7f, 0.85f, 0.6f, 0.4f))
    )

    val myGearList = listOf(
        "Taylor 214ce DLX Grand Auditorium Acoustic",
        "Fender American Professional II Stratocaster",
        "Universal Audio Volt 276 Audio Interface",
        "Shure SM57 Dynamic Microphone",
        "Strymon Timeline & BigSky Ambient Reverbs"
    )

    val genres = listOf("Acoustic Fusion", "Sufi Rock", "Prog Rock", "Fingerstyle", "Indie Folk")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box {
                                Surface(
                                    modifier = Modifier.size(68.dp),
                                    shape = CircleShape,
                                    color = DeepIndigo,
                                    border = androidx.compose.foundation.BorderStroke(2.dp, ElectricPurple)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "DS",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }

                                if (isOpenForJams) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .background(NeonGreen)
                                            .border(2.dp, StudioCardBg, CircleShape)
                                            .align(Alignment.BottomEnd)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Dhruv Sharma",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Musician",
                                        tint = CyanGlow,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = "@dhruv_strings",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ElectricPurple
                                )
                                Text(
                                    text = "Guitarist & Songwriter",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = WarmGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Surface(
                            color = StudioSurfaceVariant,
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = TextMuted,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Acoustic fingerstyle player & electric ambient explorer. Love blending Indian Classical ragas with acoustic chords and odd-time loops. Always up for organic jams!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(label = "Completed Jams", value = "28")
                        StatItem(label = "Rating", value = "4.98 ★")
                        StatItem(label = "Stems Shared", value = "14")
                        StatItem(label = "Radius", value = "25 km")
                    }
                }
            }
        }

        // Open For Jams Switch Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Open for Jam Invites",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isOpenForJams) NeonGreen else TextMuted)
                            )
                        }
                        Text(
                            text = if (isOpenForJams) "Visible on nearby musician radar map" else "Paused - Not receiving jam requests",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }

                    Switch(
                        checked = isOpenForJams,
                        onCheckedChange = { isOpenForJams = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = WarmGold,
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = StudioSurfaceVariant
                        )
                    )
                }
            }
        }

        // Genres
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Primary Genres & Fusion Styles",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = WarmGold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        genres.forEach { genre ->
                            Surface(
                                color = ElectricPurple.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricPurple)
                            ) {
                                Text(
                                    text = genre,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ElectricPurple,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Gear Locker
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Gear Locker",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = WarmGold
                        )
                        Text(
                            text = "+ Add Gear",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanGlow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    myGearList.forEach { gear ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = null,
                                tint = CyanGlow,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = gear,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Audio Demos
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "My Audio Samples & Showcase",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = WarmGold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    myAudioTracks.forEach { track ->
                        AudioWaveformBar(
                            title = track.title,
                            durationStr = track.durationStr,
                            waveformPoints = track.waveformPoints,
                            modifier = Modifier.padding(bottom = 8.dp),
                            accentColor = CyanGlow
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted,
            fontSize = 10.sp
        )
    }
}
