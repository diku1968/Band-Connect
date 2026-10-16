package com.example.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun ArchitectureScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("architecture_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "System Architecture & Specs",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Production-grade Blueprint: Mobile, Realtime Backend, Geo-spatial, & Audio Pipeline",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }
        }

        // Section 1: Mobile Client Architecture
        item {
            ArchSectionCard(
                icon = Icons.Default.Layers,
                accentColor = ElectricPurple,
                title = "1. Mobile Frontend Architecture (Android)",
                subtitle = "Jetpack Compose, Clean Architecture, ExoPlayer/Media3 & FusedLocation",
                initiallyExpanded = true
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• Presentation Layer: Declarative Jetpack Compose using MVI / Unidirectional Data Flow (StateFlow / SharedFlow) with Material Design 3.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• Audio Engine: AndroidX Media3 (ExoPlayer) with custom AudioWaveform visualizer, buffer preloading for stem previews, and OpenSL ES / AAudio for low-latency live microphone monitoring.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• Location Engine: Google Fused Location Provider Client with battery-conscious geofencing and geohash clustering.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• Local Cache: Room Database + Kotlinx Serialization for offline feed caching, draft lyric memos, and encrypted credentials.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section 2: Realtime Backend & API
        item {
            ArchSectionCard(
                icon = Icons.Default.Speed,
                accentColor = CyanGlow,
                title = "2. Backend API & Real-time Layer",
                subtitle = "Node.js (NestJS/Fastify) or Go + WebSockets + WebRTC Gateway",
                initiallyExpanded = true
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• API Gateway & Auth: JWT / OAuth2 (Google Sign-In) with rate limiting and Envoy / Kong ingress.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• WebSocket Gateway: Socket.io / ws cluster backed by Redis Pub/Sub for instant chat delivery, live radar proximity pings, and typing indicators.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• WebRTC Mesh Server (LiveJam): LiveKit or Janus SFU for ultra-low latency peer audio streaming during virtual jam rehearsals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section 3: Geolocation Database
        item {
            ArchSectionCard(
                icon = Icons.Default.Map,
                accentColor = WarmGold,
                title = "3. Geolocation & Spatial Indexing Strategy",
                subtitle = "PostgreSQL + PostGIS & Redis Geospatial (GEOSEARCH)",
                initiallyExpanded = true
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• Primary DB: PostgreSQL with PostGIS extension. Uses Geography(Point, 4326) and GiST (Generalized Search Tree) spatial indexing.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Code snippet box
                    Surface(
                        color = StudioSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "-- PostGIS Radius Search Query (e.g. 25km radius)",
                                style = MaterialTheme.typography.labelSmall,
                                color = WarmGold,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "SELECT id, name, instruments, ST_Distance(location, ST_MakePoint(77.64, 12.97)::geography) / 1000 AS distance_km\nFROM musicians\nWHERE is_open_for_jams = true\n  AND ST_DWithin(location, ST_MakePoint(77.64, 12.97)::geography, 25000)\nORDER BY distance_km ASC;",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyanGlow,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Text(
                        text = "• In-Memory Radar Caching: Redis GEOADD and GEOSEARCH with BYRADIUS 25 km for sub-5ms radar polling during active app sessions.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section 4: Audio & Media Strategy
        item {
            ArchSectionCard(
                icon = Icons.Default.Storage,
                accentColor = NeonGreen,
                title = "4. Audio Storage & Stem Transcoding Pipeline",
                subtitle = "Cloudflare R2 / AWS S3 + FFmpeg Worker + HLS & Waveform Generator",
                initiallyExpanded = true
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• Storage: Cloudflare R2 (zero egress fees for high-bandwidth audio streaming) or Amazon S3 with signed upload URLs (direct client-to-bucket upload).",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "• Transcoder Pipeline: Worker service triggered via S3 Event -> RabbitMQ/SQS -> FFmpeg container:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "   1. Converts uncompressed WAV (24-bit/48kHz) to high-efficiency AAC/Opus (128 kbps) for fast mobile feed streaming.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                    Text(
                        text = "   2. Generates normalized 100-point JSON waveform array for UI rendering without client-side heavy decoding.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                    Text(
                        text = "   3. Archives full-fidelity stems (.zip / .wav) for collaborator stem downloads.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }
        }

        // Section 5: MVP Development Roadmap
        item {
            ArchSectionCard(
                icon = Icons.Default.RocketLaunch,
                accentColor = ElectricCoral,
                title = "5. Step-by-Step MVP Development Roadmap",
                subtitle = "4-Phase execution plan from Prototype to Production Launch",
                initiallyExpanded = true
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    RoadmapPhaseItem(
                        phase = "Sprint 1-2 (Phase 1)",
                        title = "Core Profiles & Geo-Discovery MVP",
                        tasks = listOf(
                            "Auth (Google/Phone OTP) and Musician Onboarding (Instruments, Gear, Genres, Bio).",
                            "PostGIS schema setup with latitude/longitude coordinates & radius query API.",
                            "Android Radar Map & List View with 5km-50km interactive distance filter."
                        )
                    )
                    RoadmapPhaseItem(
                        phase = "Sprint 3-4 (Phase 2)",
                        title = "Raw Audio Feed & Stem Uploads",
                        tasks = listOf(
                            "S3/R2 direct audio upload with client audio recording in Android (AAC/PCM).",
                            "Waveform extractor worker generating JSON visualizer amplitudes.",
                            "Feed UI with Play/Pause waveform player, BPM/Key pills, and lyric sheet modal."
                        )
                    )
                    RoadmapPhaseItem(
                        phase = "Sprint 5-6 (Phase 3)",
                        title = "Jam Requests, Studio Booking & Chat",
                        tasks = listOf(
                            "Jam Request workflow (Pending, Accepted, Scheduled) with push notifications via FCM.",
                            "WebSocket-based 1-on-1 and Group Chat with audio stem attachments.",
                            "Google Calendar / ICS sync for scheduled rehearsal sessions."
                        )
                    )
                    RoadmapPhaseItem(
                        phase = "Sprint 7+ (Phase 4)",
                        title = "Low-Latency Virtual Jams & Growth",
                        tasks = listOf(
                            "WebRTC SFU room for remote vocal/chord auditioning.",
                            "Local Jam Room / Rehearsal Studio directory integration.",
                            "Community ratings, verified session player badges, and stem marketplace."
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ArchSectionCard(
    icon: ImageVector,
    accentColor: Color,
    title: String,
    subtitle: String,
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }

    Card(
        colors = CardDefaults.cardColors(containerColor = StudioCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        color = accentColor.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = TextMuted
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun RoadmapPhaseItem(phase: String, title: String, tasks: List<String>) {
    Surface(
        color = StudioSurfaceVariant,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = phase,
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmGold,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            tasks.forEach { task ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        color = CyanGlow,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = task,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
