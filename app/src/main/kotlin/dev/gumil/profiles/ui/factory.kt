package dev.gumil.profiles.ui

import dev.gumil.profiles.data.GithubLanguage
import dev.gumil.profiles.data.GithubRepository
import dev.gumil.profiles.data.GithubUser

fun githubUser() = GithubUser(
    avatarUrl = "https://avatars.githubusercontent.com/u/aang",
    bio = null,
    followers = 1,
    following = 1,
    email = "gumil.dev@gmail.com",
    name = "Miguel Panelo",
    login = "gumil",
    pinnedRepositories = githubRepositories(),
    topRepositories = githubRepositories(),
    starredRepositories = githubRepositories()
)

fun githubRepositories() = (0..2).map { githubRepository() }

fun githubRepository() = GithubRepository(
    name = "Github Profiles",
    description = "Github Profiles is the best",
    stargazers = 227,
    language = GithubLanguage(
        name = "Kotlin",
        color = "#A97BFF"
    )
)
