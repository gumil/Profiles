package dev.gumil.profiles.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gumil.profiles.ui.theme.ProfilesTheme

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "\uD83D\uDEAB",
            fontSize = 72.sp
        )
        Text(
            text = "There was a problem loading your profile",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )
    }
}

@Preview
@Composable
private fun EmptyScreenPreview() {
    ProfilesTheme {
        Surface(color = MaterialTheme.colors.background) {
            EmptyScreen()
        }
    }
}
