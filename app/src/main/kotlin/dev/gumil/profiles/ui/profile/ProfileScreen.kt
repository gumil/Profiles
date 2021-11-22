package dev.gumil.profiles.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.gumil.profiles.MainViewModel
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.ui.EmptyScreen
import dev.gumil.profiles.ui.githubUser
import dev.gumil.profiles.ui.theme.ProfilesTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    userFlow: Flow<MainViewModel.UiState>,
    onRefresh: () -> Unit
) {
    val state = userFlow.collectAsState(initial = MainViewModel.UiState())
    val refreshState = rememberSwipeRefreshState(state.value.isRefreshing)

    if (state.value.hasError) {
        EmptyScreen()
        return
    }

    SwipeRefresh(
        state = refreshState,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        state.value.user?.let { user ->
            ProfileScreen(user = user)
        }
    }
}

@Composable
private fun ProfileScreen(user: GithubUser) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        RepositoryUser(user)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = user.email,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        FollowingAndFollowersCount(user)
        Spacer(modifier = Modifier.height(24.dp))
        RepositoryListColumn(
            header = "Pinned",
            user = user,
            repositories = user.pinnedRepositories
        )
        Spacer(modifier = Modifier.height(24.dp))
        RepositoryListRow(
            header = "Top Repositories",
            user = user,
            repositories = user.topRepositories
        )
        Spacer(modifier = Modifier.height(24.dp))
        RepositoryListRow(
            header = "Starred Repositories",
            user = user,
            repositories = user.starredRepositories
        )
    }
}

@Composable
private fun RepositoryUser(user: GithubUser) {
    Row {
        ProfileImage(user = user, modifier = Modifier.size(88.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = user.name ?: user.login,
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = user.login)
        }
    }
}

@Composable
private fun FollowingAndFollowersCount(user: GithubUser) {
    Row {
        PartlyBoldedText(
            bold = user.followers.toString(),
            text = "followers"
        )
        Spacer(modifier = Modifier.width(16.dp))
        PartlyBoldedText(
            bold = user.following.toString(),
            text = "following"
        )
    }
}

@Composable
private fun PartlyBoldedText(
    bold: String,
    text: String
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.SemiBold)
            ) {
                append(bold)
            }
            append(" ")
            append(text)
        }
    )
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfilesTheme {
        Surface(color = MaterialTheme.colors.background) {
            ProfileScreen(
                user = githubUser()
            )
        }
    }
}
