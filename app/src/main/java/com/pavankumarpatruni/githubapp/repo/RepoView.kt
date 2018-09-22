package com.pavankumarpatruni.githubapp.repo

import com.pavankumarpatruni.githubapp.api.models.Owner

interface RepoView {

    fun attachContributors(list: List<Owner>)

    fun changeMessage(message: String)

}