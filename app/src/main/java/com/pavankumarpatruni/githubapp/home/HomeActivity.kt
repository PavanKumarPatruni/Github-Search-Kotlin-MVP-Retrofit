package com.pavankumarpatruni.githubapp.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.pavankumarpatruni.githubapp.R
import com.pavankumarpatruni.githubapp.api.Constants
import com.pavankumarpatruni.githubapp.api.models.Item
import com.pavankumarpatruni.githubapp.repo.RepoActivity
import com.pavankumarpatruni.githubapp.utils.Utils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_filter.view.*

class HomeActivity : AppCompatActivity(), HomeView, View.OnClickListener, RepoAdapter.OnItemClickListener, RepoAdapter.OnLoadMoreListener {

    private lateinit var homePresenterImpl: HomePresenterImpl
    private lateinit var repoAdapter: RepoAdapter

    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheetView: View

    private var orderBy: String = ""
    private var sortBy: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imageViewSearch.setOnClickListener(this)
        imageViewFilters.setOnClickListener(this)

        homePresenterImpl = HomePresenterImpl(this)

        initRecyclerView()
        initBottomSheet()

        editTextSearch.setText("krishcdbry")
        imageViewSearch.performClick()
    }

    private fun initRecyclerView() {
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)

        repoAdapter = RepoAdapter(true, recyclerView)
        repoAdapter.onItemClickListener = this
        repoAdapter.onLoadMoreListener = this

        recyclerView.adapter = repoAdapter
    }

    private fun initBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.layout_bottom_sheet_filter, null)

        bottomSheetView.textViewStars.setOnClickListener(this)
        bottomSheetView.textViewForks.setOnClickListener(this)
        bottomSheetView.textViewUpdated.setOnClickListener(this)
        bottomSheetView.textViewAscending.setOnClickListener(this)
        bottomSheetView.textViewDescending.setOnClickListener(this)
        bottomSheetView.textViewApply.setOnClickListener(this)
        bottomSheetView.textViewClear.setOnClickListener(this)

        dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)
        dialog.setOnCancelListener {
            homePresenterImpl.onDialogCancel()
        }
    }

    private fun clearSortFilters() {
        bottomSheetView.textViewStars.isSelected = false
        bottomSheetView.textViewForks.isSelected = false
        bottomSheetView.textViewUpdated.isSelected = false
    }

    private fun clearOrderByFilters() {
        bottomSheetView.textViewAscending.isSelected = false
        bottomSheetView.textViewDescending.isSelected = false
    }

    private fun manageSortFilters() {
        clearSortFilters()

        when (sortBy) {
            Utils.SortBy.stars.name -> bottomSheetView.textViewStars.isSelected = true
            Utils.SortBy.forks.name -> bottomSheetView.textViewForks.isSelected = true
            Utils.SortBy.updated.name -> bottomSheetView.textViewUpdated.isSelected = true
        }
    }

    private fun manageOrderByFilters() {
        clearOrderByFilters()

        when (orderBy) {
            Utils.OrderBy.asc.name -> bottomSheetView.textViewAscending.isSelected = true
            Utils.OrderBy.desc.name -> bottomSheetView.textViewDescending.isSelected = true
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewSearch -> {
                homePresenterImpl.searchRepos(editTextSearch.text.toString(), sortBy, orderBy)
            }
            R.id.imageViewFilters -> {
                dialog.show()
            }
            R.id.textViewStars -> {
                sortBy = Utils.SortBy.stars.name
                manageSortFilters()
            }
            R.id.textViewForks -> {
                sortBy = Utils.SortBy.forks.name
                manageSortFilters()
            }
            R.id.textViewUpdated -> {
                sortBy = Utils.SortBy.updated.name
                manageSortFilters()
            }
            R.id.textViewAscending -> {
                orderBy = Utils.OrderBy.asc.name
                manageOrderByFilters()
            }
            R.id.textViewDescending -> {
                orderBy = Utils.OrderBy.desc.name
                manageOrderByFilters()
            }
            R.id.textViewApply -> {
                dialog.dismiss()
                homePresenterImpl.onFilterApply()
                imageViewSearch.performClick()
            }
            R.id.textViewClear -> {
                dialog.dismiss()
                homePresenterImpl.onFilterClear()
            }
        }
    }

    override fun onLoadMore() {
        homePresenterImpl.onLoadMore()
    }

    override fun onItemClick(item: Item) {
        val intent = Intent(this, RepoActivity::class.java)
        intent.putExtra(Constants.REPO_DETAILS, item)
        startActivity(intent)
    }

    override fun changeMessage(message: String) {
        textViewMessage.text = message
        showMessage()
    }

    override fun showMessage() {
        hideList()
        textViewMessage.visibility = View.VISIBLE
    }

    override fun hideMessage() {
        showList()
        textViewMessage.visibility = View.GONE
    }

    override fun updateList(items: List<Item>) {
        hideMessage()
        repoAdapter.setLoaded()
        repoAdapter.updateItems(items)
    }

    override fun clearList() {
        repoAdapter.clearItems()
    }

    override fun showList() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideList() {
        recyclerView.visibility = View.GONE
    }

    override fun resetFilters() {
        clearOrderByFilters()
        clearSortFilters()
    }

}
