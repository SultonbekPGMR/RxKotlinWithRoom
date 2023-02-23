package com.sultonbek1547.rxkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sultonbek1547.rxkotlin.database.AppDatabase
import com.sultonbek1547.rxkotlin.database.User
import com.sultonbek1547.rxkotlin.databinding.RvItemBinding

class MyUserAdapter(val appDatabase: AppDatabase) : ListAdapter<User, MyUserAdapter.Vh>(DiffUtils()) {

    class DiffUtils : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }
    }

    inner class Vh(val itemRvItemBinding: RvItemBinding) : ViewHolder(itemRvItemBinding.root) {
        fun onBind(user: User) {
            itemRvItemBinding.apply {
                tvId.text = user.id.toString()
                tvName.text = user.name
                tvNumber.text = user.number

                itemCard.setOnLongClickListener {
                    appDatabase.myUserDao().deleteUser(user)

                    true
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

}