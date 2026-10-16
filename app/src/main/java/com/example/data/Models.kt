package com.example.data

enum class JamStatus {
    PENDING,
    ACCEPTED,
    SCHEDULED,
    DECLINED
}

data class AudioTrack(
    val id: String,
    val title: String,
    val durationStr: String,
    val genre: String,
    val waveformPoints: List<Float> = listOf(0.3f, 0.6f, 0.9f, 0.4f, 0.8f, 0.5f, 0.7f, 0.9f, 0.4f, 0.7f, 0.3f)
)

data class Musician(
    val id: String,
    val name: String,
    val handle: String,
    val avatarInitials: String,
    val primaryInstrument: String,
    val secondaryInstruments: List<String>,
    val genres: List<String>,
    val skillLevel: String,
    val distanceKm: Float,
    val locationName: String,
    val angleDegrees: Float,
    val gearOwned: List<String>,
    val audioSnippets: List<AudioTrack>,
    val bio: String,
    val isOpenForJams: Boolean = true,
    val studioAvailable: Boolean = false,
    val rating: Float = 4.9f,
    val totalJams: Int = 18
)

data class FeedIdea(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val authorAvatar: String,
    val title: String,
    val tag: String,
    val bpm: Int,
    val musicalKey: String,
    val description: String,
    val lyricsSnippet: String = "",
    val durationStr: String,
    val waveform: List<Float>,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val collabsCount: Int,
    val commentsCount: Int,
    val timeAgo: String,
    val stemsAvailable: Boolean = true
)

data class JamRequest(
    val id: String,
    val artistId: String,
    val artistName: String,
    val artistRole: String,
    val proposedDate: String,
    val jamType: String,
    val location: String,
    val note: String,
    val status: JamStatus = JamStatus.PENDING
)

data class ChatMessage(
    val id: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isMe: Boolean,
    val jamInvitation: JamRequest? = null
)
