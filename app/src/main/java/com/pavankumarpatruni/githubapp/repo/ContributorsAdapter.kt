package com.pavankumarpatruni.githubapp.repo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pavankumarpatruni.githubapp.R
import com.pavankumarpatruni.githubapp.api.models.Owner
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_item_contributor.view.*

class ContributorsAdapter : RecyclerView.Adapter<ContributorsAdapter.MyViewHolder>() {

    private var items: MutableList<Owner> = mutableListOf()
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_contributor, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    fun updateList(list: List<Owner>) {
        this.items = list as MutableList<Owner>
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(owner: Owner) {
            itemView.textViewName.text = owner.login

            Picasso.get().load(owner.avatarUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(itemView.imageViewProfile)
        }

        override fun onClick(view: View?) {
            onItemClickListener.onItemClick(items[adapterPosition])
        }
    }

    interface OnItemClickListener {
        fun onItemClick(owner: Owner)
    }

}
