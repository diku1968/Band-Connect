package com.example.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.components.AudioWaveformBar
import com.example.data.FeedIdea
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DeepIndigo
import com.example.ui.theme.ElectricCoral
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.StudioSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold
import kotlinx.coroutines.launch

@Composable
fun IdeaFeedScreen(
    initialIdeas: List<FeedIdea>,
    modifier: Modifier = Modifier
) {
    val ideasList = remember { mutableStateListOf<FeedIdea>().apply { addAll(initialIdeas) } }
    var selectedTagFilter by remember { mutableStateOf("All") }
    var showUploadDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val tags = listOf("All", "#RawVocals", "#BeatStems", "#LyricSketch", "#FusionJam")

    val filteredIdeas = ideasList.filter { idea ->
        selectedTagFilter == "All" || idea.tag.equals(selectedTagFilter, ignoreCase = true)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showUploadDialog = true },
                containerColor = WarmGold,
                contentColor = Color.Black,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("upload_idea_fab")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Drop Idea"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Drop Idea / Vocal",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("idea_feed_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column {
                    Text(
                        text = "Raw Vocals & Ideas",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Where vocalists post unfiltered takes and producers find stems",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }

            // Tag Filters
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tags.forEach { tag ->
                        val isSelected = selectedTagFilter == tag
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedTagFilter = tag },
                            label = { Text(tag) },
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

            // Feed Items
            items(filteredIdeas, key = { it.id }) { idea ->
                IdeaFeedCard(
                    idea = idea,
                    onToggleLike = {
                        val index = ideasList.indexOfFirst { it.id == idea.id }
                        if (index != -1) {
                            val current = ideasList[index]
                            val newLiked = !current.isLiked
                            val newLikes = if (newLiked) current.likesCount + 1 else current.likesCount - 1
                            ideasList[index] = current.copy(isLiked = newLiked, likesCount = newLikes)
                        }
                    },
                    onDownloadStem = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Stem pack downloading for '${idea.title}' (24-bit WAV)")
                        }
                    },
                    onSendCollab = {
                        val index = ideasList.indexOfFirst { it.id == idea.id }
                        if (index != -1) {
                            val current = ideasList[index]
                            ideasList[index] = current.copy(collabsCount = current.collabsCount + 1)
                        }
                        scope.launch {
                            snackbarHostState.showSnackbar("Collaboration invitation sent to ${idea.authorName}!")
                        }
                    }
                )
            }
        }
    }

    if (showUploadDialog) {
        UploadIdeaDialog(
            onDismiss = { showUploadDialog = false },
            onPostIdea = { newIdea ->
                ideasList.add(0, newIdea)
                showUploadDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar("Audio snippet & lyrics published to feed!")
                }
            }
        )
    }
}

