package com.example.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.ChatMessage
import com.example.data.JamRequest
import com.example.data.JamStatus
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

@Composable
fun JamCenterScreen(
    jamRequests: List<JamRequest>,
    initialChats: List<ChatMessage>,
    modifier: Modifier = Modifier
) {
    val requestsList = remember { mutableStateListOf<JamRequest>().apply { addAll(jamRequests) } }
    val chatsList = remember { mutableStateListOf<ChatMessage>().apply { addAll(initialChats) } }

    var selectedTab by remember { mutableStateOf(0) } // 0 = Jam Invitations, 1 = Direct Chat Room
    var messageInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("jam_center_screen")
    ) {
        // Header
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Jam Center & Chat",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Coordinate live rehearsals, studio bookings & virtual sessions",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Tab switch
            Surface(
                color = StudioSurfaceVariant,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    Surface(
                        color = if (selectedTab == 0) ElectricPurple else Color.Transparent,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = 0 }
                            .testTag("tab_jam_invites")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Handshake,
                                contentDescription = "Jam requests",
                                tint = if (selectedTab == 0) Color.White else TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Jam Requests (${requestsList.size})",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selectedTab == 0) Color.White else TextMuted,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Surface(
                        color = if (selectedTab == 1) ElectricPurple else Color.Transparent,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = 1 }
                            .testTag("tab_direct_chat")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = "Direct Chat",
                                tint = if (selectedTab == 1) Color.White else TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Direct Studio Chat",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selectedTab == 1) Color.White else TextMuted,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        if (selectedTab == 0) {
            // Jam Requests List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(requestsList, key = { it.id }) { request ->
                    JamRequestCard(
                        request = request,
                        onAccept = {
                            val index = requestsList.indexOfFirst { it.id == request.id }
                            if (index != -1) {
                                requestsList[index] = request.copy(status = JamStatus.ACCEPTED)
                            }
                        },
                        onSchedule = {
                            val index = requestsList.indexOfFirst { it.id == request.id }
                            if (index != -1) {
                                requestsList[index] = request.copy(status = JamStatus.SCHEDULED)
                            }
                        },
                        onDecline = {
                            val index = requestsList.indexOfFirst { it.id == request.id }
                            if (index != -1) {
                                requestsList[index] = request.copy(status = JamStatus.DECLINED)
                            }
                        },
                        onOpenChat = { selectedTab = 1 }
                    )
                }
            }
        } else {
            // Direct Chat Room with Artist
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                // Active chat header
                Surface(
                    color = StudioCardBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box {
                                Surface(
                                    modifier = Modifier.size(36.dp),
                                    shape = CircleShape,
                                    color = DeepIndigo
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = "RD", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(NeonGreen)
                                        .border(1.5.dp, StudioCardBg, CircleShape)
                                        .align(Alignment.BottomEnd)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Rohan Deshmukh",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Tabla Player • In Studio Session",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CyanGlow,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Virtual Call icon
                        Surface(
                            color = StudioSurfaceVariant,
                            shape = CircleShape
                        ) {
                            IconButton(onClick = { /* Start virtual jam */ }) {
                                Icon(
                                    imageVector = Icons.Default.VideoCall,
                                    contentDescription = "Virtual Jam WebRTC Room",
                                    tint = WarmGold
                                )
                            }
                        }
                    }
                }

                // Chat Messages List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(chatsList, key = { it.id }) { msg ->
                        ChatBubble(message = msg)
                    }
                }

                // Quick chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = StudioSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable {
                            messageInput = "Echo Sound Studio booked for Saturday 4 PM!"
                        }
                    ) {
                        Text(
                            text = "📍 Confirm Echo Sound Studio",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = WarmGold
                        )
                    }

                    Surface(
                        color = StudioSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable {
                            messageInput = "Sending guitar scratch track (D minor, 120 BPM)..."
                        }
                    ) {
                        Text(
                            text = "🎵 Share Stem Audio",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanGlow
                        )
                    }
                }

                // Message Input Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        placeholder = { Text("Message Rohan or share rehearsal plan...") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_field"),
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricPurple,
                            unfocusedBorderColor = StudioBorder,
                            focusedContainerColor = StudioCardBg,
                            unfocusedContainerColor = StudioCardBg
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (messageInput.isNotBlank()) {
                                chatsList.add(
                                    ChatMessage(
                                        id = "c_${System.currentTimeMillis()}",
                                        senderName = "Me",
                                        text = messageInput,
                                        timestamp = "Now",
                                        isMe = true
                                    )
                                )
                                messageInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(WarmGold)
                            .testTag("send_chat_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send message",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JamRequestCard(
    request: JamRequest,
    onAccept: () -> Unit,
    onSchedule: () -> Unit,
    onDecline: () -> Unit,
    onOpenChat: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = StudioCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with status badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(38.dp),
                        shape = CircleShape,
                        color = DeepIndigo
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = request.artistName.take(2).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = request.artistName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = request.artistRole,
                            style = MaterialTheme.typography.bodySmall,
                            color = WarmGold
                        )
                    }
                }

                // Status chip
                val (statusColor, statusText) = when (request.status) {
                    JamStatus.PENDING -> Pair(WarmGold, "Pending Approval")
                    JamStatus.ACCEPTED -> Pair(NeonGreen, "Jam Accepted")
                    JamStatus.SCHEDULED -> Pair(CyanGlow, "Studio Scheduled")
                    JamStatus.DECLINED -> Pair(ElectricCoral, "Declined")
                }

                Surface(
                    color = statusColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusColor)
                ) {
                    Text(
                        text = statusText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Jam details
            Text(
                text = request.jamType,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = ElectricPurple
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Date",
                        tint = CyanGlow,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = request.proposedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = WarmGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = request.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"${request.note}\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action row
            when (request.status) {
                JamStatus.PENDING -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDecline,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricCoral)
                        ) {
                            Text("Decline")
                        }

                        Button(
                            onClick = onAccept,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WarmGold)
                        ) {
                            Text("Accept Jam", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                JamStatus.ACCEPTED -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onSchedule,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanGlow)
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Confirm Studio Slot")
                        }

                        Button(
                            onClick = onOpenChat,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple)
                        ) {
                            Text("Open Chat")
                        }
                    }
                }
                JamStatus.SCHEDULED -> {
                    Button(
                        onClick = onOpenChat,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple)
                    ) {
                        Text("Open Jam Chat & Sync Tracks")
                    }
                }
                JamStatus.DECLINED -> {
                    Text(
                        text = "Invitation declined.",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isMe = message.isMe
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
            modifier = Modifier.fillMaxWidth(0.82f)
        ) {
            Surface(
                color = if (isMe) ElectricPurple else StudioCardBg,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isMe) 16.dp else 2.dp,
                    bottomEnd = if (isMe) 2.dp else 16.dp
                ),
                border = if (!isMe) androidx.compose.foundation.BorderStroke(1.dp, StudioBorder) else null
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${message.senderName} • ${message.timestamp}",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted,
                fontSize = 10.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}
