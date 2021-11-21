package dev.gumil.profiles.data

data class GithubUser(
    val name: String?,
    val login: String,
    val email: String,
    val avatarUrl: String,
    val bio: String?,
    val followers: Int,
    val following: Int,
    val pinnedRepositories: List<GithubRepository>,
    val topRepositories: List<GithubRepository>,
    val starredRepositories: List<GithubRepository>
)

data class GithubRepository(
    val name: String,
    val description: String?,
    val stargazers: Int,
    val language: GithubLanguage
)

data class GithubLanguage(
    val name: String,
    val color: String
)

// Suppressing this warning because the generated repository
// classes are different from each other
@Suppress("LongMethod")
internal fun GetProfileQuery.User.toGithubUser(): GithubUser {
    return GithubUser(
        name = name,
        login = login,
        email = email,
        avatarUrl = avatarUrl.toString(),
        bio = bio,
        followers = followers.totalCount,
        following = following.totalCount,
        pinnedRepositories = pinnedItems
            .edges
            ?.mapNotNull { it?.node?.asRepository }
            ?.mapNotNull { repository ->
                if (repository.primaryLanguage?.color == null) {
                    return@mapNotNull null
                }

                GithubRepository(
                    name = repository.name,
                    description = repository.description,
                    stargazers = repository.stargazerCount,
                    language = GithubLanguage(
                        repository.primaryLanguage.name,
                        repository.primaryLanguage.color
                    )
                )
            } ?: emptyList(),
        topRepositories = topRepositories
            .edges
            ?.mapNotNull { it?.node }
            ?.mapNotNull { repository ->
                if (repository.primaryLanguage?.color == null) {
                    return@mapNotNull null
                }

                GithubRepository(
                    name = repository.name,
                    description = repository.description,
                    stargazers = repository.stargazerCount,
                    language = GithubLanguage(
                        repository.primaryLanguage.name,
                        repository.primaryLanguage.color
                    )
                )
            } ?: emptyList(),
        starredRepositories = starredRepositories
            .edges
            ?.mapNotNull { it?.node }
            ?.mapNotNull { repository ->
                if (repository.primaryLanguage?.color == null) {
                    return@mapNotNull null
                }

                GithubRepository(
                    name = repository.name,
                    description = repository.description,
                    stargazers = repository.stargazerCount,
                    language = GithubLanguage(
                        repository.primaryLanguage.name,
                        repository.primaryLanguage.color
                    )
                )
            } ?: emptyList()
    )
}
