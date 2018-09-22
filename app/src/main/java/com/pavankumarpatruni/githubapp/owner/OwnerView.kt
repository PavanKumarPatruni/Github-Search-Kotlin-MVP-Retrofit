package com.pavankumarpatruni.githubapp.owner

import com.pavankumarpatruni.githubapp.api.models.Item

interface OwnerView {

    fun attachRepos(items: List<Item>)

}