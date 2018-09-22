package com.pavankumarpatruni.githubapp.api

class Constants {

    companion object {

        const val HOST_URL: String = "https://api.github.com"

        const val GET_REPOS: String = "/search/repositories"

        const val GET_CONTRIBUTORS: String = "/repos/{ownerName}/{repoName}/contributors"

        const val GET_REPOS_BY_NAME: String = "/users/{ownerName}/repos"

        const val REPO_DETAILS: String = "REPO_DETAILS"

        const val OWNER_DETAILS: String = "OWNER_DETAILS"

        const val REPO_URL: String = "REPO_URL"

        const val REPO_NAME: String = "REPO_NAME"

    }

}