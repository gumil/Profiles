package dev.gumil.profiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.gumil.profiles.ui.profile.ProfileScreen
import dev.gumil.profiles.ui.theme.ProfilesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfilesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        ProfileAppbar()
                        ProfileScreen(userFlow = viewModel.getProfile("gumil"))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileAppbar() {
    TopAppBar(elevation = 0.dp) {
        Text(
            text = "Profile",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun ProfileAppbarLightPreview() {
    ProfilesTheme {
        ProfileAppbar()
    }
}

@Preview
@Composable
private fun ProfileAppbarDarkPreview() {
    ProfilesTheme(darkTheme = true) {
        ProfileAppbar()
    }
}
