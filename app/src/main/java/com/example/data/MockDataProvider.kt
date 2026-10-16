package com.example.data

object MockDataProvider {

    val sampleMusicians = listOf(
        Musician(
            id = "m1",
            name = "Rohan Deshmukh",
            handle = "@rohan_tabla_grooves",
            avatarInitials = "RD",
            primaryInstrument = "Tabla",
            secondaryInstruments = listOf("Djembe", "Kanjira"),
            genres = listOf("Indian Classical", "Fusion", "World Jazz"),
            skillLevel = "Session Pro (12 yrs)",
            distanceKm = 4.2f,
            locationName = "Indiranagar, Stage 2 (4.2 km)",
            angleDegrees = 45f,
            gearOwned = listOf("Banaras Gharana Brass Bayan", "Shure Beta 91A", "Audix D6"),
            audioSnippets = listOf(
                AudioTrack("t1", "Teentaal 140BPM Fusion Solo", "1:24", "Fusion", listOf(0.4f, 0.8f, 0.9f, 0.6f, 0.85f, 0.95f, 0.7f, 0.6f, 0.9f, 0.4f, 0.8f)),
                AudioTrack("t2", "Rupak Taal Grooves with Bass", "0:52", "World", listOf(0.3f, 0.5f, 0.7f, 0.8f, 0.6f, 0.7f, 0.8f, 0.4f, 0.3f))
            ),
            bio = "Banaras Gharana disciple. Love collaborating with funk bassists, prog-rock guitarists, and electronic beat producers. Have an isolated home recording room.",
            isOpenForJams = true,
            studioAvailable = true,
            rating = 4.95f,
            totalJams = 34
        ),
        Musician(
            id = "m2",
            name = "Meera Krishnan",
            handle = "@meera_vocals",
            avatarInitials = "MK",
            primaryInstrument = "Vocalist",
            secondaryInstruments = listOf("Harmonium", "Tanpura"),
            genres = listOf("Carnatic Fusion", "Sufi", "Indie Folk"),
            skillLevel = "Touring Artist",
            distanceKm = 7.8f,
            locationName = "Koramangala 4th Block (7.8 km)",
            angleDegrees = 130f,
            gearOwned = listOf("Neumann TLM 102", "Apollo Twin X Quad", "Sennheiser HD650"),
            audioSnippets = listOf(
                AudioTrack("t3", "Acoustic Raga Charukesi Improvisation", "2:10", "Carnatic Fusion", listOf(0.5f, 0.7f, 0.9f, 0.85f, 0.75f, 0.9f, 0.95f, 0.6f, 0.4f)),
                AudioTrack("t4", "Sufi Hook 'Naino Mein'", "1:05", "Sufi", listOf(0.3f, 0.6f, 0.8f, 0.9f, 0.85f, 0.7f, 0.5f, 0.6f))
            ),
            bio = "Classically trained vocalist blending ancient alaap with dream pop and lo-fi textures. Looking for composers and arrangers for my upcoming EP.",
            isOpenForJams = true,
            studioAvailable = false,
            rating = 5.0f,
            totalJams = 27
        ),
        Musician(
            id = "m3",
            name = "Arjun Sengupta",
            handle = "@arjun_strats",
            avatarInitials = "AS",
            primaryInstrument = "Electric Guitar",
            secondaryInstruments = listOf("Acoustic Guitar", "Mandolin"),
            genres = listOf("Prog Rock", "Psychedelic Blues", "Fusion"),
            skillLevel = "Advanced",
            distanceKm = 11.5f,
            locationName = "HSR Layout Sector 1 (11.5 km)",
            angleDegrees = 210f,
            gearOwned = listOf("Fender American Ultra Strat", "Line 6 Helix Floor", "Two Notes Torpedo"),
            audioSnippets = listOf(
                AudioTrack("t5", "Ambient Delay Swell Solo in Dm", "1:45", "Prog Rock", listOf(0.2f, 0.4f, 0.7f, 0.95f, 0.8f, 0.6f, 0.85f, 0.9f, 0.3f))
            ),
            bio = "Lead & rhythm guitarist addicted to 7/8 time signatures and ambient reverb landscapes. Down for weekend rehearsals and studio tracking.",
            isOpenForJams = true,
            studioAvailable = true,
            rating = 4.85f,
            totalJams = 19
        ),
        Musician(
            id = "m4",
            name = "Kabir 'K-Beat' Roy",
            handle = "@kbeat_producer",
            avatarInitials = "KR",
            primaryInstrument = "Music Producer",
            secondaryInstruments = listOf("Synthesizer", "Drum Machine"),
            genres = listOf("Lo-Fi Beats", "Boom Bap", "Synthwave"),
            skillLevel = "Session Pro (DAW Guru)",
            distanceKm = 16.2f,
            locationName = "JP Nagar 7th Phase (16.2 km)",
            angleDegrees = 310f,
            gearOwned = listOf("Ableton Push 3", "Moog Subsequent 37", "Genelec 8030C Monitors"),
            audioSnippets = listOf(
                AudioTrack("t6", "Monsoon Midnight Lo-Fi Beat", "1:15", "Lo-Fi", listOf(0.6f, 0.7f, 0.8f, 0.75f, 0.85f, 0.9f, 0.7f, 0.8f, 0.5f))
            ),
            bio = "Beatmaker & sound designer. I sample acoustic instruments and build punchy vintage drum pockets. Let's record your live stems at my studio!",
            isOpenForJams = true,
            studioAvailable = true,
            rating = 4.9f,
            totalJams = 42
        ),
        Musician(
            id = "m5",
            name = "Tanvi Bansal",
            handle = "@tanvi_lyricist",
            avatarInitials = "TB",
            primaryInstrument = "Songwriter / Lyricist",
            secondaryInstruments = listOf("Ukulele", "Melodica"),
            genres = listOf("Indie Pop", "Acoustic Poetry", "Contemporary Ghazal"),
            skillLevel = "Published Lyricist",
            distanceKm = 24.0f,
            locationName = "Whitefield (24.0 km)",
            angleDegrees = 85f,
            gearOwned = listOf("Aston Origin Mic", "Logic Pro X", "Yamaha FS800 Guitar"),
            audioSnippets = listOf(
                AudioTrack("t7", "Voice Memo: 'Pukaar' Chorus Idea", "0:48", "Indie Folk", listOf(0.4f, 0.6f, 0.8f, 0.65f, 0.7f, 0.5f, 0.3f))
            ),
            bio = "Hindi and Urdu lyricist writing evocative, cinematic hooks. Looking for melodic singers and acoustic guitarists to build complete songs together.",
            isOpenForJams = true,
            studioAvailable = false,
            rating = 4.92f,
            totalJams = 15
        ),
        Musician(
            id = "m6",
            name = "Pranav Nair",
            handle = "@pranav_flute",
            avatarInitials = "PN",
            primaryInstrument = "Bansuri (Flute)",
            secondaryInstruments = listOf("Tin Whistle", "Saxophone"),
            genres = listOf("Indian Classical", "Ambient Fusion", "Film Score"),
            skillLevel = "Touring Artist",
            distanceKm = 38.5f,
            locationName = "Yelahanka New Town (38.5 km)",
            angleDegrees = 350f,
            gearOwned = listOf("Subhash Dhiman E Natural Bansuri", "DPA 4099 Mic", "Strymon BigSky"),
            audioSnippets = listOf(
                AudioTrack("t8", "Raag Yaman Meditative Lead", "2:30", "Ambient Classical", listOf(0.3f, 0.6f, 0.7f, 0.9f, 0.85f, 0.7f, 0.6f, 0.4f))
            ),
            bio = "Bamboo flute player inspired by Hariprasad Chaurasia and modern cinematic film music. Open to studio sessions and live cross-genre jams.",
            isOpenForJams = true,
            studioAvailable = true,
            rating = 4.98f,
            totalJams = 23
        )
    )

