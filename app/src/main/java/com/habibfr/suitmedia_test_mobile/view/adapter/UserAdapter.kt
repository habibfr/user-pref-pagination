package com.habibfr.githubusersapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.DataUser
import com.habibfr.suitmedia_test_mobile.databinding.ItemUserBinding

class UserAdapter() :
    ListAdapter<DataUser, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataUser) {
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
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(users) }
        holder.bind(users)
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: DataUser)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DataUser> =
            object : DiffUtil.ItemCallback<DataUser>() {
                override fun areItemsTheSame(
                    oldItem: DataUser,
                    newItem: DataUser
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: DataUser,
                    newItem: DataUser
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}