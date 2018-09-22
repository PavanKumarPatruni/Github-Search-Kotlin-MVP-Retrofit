package com.pavankumarpatruni.githubapp.home

import com.pavankumarpatruni.githubapp.api.models.Item

interface HomeView {

    fun changeMessage(message: String)

    fun showMessage()

    fun hideMessage()

    fun updateList(items: List<Item>)

    fun clearList()

    fun showList()

    fun hideList()

    fun resetFilters()

}