    val sampleIdeas = listOf(
        FeedIdea(
            id = "idea_1",
            authorName = "Meera Krishnan",
            authorRole = "Carnatic Vocalist",
            authorAvatar = "MK",
            title = "Raw Alaap in Raag Bhairavi (Key: C# Minor) - Seeking Tabla & Bass Stems!",
            tag = "#RawVocals",
            bpm = 96,
            musicalKey = "C# Minor",
            description = "Woke up with this melodic curve lingering in my mind. Recorded raw through my TLM 102 without tuning or reverb. Seeking a Tabla player for odd-meter grooving and a sub-bass producer to turn this into a deep ambient track.",
            lyricsSnippet = "तू ही किनारे, तू ही दरिया...\nबहती हवा सा तेरा साया...\n(Chorus hook ready, verse open for co-writing)",
            durationStr = "1:14",
            waveform = listOf(0.2f, 0.5f, 0.85f, 0.95f, 0.7f, 0.8f, 0.9f, 0.65f, 0.4f, 0.85f, 0.7f, 0.3f),
            likesCount = 48,
            isLiked = true,
            collabsCount = 6,
            commentsCount = 12,
            timeAgo = "2 hours ago",
            stemsAvailable = true
        ),
        FeedIdea(
            id = "idea_2",
            authorName = "Kabir 'K-Beat' Roy",
            authorRole = "Music Producer",
            authorAvatar = "KR",
            title = "Heavy 7/8 Mumbai Street Percussion Groove (Free Stems Download)",
            tag = "#BeatStems",
            bpm = 88,
            musicalKey = "F Dorian",
            description = "Sampled live dholak and matched with an analog 808 sub kick and tape-saturated claps. Need a lead instrument (Bansuri, Sitar, or Saxophone) to layer melodies over this verse-chorus arrangement.",
            lyricsSnippet = "[Instrumental beat drop. Stem pack includes: 808_Sub.wav, Perc_Loops.wav, Chord_Pads.wav]",
            durationStr = "0:56",
            waveform = listOf(0.7f, 0.9f, 0.6f, 0.85f, 0.95f, 0.75f, 0.9f, 0.7f, 0.8f, 0.6f, 0.9f, 0.4f),
            likesCount = 84,
            isLiked = false,
            collabsCount = 14,
            commentsCount = 21,
            timeAgo = "5 hours ago",
            stemsAvailable = true
        ),
        FeedIdea(
            id = "idea_3",
            authorName = "Tanvi Bansal",
            authorRole = "Songwriter / Lyricist",
            authorAvatar = "TB",
            title = "Acoustic Ballad Lyric Sheet: 'Subah Ki Chhaya'",
            tag = "#LyricSketch",
            bpm = 72,
            musicalKey = "G Major",
            description = "Wrote this narrative about long distance friendships in bustling cities. Sung as a scratch demo with simple nylon guitar chords. Any singer/composer free to take this melody and rework it!",
            lyricsSnippet = "खिड़की पे बैठी धूप भी अब सवाल पूछती है...\nवो रास्ते जो साथ चले, मंजिल क्यों रूठती है...\n\n(Hook):\nतेरी मेरी बात बाकी है,\nएक अधूरी रात बाकी है...",
            durationStr = "1:42",
            waveform = listOf(0.3f, 0.4f, 0.6f, 0.75f, 0.8f, 0.7f, 0.65f, 0.7f, 0.5f, 0.4f, 0.2f),
            likesCount = 62,
            isLiked = true,
            collabsCount = 9,
            commentsCount = 17,
            timeAgo = "Yesterday",
            stemsAvailable = false
        )
    )

