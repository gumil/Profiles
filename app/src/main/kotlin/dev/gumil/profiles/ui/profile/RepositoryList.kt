package dev.gumil.profiles.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gumil.profiles.data.GithubRepository
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.ui.githubRepositories
import dev.gumil.profiles.ui.githubUser
import dev.gumil.profiles.ui.theme.ProfilesTheme

@Composable
fun RepositoryListColumn(
    header: String,
    user: GithubUser,
    repositories: List<GithubRepository>,
    modifier: Modifier = Modifier
) {
    RepositoryList(
        header = header,
        modifier = modifier
    ) {
        repositories.forEachIndexed { index, repository ->
            RepositoryItem(
                user = user,
                repository = repository,
                modifier = Modifier.fillMaxWidth()
            )
            if (index < repositories.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RepositoryListRow(
    header: String,
    user: GithubUser,
    repositories: List<GithubRepository>,
    modifier: Modifier = Modifier
) {
    RepositoryList(
        header = header,
        modifier = modifier.fillMaxWidth()
    ) {
        LazyRow {
            repositories.forEachIndexed { index, repository ->
                item {
                    RepositoryItem(
                        user = user,
                        repository = repository,
                        modifier = Modifier.width(200.dp)
                    )
                    if (index < repositories.size - 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun RepositoryList(
    header: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier) {
        Text(
            text = header,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(16.dp))

        content()
    }
}

@Preview
@Composable
fun RepositoryListColumnPreview() {
    ProfilesTheme {
        Surface(color = MaterialTheme.colors.background) {
            RepositoryListColumn(
                header = "Pinned",
                user = githubUser(),
                repositories = githubRepositories()
            )
        }
    }
}

@Preview
@Composable
fun RepositoryListRowPreview() {
    ProfilesTheme {
        Surface(color = MaterialTheme.colors.background) {
            RepositoryListRow(
                header = "Top repositories",
                user = githubUser(),
                repositories = githubRepositories()
            )
        }
    }
}
