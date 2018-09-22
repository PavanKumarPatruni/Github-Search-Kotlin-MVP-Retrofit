package com.pavankumarpatruni.githubapp.home

import android.app.Activity
import android.content.Context
import com.pavankumarpatruni.githubapp.GithubApplication
import com.pavankumarpatruni.githubapp.R
import com.pavankumarpatruni.githubapp.api.models.GetRepos
import com.pavankumarpatruni.githubapp.utils.KeyboardUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenterImpl(private var context: Context) : HomePresenter {

    private var homeView: HomeView = context as HomeView

    private var searchText: String = ""
    private var sortBy: String = ""
    private var orderBy: String = ""
    private var pageNum: Int = 0
    private var pageLength: Int = 10
    private var itemsCount: Int = 0
    private var totalCount: Int = 0

    private var isFilterApplied: Boolean = false

    private fun resetData() {
        homeView.clearList()

        pageLength = 10
        pageNum= 1

        totalCount = 0
        itemsCount = 0
    }

    private fun clearFilters() {
        orderBy = ""
        sortBy = ""

        homeView.resetFilters()
    }

    private fun onSearching() {
        homeView.changeMessage(context.getString(R.string.fetching))
    }

    private fun showNoReposFound() {
        if (itemsCount == 0) homeView.changeMessage(context.getString(R.string.no_repo_found))
    }

    private fun manageResponse(response: Response<GetRepos>) {
        totalCount = response.body()!!.totalCount
        itemsCount += response.body()!!.items.size

        showNoReposFound()

        if (itemsCount != 0) {
            homeView.updateList(response.body()!!.items)
        }
    }

    private fun getReposService() {
        GithubApplication.service.getRepos(searchText, sortBy, orderBy, pageLength, pageNum).enqueue(object: Callback<GetRepos> {
            override fun onFailure(call: Call<GetRepos>, t: Throwable) {
                showNoReposFound()
            }

            override fun onResponse(call: Call<GetRepos>, response: Response<GetRepos>) {
                manageResponse(response)
            }

        })
    }

    override fun searchRepos(searchText: String, sortBy: String, orderBy: String) {

        KeyboardUtils.hideKeyboard(context as Activity)

        if (this.searchText != searchText) {
            resetData()
        }

        this.searchText = searchText
        this.sortBy = sortBy
        this.orderBy = orderBy

        if (pageNum == 1) {
            onSearching()
        }

        getReposService()
    }

    override fun onDialogCancel() {
        if (isFilterApplied) {
            getReposService()
        }
    }

    override fun onFilterApply() {
        isFilterApplied = true
        resetData()
    }

    override fun onFilterClear() {
        clearFilters()
        resetData()
        getReposService()
    }

    override fun onLoadMore() {
        if (itemsCount != 0 && totalCount != itemsCount) {
            pageNum++
            getReposService()
        }
    }
}