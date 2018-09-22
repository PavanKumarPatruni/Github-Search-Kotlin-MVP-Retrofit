package com.pavankumarpatruni.githubapp.repo

interface RepoPresenter {

    fun getContributors(ownerName: String, repoName: String)

}