package com.pavankumarpatruni.githubapp.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pavankumarpatruni.githubapp.R
import com.pavankumarpatruni.githubapp.api.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_item_repo_details.view.*

class RepoAdapter(val showPic: Boolean, recyclerView: RecyclerView) : RecyclerView.Adapter<RepoAdapter.MyViewHolder>() {

    private var items: MutableList<Item> = mutableListOf()

    private var loading: Boolean = false

    lateinit var onItemClickListener: OnItemClickListener
    lateinit var onLoadMoreListener: OnLoadMoreListener

    init {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (!loading && totalItemCount - 1 <= lastVisibleItem && lastVisibleItem > items.size - 5) {
                        onLoadMoreListener.onLoadMore()
                        loading = true
                    }
                }
            })
        }
    }

    fun updateItems(items: List<Item>) {
        items.forEach { this.items.add(it) }

        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun setLoaded() {
        loading = false
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item_repo_details, viewGroup, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bindData(items[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(item: Item) {
            itemView.textViewName.text = item.name
            itemView.textViewFullName.text = item.fullName
            itemView.textViewWatchCommitCount.text = "Watcher Count ${item.watchersCount}, Forks Count ${item.forksCount}"

            if (showPic) {
                Picasso.get().load(item.owner.avatarUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(itemView.imageViewProfile)
            } else {
                itemView.imageViewProfile.visibility = View.GONE
            }
        }

        override fun onClick(view: View?) {
            onItemClickListener.onItemClick(items[adapterPosition])
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

}
