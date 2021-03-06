package dev.gumil.profiles.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.ui.githubUser

@Composable
fun ProfileImage(
    user: GithubUser,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = user.avatarUrl
        ),
        contentDescription = null,
        modifier = modifier.clip(CircleShape)
    )
}

@Preview
@Composable
private fun ProfileImageBigPreview() {
    ProfileImage(
        user = githubUser(),
        modifier = Modifier
            .size(88.dp)
    )
}

@Preview
@Composable
private fun ProfileImageSmallPreview() {
    ProfileImage(
        user = githubUser(),
        modifier = Modifier
            .size(32.dp)
    )
}