@Composable
fun IdeaFeedCard(
    idea: FeedIdea,
    onToggleLike: () -> Unit,
    onDownloadStem: () -> Unit,
    onSendCollab: () -> Unit
) {
    var isLyricsExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = StudioCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("feed_card_${idea.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top author row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        color = DeepIndigo,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricPurple)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = idea.authorAvatar,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = idea.authorName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${idea.authorRole} • ${idea.timeAgo}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    color = ElectricPurple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = idea.tag,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricPurple,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = idea.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Musical Metadata Pills (BPM & Key)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = StudioSurfaceVariant,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "BPM",
                            tint = WarmGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${idea.bpm} BPM",
                            style = MaterialTheme.typography.labelSmall,
                            color = WarmGold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    color = StudioSurfaceVariant,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Musical Key",
                            tint = CyanGlow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Key: ${idea.musicalKey}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanGlow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (idea.stemsAvailable) {
                    Surface(
                        color = NeonGreen.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Stems Ready",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonGreen,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Description
            Text(
                text = idea.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Audio Player Bar
            Spacer(modifier = Modifier.height(12.dp))
            AudioWaveformBar(
                title = "Raw Audio Snippet (${idea.durationStr})",
                durationStr = idea.durationStr,
                waveformPoints = idea.waveform,
                accentColor = WarmGold
            )

            // Expandable Lyrics Section
            if (idea.lyricsSnippet.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = StudioSurfaceVariant,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, StudioBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isLyricsExpanded = !isLyricsExpanded }
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TextSnippet,
                                    contentDescription = "Lyrics icon",
                                    tint = WarmGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Lyric Sheet & Verses",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = if (isLyricsExpanded) "Hide" else "Read Lyrics",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElectricPurple
                            )
                        }

                        AnimatedVisibility(visible = isLyricsExpanded) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    text = idea.lyricsSnippet,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onToggleLike() }
                ) {
                    Icon(
                        imageVector = if (idea.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (idea.isLiked) ElectricCoral else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${idea.likesCount}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (idea.isLiked) ElectricCoral else TextMuted
                    )
                }

                // Collabs Count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Handshake,
                        contentDescription = "Collab requests",
                        tint = WarmGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${idea.collabsCount} collabs",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Download Stems
                if (idea.stemsAvailable) {
                    OutlinedButton(
                        onClick = onDownloadStem,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanGlow)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download stems",
                            tint = CyanGlow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Stems", fontSize = 11.sp)
                    }
                }

                // Collab Button
                Button(
                    onClick = onSendCollab,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple)
                ) {
                    Text("Collaborate", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun UploadIdeaDialog(
    onDismiss: () -> Unit,
    onPostIdea: (FeedIdea) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("#RawVocals") }
    var bpmText by remember { mutableStateOf("92") }
    var keyText by remember { mutableStateOf("D Minor") }
    var description by remember { mutableStateOf("") }
    var lyrics by remember { mutableStateOf("") }
    var isRecordingSimulated by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, StudioBorder, RoundedCornerShape(24.dp)),
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
                    Text(
                        text = "Drop a Raw Musical Idea",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Microphone Audio Record Simulation Box
                Surface(
                    color = if (isRecordingSimulated) ElectricCoral.copy(alpha = 0.2f) else StudioSurfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isRecordingSimulated) ElectricCoral else StudioBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(14.dp)
                            .clickable { isRecordingSimulated = !isRecordingSimulated },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (isRecordingSimulated) ElectricCoral else WarmGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Mic",
                                    tint = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isRecordingSimulated) "Recording Live Vocals (0:34)..." else "Tap to Record Audio / Stems",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isRecordingSimulated) ElectricCoral else MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = if (isRecordingSimulated) "Input level: -6 dB • 24-bit PCM WAV" else "Built-in mic or USB audio interface",
                                    color = TextMuted,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        if (isRecordingSimulated) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(ElectricCoral)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title
                Text(text = "Idea Title / Track Name", style = MaterialTheme.typography.labelMedium, color = WarmGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("e.g. Sufi Rock hook in Dm") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricPurple,
                        unfocusedBorderColor = StudioBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Musical BPM and Key
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Tempo (BPM)", style = MaterialTheme.typography.labelMedium, color = WarmGold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = bpmText,
                            onValueChange = { bpmText = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricPurple,
                                unfocusedBorderColor = StudioBorder
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Musical Key", style = MaterialTheme.typography.labelMedium, color = WarmGold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = keyText,
                            onValueChange = { keyText = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricPurple,
                                unfocusedBorderColor = StudioBorder
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Collab Description
                Text(text = "What musicians/producers are you seeking?", style = MaterialTheme.typography.labelMedium, color = WarmGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Need Tabla grooving, acoustic guitar chords, or synth pads...") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricPurple,
                        unfocusedBorderColor = StudioBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Lyrics Sheet
                Text(text = "Lyrics / Song Sheet (Optional)", style = MaterialTheme.typography.labelMedium, color = WarmGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = lyrics,
                    onValueChange = { lyrics = it },
                    placeholder = { Text("Write or paste your verse and chorus lyrics...") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricPurple,
                        unfocusedBorderColor = StudioBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Publish Button
                Button(
                    onClick = {
                        val bpmInt = bpmText.toIntOrNull() ?: 90
                        val newIdea = FeedIdea(
                            id = "idea_${System.currentTimeMillis()}",
                            authorName = "You (Dhruv)",
                            authorRole = "Guitarist & Songwriter",
                            authorAvatar = "DS",
                            title = if (title.isBlank()) "Acoustic Melody in $keyText" else title,
                            tag = tag,
                            bpm = bpmInt,
                            musicalKey = if (keyText.isBlank()) "D Minor" else keyText,
                            description = if (description.isBlank()) "Looking for passionate local artists to jam on this idea!" else description,
                            lyricsSnippet = lyrics,
                            durationStr = "1:08",
                            waveform = listOf(0.4f, 0.7f, 0.9f, 0.6f, 0.85f, 0.75f, 0.9f, 0.5f, 0.3f),
                            likesCount = 1,
                            isLiked = true,
                            collabsCount = 0,
                            commentsCount = 0,
                            timeAgo = "Just now",
                            stemsAvailable = true
                        )
                        onPostIdea(newIdea)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("publish_idea_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Publish to Collaboration Feed",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
