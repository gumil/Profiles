package dev.gumil.profiles.ui

import dev.gumil.profiles.data.GithubLanguage
import dev.gumil.profiles.data.GithubRepository
import dev.gumil.profiles.data.GithubUser

fun githubUser() = GithubUser(
    avatarUrl = "https://avatars.githubusercontent.com/u/aang",
    bio = null,
    followers = 1,
    following = 1,
    email = "",
    name = null,
    login = "gumil",
    pinnedRepositories = emptyList(),
    topRepositories = emptyList(),
    starredRepositories = emptyList()
)

fun githubRepository() = GithubRepository(
    name = "Github Profiles",
    description = "Github Profiles is the best",
    stargazers = 227,
    language = GithubLanguage(
        name = "Kotlin",
        color = "#A97BFF"
    )
)
