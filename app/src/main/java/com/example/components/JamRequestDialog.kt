package com.example.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.JamRequest
import com.example.data.JamStatus
import com.example.data.Musician
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.StudioSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold

@Composable
fun JamRequestDialog(
    musician: Musician,
    onDismiss: () -> Unit,
    onSubmitRequest: (JamRequest) -> Unit
) {
    var selectedType by remember { mutableStateOf("Acoustic Fusion Jam") }
    var selectedDate by remember { mutableStateOf("This Saturday, 5:00 PM") }
    var locationInput by remember { mutableStateOf("Echo Sound Studios or BandConnect Virtual Room") }
    var noteInput by remember { mutableStateOf("Hey ${musician.name}! Would love to jam over some 7/8 folk grooves and record a couple of raw stems together.") }

    val jamTypes = listOf(
        "Acoustic Fusion Jam",
        "Studio Tracking Session",
        "Virtual WebRTC Session",
        "Songwriting & Lyrics"
    )

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
                .testTag("jam_request_dialog"),
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
                    Column {
                        Text(
                            text = "New Jam Request",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Inviting ${musician.name} (${musician.primaryInstrument})",
                            style = MaterialTheme.typography.bodySmall,
                            color = ElectricPurple
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Jam Format Selection
                Text(
                    text = "Select Jam Format",
                    style = MaterialTheme.typography.labelMedium,
                    color = WarmGold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    jamTypes.forEach { type ->
                        val isSelected = selectedType == type
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) ElectricPurple.copy(alpha = 0.25f) else StudioSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) ElectricPurple else StudioBorder
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedType = type }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = type,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected) ElectricPurple else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Handshake,
                                        contentDescription = "Selected format",
                                        tint = ElectricPurple
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Proposed Date
                Text(
                    text = "Proposed Date & Time",
                    style = MaterialTheme.typography.labelMedium,
                    color = WarmGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Date",
                            tint = CyanGlow
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricPurple,
                        unfocusedBorderColor = StudioBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Location / Platform
                Text(
                    text = "Jam Location / Studio",
                    style = MaterialTheme.typography.labelMedium,
                    color = WarmGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = locationInput,
                    onValueChange = { locationInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricPurple,
                        unfocusedBorderColor = StudioBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Note / Collaborative Vision
                Text(
                    text = "Collaborative Vision & Musical Notes",
                    style = MaterialTheme.typography.labelMedium,
                    color = WarmGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = noteInput,
                    onValueChange = { noteInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricPurple,
                        unfocusedBorderColor = StudioBorder
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button
                Button(
                    onClick = {
                        val req = JamRequest(
                            id = "jr_${System.currentTimeMillis()}",
                            artistId = musician.id,
                            artistName = musician.name,
                            artistRole = musician.primaryInstrument,
                            proposedDate = selectedDate,
                            jamType = selectedType,
                            location = locationInput,
                            note = noteInput,
                            status = JamStatus.PENDING
                        )
                        onSubmitRequest(req)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_jam_request_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Send Jam Request",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
