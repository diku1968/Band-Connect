package com.example.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import com.example.components.ArtistDetailDialog
import com.example.components.AudioWaveformBar
import com.example.components.JamRequestDialog
import com.example.components.RadarMapView
import com.example.components.UserGuideDialog
import com.example.data.JamRequest
import com.example.data.Musician
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DeepIndigo
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.StudioSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FinderScreen(
    musicians: List<Musician>,
    onSendJamRequest: (JamRequest) -> Unit,
    modifier: Modifier = Modifier
) {
    var isRadarView by remember { mutableStateOf(true) }
    var radiusKm by remember { mutableFloatStateOf(25f) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedInstrumentFilter by remember { mutableStateOf("All") }
    var selectedGenreFilter by remember { mutableStateOf("All") }

    var selectedMusicianForDetail by remember { mutableStateOf<Musician?>(null) }
    var musicianForJamRequest by remember { mutableStateOf<Musician?>(null) }
    var radarSelectedMusician by remember { mutableStateOf<Musician?>(null) }
    var showGuideDialog by remember { mutableStateOf(false) }
    var showInstructionBanner by remember { mutableStateOf(true) }

    val instrumentFilters = listOf("All", "Tabla", "Vocalist", "Guitar", "Producer", "Flute", "Lyricist")
    val genreFilters = listOf("All", "Fusion", "Classical", "Sufi", "Prog Rock", "Lo-Fi", "Indie Folk")

    // Filter logic
    val filteredMusicians = musicians.filter { musician ->
        val withinRadius = musician.distanceKm <= radiusKm
        val matchesQuery = searchQuery.isBlank() ||
                musician.name.contains(searchQuery, ignoreCase = true) ||
                musician.primaryInstrument.contains(searchQuery, ignoreCase = true) ||
                musician.genres.any { it.contains(searchQuery, ignoreCase = true) }
        val matchesInstrument = selectedInstrumentFilter == "All" ||
                musician.primaryInstrument.contains(selectedInstrumentFilter, ignoreCase = true) ||
                musician.secondaryInstruments.any { it.contains(selectedInstrumentFilter, ignoreCase = true) }
        val matchesGenre = selectedGenreFilter == "All" ||
                musician.genres.any { it.contains(selectedGenreFilter, ignoreCase = true) }

        withinRadius && matchesQuery && matchesInstrument && matchesGenre
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("finder_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Header
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "JamLocal Radar",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Discover musicians & vocalists within ${radiusKm.roundToInt()} km",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }

                    // View Mode Switcher
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = StudioSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder)
                    ) {
                        Row(modifier = Modifier.padding(2.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isRadarView) ElectricPurple else Color.Transparent,
                                modifier = Modifier
                                    .clickable { isRadarView = true }
                                    .testTag("toggle_radar_view")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Radar,
                                        contentDescription = "Radar view",
                                        tint = if (isRadarView) Color.White else TextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Radar",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isRadarView) Color.White else TextMuted
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (!isRadarView) ElectricPurple else Color.Transparent,
                                modifier = Modifier
                                    .clickable { isRadarView = false }
                                    .testTag("toggle_list_view")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = "List view",
                                        tint = if (!isRadarView) Color.White else TextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "List",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (!isRadarView) Color.White else TextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Instruction Note / How to Use Guide Card
        item {
            AnimatedVisibility(visible = showInstructionBanner) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = StudioSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("instruction_note_card")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    modifier = Modifier.size(32.dp),
                                    shape = CircleShape,
                                    color = WarmGold.copy(alpha = 0.2f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = "Instruction Guide",
                                            tint = WarmGold,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Column {
                                    Text(
                                        text = "How to Use BandConnect",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = WarmGold
                                    )
                                    Text(
                                        text = "Quick 3-Step Musician Guide",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyanGlow,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Dismiss Note",
                                tint = TextMuted,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable { showInstructionBanner = false }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "1. Set Search Radius (5km–50km) below to discover local artists.\n2. Tap Radar markers or cards to inspect gear, audio stems & ratings.\n3. Tap 'Jam Request' to schedule live rehearsals or remote sessions.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { showGuideDialog = true },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = WarmGold),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Read Full Guide",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }

                            Text(
                                text = "Got it",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                modifier = Modifier
                                    .clickable { showInstructionBanner = false }
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by instrument, genre, or artist...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = CyanGlow
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_musician_input"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricPurple,
                    unfocusedBorderColor = StudioBorder,
                    focusedContainerColor = StudioCardBg,
                    unfocusedContainerColor = StudioCardBg
                ),
                singleLine = true
            )
        }

        // Distance Radius Slider Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.NearMe,
                                contentDescription = "Distance radius",
                                tint = WarmGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Search Radius",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            color = WarmGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${radiusKm.roundToInt()} km (${filteredMusicians.size} artists found)",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = WarmGold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Slider(
                        value = radiusKm,
                        onValueChange = { radiusKm = it },
                        valueRange = 5f..50f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = WarmGold,
                            activeTrackColor = ElectricPurple,
                            inactiveTrackColor = StudioBorder
                        ),
                        modifier = Modifier.testTag("distance_slider")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "5 km (Local)", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        Text(text = "25 km (Citywide)", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        Text(text = "50 km (Metro Area)", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    }
                }
            }
        }

        // Instrument Filter Chips
        item {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    instrumentFilters.forEach { instrument ->
                        val isSelected = selectedInstrumentFilter == instrument
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedInstrumentFilter = instrument },
                            label = { Text(instrument) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricPurple,
                                selectedLabelColor = Color.White,
                                containerColor = StudioCardBg,
                                labelColor = TextMuted
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) ElectricPurple else StudioBorder
                            )
                        )
                    }
                }
            }
        }

        // If Radar Mode is selected:
        if (isRadarView) {
            item {
                RadarMapView(
                    musicians = filteredMusicians,
                    maxRadiusKm = radiusKm,
                    selectedMusician = radarSelectedMusician,
                    onSelectMusician = { musician ->
                        radarSelectedMusician = musician
                    }
                )
            }

            // Quick Preview Card for Radar Selected Musician
            item {
                AnimatedVisibility(visible = radarSelectedMusician != null) {
                    radarSelectedMusician?.let { musician ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = StudioCardBg),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricPurple),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            modifier = Modifier.size(42.dp),
                                            shape = CircleShape,
                                            color = DeepIndigo
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = musician.avatarInitials,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = musician.name,
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = "${musician.primaryInstrument} • ${musician.distanceKm} km away",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = CyanGlow
                                            )
                                        }
                                    }

                                    Surface(
                                        color = if (musician.isOpenForJams) NeonGreen.copy(alpha = 0.2f) else StudioSurfaceVariant,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = if (musician.isOpenForJams) "Open to Jam" else "Busy",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (musician.isOpenForJams) NeonGreen else TextMuted
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = musician.bio,
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { selectedMusicianForDetail = musician },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricPurple)
                                    ) {
                                        Text("View Portfolio")
                                    }

                                    Button(
                                        onClick = { musicianForJamRequest = musician },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = WarmGold)
                                    ) {
                                        Text("Jam Request", color = Color.Black, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section header for Results
        item {
            Text(
                text = "Nearby Artists (${filteredMusicians.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Musician Cards List
        items(filteredMusicians, key = { it.id }) { musician ->
            MusicianCard(
                musician = musician,
                onViewProfile = { selectedMusicianForDetail = musician },
                onJamRequest = { musicianForJamRequest = musician }
            )
        }
    }

    // Modal Dialogs
    selectedMusicianForDetail?.let { musician ->
        ArtistDetailDialog(
            musician = musician,
            onDismiss = { selectedMusicianForDetail = null },
            onOpenJamRequest = {
                selectedMusicianForDetail = null
                musicianForJamRequest = it
            }
        )
    }

    musicianForJamRequest?.let { musician ->
        JamRequestDialog(
            musician = musician,
            onDismiss = { musicianForJamRequest = null },
            onSubmitRequest = { req ->
                onSendJamRequest(req)
                musicianForJamRequest = null
            }
        )
    }

    if (showGuideDialog) {
        UserGuideDialog(
            onDismiss = { showGuideDialog = false }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MusicianCard(
    musician: Musician,
    onViewProfile: () -> Unit,
    onJamRequest: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = StudioCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("musician_card_${musician.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box {
                        Surface(
                            modifier = Modifier.size(50.dp),
                            shape = CircleShape,
                            color = DeepIndigo,
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricPurple)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = musician.avatarInitials,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                        if (musician.isOpenForJams) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(NeonGreen)
                                    .border(2.dp, StudioCardBg, CircleShape)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = musician.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = musician.primaryInstrument,
                            style = MaterialTheme.typography.bodyMedium,
                            color = WarmGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Surface(
                    color = StudioSurfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = CyanGlow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${musician.distanceKm} km",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanGlow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = musician.locationName,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = WarmGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${musician.rating} (${musician.totalJams} jams)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Genres & tags
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                musician.genres.forEach { genre ->
                    Surface(
                        color = ElectricPurple.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = genre,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricPurple,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    color = StudioSurfaceVariant,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = musician.skillLevel,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            // Audio Sample Preview
            if (musician.audioSnippets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                val firstAudio = musician.audioSnippets.first()
                AudioWaveformBar(
                    title = firstAudio.title,
                    durationStr = firstAudio.durationStr,
                    waveformPoints = firstAudio.waveformPoints,
                    accentColor = ElectricPurple
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Card Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onViewProfile,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
                ) {
                    Text("Portfolio & Gear", fontSize = 13.sp)
                }

                Button(
                    onClick = onJamRequest,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmGold)
                ) {
                    Icon(
                        imageVector = Icons.Default.Handshake,
                        contentDescription = "Jam",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Jam Request", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}
