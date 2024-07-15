package com.bugrakstudios.livescore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugrakstudios.livescore.data.remote.model.Response
import com.bugrakstudios.livescore.ui.theme.LiveScoreTheme
import com.bugrakstudios.livescore.viewModel.InPlayMatchesViewModel
import com.bugrakstudios.livescore.viewModel.state.MatchesState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiveScoreTheme {
                Column(modifier = Modifier.padding(top = 30.dp, start = 10.dp, end = 10.dp)) {
                    TopAppBar()
                    FetchData()
                }
            }
        }
    }
}


@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon")
        }
        Text(text = "LiveScore", style = MaterialTheme.typography.headlineMedium)

        IconButton(onClick = {}) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_mode_night_24),
                contentDescription = "darkNight Icon"
            )
        }
    }
}

@Composable
fun FetchData(inPlayMatchesViewModel: InPlayMatchesViewModel = viewModel()) {
    Column {
        when (val state = inPlayMatchesViewModel.inplayMatchesState.collectAsState().value) {
            is MatchesState.Empty -> Text(text = "No data available")
            is MatchesState.Loading -> Text(text = "Loading...")
            is MatchesState.Success -> LiveMatches(liveMatches = state.matches)
            is MatchesState.Error -> Text(text = state.message)
        }
    }
}

@Composable
fun LiveMatches(liveMatches: List<Response>) {
    Column(modifier = Modifier.padding(15.dp)) {
        Text(
            text = "Live Matches",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 12.dp)
        )

        if (liveMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Mo Live Matches Currently"
                )
                Text(
                    text = "No Live Matches Currently",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        else {
            LazyRow(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(liveMatches.size){
                        LiveMatchItem(match = liveMatches[it])
                    }
            }
        }
    }
}

@Composable
fun LiveMatchItem(match:Response){
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(300.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
                ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column (modifier = Modifier.padding(10.dp)) {
            Text(
                text = match.league.name,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
                )

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val homeScore = match.goals.home
                val awayScore = match.goals.away
                
                Text(
                    text = match.teams.home.name,
                    style = MaterialTheme.typography.bodyLarge
                    )
                Text(
                    text = "$homeScore:$awayScore",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = match.teams.away.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


