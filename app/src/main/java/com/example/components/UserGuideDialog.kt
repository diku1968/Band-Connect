package com.example.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricCoral
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.StudioSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold

@Composable
fun UserGuideDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, StudioBorder, RoundedCornerShape(24.dp))
                .testTag("user_guide_dialog"),
            colors = CardDefaults.cardColors(containerColor = StudioCardBg)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(38.dp),
                            shape = CircleShape,
                            color = WarmGold.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, WarmGold)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = "User Guide",
                                    tint = WarmGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "How to Use BandConnect",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Quick User Manual & Step-by-Step Guide",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyanGlow,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_guide_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Intro banner
                Surface(
                    color = StudioSurfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, StudioBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Welcome to BandConnect / JamLocal! Here is a simple guide to discovering local artists, exchanging raw audio stems, scheduling jams, and collaborating seamlessly.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(12.dp),
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Step 1: Radar Discovery
                GuideStepCard(
                    stepNumber = "1",
                    title = "Find Artists Nearby (Radar & List)",
                    icon = Icons.Default.Radar,
                    iconColor = CyanGlow,
                    points = listOf(
                        "Slide the Search Radius bar (5 km to 50 km) to widen or narrow your local search.",
                        "Tap any marker pin on the Radar to see live artist details and preview their music.",
                        "Toggle to 'List' view for a structured view with audio sample waveforms.",
                        "Use instrument chips (Tabla, Vocalist, Guitar, Producer, Flute) to find specific collaborators."
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Step 2: Idea & Stem Feed
                GuideStepCard(
                    stepNumber = "2",
                    title = "Raw Vocals & Idea Exchange Feed",
                    icon = Icons.Default.GraphicEq,
                    iconColor = WarmGold,
                    points = listOf(
                        "Listen to raw vocal sketches, chord progressions, and beat drops using the Play button.",
                        "Check the musical Key (e.g., C# Minor) and Tempo (BPM) pills to match your instrumentation.",
                        "Tap 'Read Lyrics' to open songwriter lyrics sheets.",
                        "Click 'Stems' to download 24-bit audio stems for your digital audio workstation (DAW).",
                        "Tap the yellow 'Drop Idea / Vocal' button to record or paste your own melody."
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Step 3: Jam Requests & Direct Chat
                GuideStepCard(
                    stepNumber = "3",
                    title = "Send Jam Requests & Direct Chat",
                    icon = Icons.Default.Handshake,
                    iconColor = ElectricPurple,
                    points = listOf(
                        "Tap 'Jam Request' on any artist card to initiate a collaboration proposal.",
                        "Choose your jam format: Acoustic Jam, Studio Tracking, Virtual WebRTC, or Songwriting.",
                        "Go to the 'Jam Center' tab to approve pending invites, confirm studio slots, or decline.",
                        "Chat directly with the artist, share rehearsal locations, and sync stems in real time."
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Step 4: Musician Portfolio & Gear Locker
                GuideStepCard(
                    stepNumber = "4",
                    title = "Your Musician Portfolio & Gear Locker",
                    icon = Icons.Default.Person,
                    iconColor = NeonGreen,
                    points = listOf(
                        "Visit the 'Portfolio' tab to showcase your primary instruments, genres, and skill level.",
                        "List your hardware gear (guitars, mics, audio interfaces, monitors) in the Gear Locker.",
                        "Toggle the 'Open for Jam Invites' switch to control your visibility on the nearby radar map."
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Step 5: Bottom AdMob Ads
                GuideStepCard(
                    stepNumber = "5",
                    title = "Bottom AdMob Sponsored Banner",
                    icon = Icons.Default.AdsClick,
                    iconColor = ElectricCoral,
                    points = listOf(
                        "A Google Mobile Ads (AdMob) banner is pinned at the bottom above the navigation bar.",
                        "Features local music gear discounts, studio booking offers, and audio promotions.",
                        "You can collapse the banner anytime using the 'X' dismiss button on the right."
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Done button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_guide_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Got It! Let's Jam 🎵",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun GuideStepCard(
    stepNumber: String,
    title: String,
    icon: ImageVector,
    iconColor: Color,
    points: List<String>
) {
    Surface(
        color = StudioSurfaceVariant,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier.size(28.dp),
                    shape = CircleShape,
                    color = iconColor.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, iconColor)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = stepNumber,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = iconColor
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                points.forEach { point ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "•",
                            color = iconColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Text(
                            text = point,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}