    val sampleJamRequests = listOf(
        JamRequest(
            id = "jr_1",
            artistId = "m1",
            artistName = "Rohan Deshmukh",
            artistRole = "Tabla Player",
            proposedDate = "This Saturday, 4:00 PM",
            jamType = "Acoustic Fusion Jam",
            location = "Echo Sound Studios, Indiranagar",
            note = "Hey! Loved your acoustic guitar riffs in D minor. Let's explore a 10-beat Jhaptal groove combined with your fingerstyle chords.",
            status = JamStatus.ACCEPTED
        ),
        JamRequest(
            id = "jr_2",
            artistId = "m2",
            artistName = "Meera Krishnan",
            artistRole = "Carnatic Vocalist",
            proposedDate = "Next Tuesday, 6:30 PM",
            jamType = "Virtual WebRTC Jam",
            location = "BandConnect Low-Latency Room #804",
            note = "Would love to audition your chord progression for my upcoming single 'Naino Mein'. Can test remote stem alignment first.",
            status = JamStatus.PENDING
        )
    )

    val sampleChats = listOf(
        ChatMessage("c1", "Rohan Deshmukh", "Hey man! Listened to that D minor acoustic lick you posted on the feed.", "10:14 AM", false),
        ChatMessage("c2", "Me", "Thanks Rohan! Really appreciate it. I've been looking for someone who can hold tight polyrhythms over it.", "10:16 AM", true),
        ChatMessage("c3", "Rohan Deshmukh", "I have my studio booked this Saturday at Echo Sound. Sent you an official Jam Request card above!", "10:18 AM", false)
    )
}
