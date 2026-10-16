package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.mutableStateOf
import com.example.components.AdMobBanner
import com.example.components.UserGuideDialog
import com.example.data.ChatMessage
import com.example.data.FeedIdea
import com.example.data.JamRequest
import com.example.data.MockDataProvider
import com.example.data.Musician
import com.example.screens.ArchitectureScreen
import com.example.screens.FinderScreen
import com.example.screens.IdeaFeedScreen
import com.example.screens.JamCenterScreen
import com.example.screens.ProfileScreen
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCardBg
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WarmGold

sealed class Screen(val title: String, val icon: ImageVector, val tag: String) {
    data object Finder : Screen("Radar", Icons.Default.Radar, "nav_finder")
    data object Feed : Screen("Raw Feed", Icons.Default.GraphicEq, "nav_feed")
    data object JamCenter : Screen("Jam Center", Icons.Default.Handshake, "nav_jam_center")
    data object Profile : Screen("Portfolio", Icons.Default.Person, "nav_profile")
    data object Architecture : Screen("System Specs", Icons.Default.Memory, "nav_architecture")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showGuideDialog by remember { mutableStateOf(false) }

    val musicians = remember { mutableStateListOf<Musician>().apply { addAll(MockDataProvider.sampleMusicians) } }
    val jamRequests = remember { mutableStateListOf<JamRequest>().apply { addAll(MockDataProvider.sampleJamRequests) } }
    val feedIdeas = remember { mutableStateListOf<FeedIdea>().apply { addAll(MockDataProvider.sampleIdeas) } }
    val chats = remember { mutableStateListOf<ChatMessage>().apply { addAll(MockDataProvider.sampleChats) } }

    val screens = listOf(
        Screen.Finder,
        Screen.Feed,
        Screen.JamCenter,
        Screen.Profile,
        Screen.Architecture
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            modifier = Modifier.size(34.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = ElectricPurple.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricPurple)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.GraphicEq,
                                    contentDescription = "BandConnect Logo",
                                    tint = WarmGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "BandConnect",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = " / JamLocal",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = CyanGlow
                                )
                            }
                            Text(
                                text = "Musician & Producer Collaboration Network",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // User Guide Button
                        Surface(
                            color = WarmGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold),
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .clickable { showGuideDialog = true }
                                .testTag("top_bar_guide_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = "User Guide",
                                    tint = WarmGold,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Guide",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = WarmGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Live status indicator
                        Surface(
                            color = StudioCardBg,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(NeonGreen)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Studio Online",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = NeonGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(StudioCardBg)
            ) {
                // AdMob Banner Ads show in bottom
                AdMobBanner(
                    modifier = Modifier.fillMaxWidth()
                )

                NavigationBar(
                    containerColor = StudioCardBg,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = StudioBorder,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .testTag("bottom_nav_bar")
                ) {
                    screens.forEachIndexed { index, screen ->
                        val isSelected = selectedTabIndex == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTabIndex = index },
                            icon = {
                                if (screen == Screen.JamCenter && jamRequests.isNotEmpty()) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = WarmGold,
                                                contentColor = Color.Black
                                            ) {
                                                Text("${jamRequests.size}", fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = screen.title
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = WarmGold,
                                indicatorColor = WarmGold,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            ),
                            modifier = Modifier.testTag(screen.tag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTabIndex) {
                0 -> FinderScreen(
                    musicians = musicians,
                    onSendJamRequest = { newReq ->
                        jamRequests.add(0, newReq)
                        selectedTabIndex = 2 // Switch to Jam Center
                    }
                )
                1 -> IdeaFeedScreen(
                    initialIdeas = feedIdeas
                )
                2 -> JamCenterScreen(
                    jamRequests = jamRequests,
                    initialChats = chats
                )
                3 -> ProfileScreen()
                4 -> ArchitectureScreen()
            }
        }
    }

    if (showGuideDialog) {
        UserGuideDialog(
            onDismiss = { showGuideDialog = false }
        )
    }
}
