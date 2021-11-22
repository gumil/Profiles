package dev.gumil.profiles.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gumil.profiles.R
import dev.gumil.profiles.data.GithubRepository
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.ui.githubRepository
import dev.gumil.profiles.ui.githubUser
import dev.gumil.profiles.ui.theme.ProfilesTheme

private const val BORDER_COLOR = 0xFFCCCCCC

@Composable
fun RepositoryItem(
    user: GithubUser,
    repository: GithubRepository,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, Color(BORDER_COLOR)),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .heightIn(164.dp)
    ) {
        RepositoryItemUser(user = user)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = repository.name,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = repository.description ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(16.dp))
        RepositoryStargazersAndLanguage(repository)
    }
}

@Composable
private fun RepositoryItemUser(user: GithubUser) {
    Row {
        ProfileImage(user = user, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = user.login,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun RepositoryStargazersAndLanguage(repository: GithubRepository) {
    Row {
        TextWithLeftContent(repository.stargazers.toString()) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        TextWithLeftContent(repository.language.name) {
            Spacer(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = Color(
                            color = android.graphics.Color.parseColor(repository.language.color)
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun TextWithLeftContent(
    text: String,
    modifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent()
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
private fun RepositoryItemFullWidthPreview() {
    ProfilesTheme {
        Surface(color = MaterialTheme.colors.background) {
            RepositoryItem(
                user = githubUser(),
                repository = githubRepository(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun RepositoryItemSmallPreview() {
    ProfilesTheme {
        Surface(color = MaterialTheme.colors.background) {
            RepositoryItem(
                user = githubUser(),
                repository = githubRepository(),
                modifier = Modifier.width(200.dp)
            )
        }
    }
}
