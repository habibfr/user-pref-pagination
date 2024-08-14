package com.habibfr.suitmedia_test_mobile.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.User
import com.habibfr.suitmedia_test_mobile.databinding.ItemUserBinding

class UserAdapter() :
    PagingDataAdapter<User, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(itemView.context).load(user.avatar).into(binding.profileImage)
            binding.txtName.text = "${user.firstName} ${user.lastName}"
            binding.txtEmail.text = user.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.itemView.setOnClickListener {
            if (users != null) {
                onItemClickCallback.onItemClicked(users)
            }
        }
        if (users != null) {
            holder.bind(users)
        }

    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(
                    oldItem: User,
                    newItem: User
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: User,
                    newItem: User
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}