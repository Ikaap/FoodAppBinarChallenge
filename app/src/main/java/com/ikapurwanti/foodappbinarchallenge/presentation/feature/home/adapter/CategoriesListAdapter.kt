package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ikapurwanti.foodappbinarchallenge.core.ViewHolderBinder
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemCategoriesBinding
import com.ikapurwanti.foodappbinarchallenge.model.Category

class CategoriesListAdapter(
    private val itemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesItemListViewHolder>(){

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.imageUrl == newItem.imageUrl

        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesItemListViewHolder {
        val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesItemListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: CategoriesItemListViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data : List<Category>){
        dataDiffer.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0,dataDiffer.currentList.size)
    }

}

class CategoriesItemListViewHolder(
    private val binding: ItemCategoriesBinding,
    val itemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        with(item){
            binding.ivCategories.load(item.imageUrl)
            binding.tvNameCategories.text = item.name
            Log.d("adapter-list", item.toString())
            itemView.setOnClickListener{
                itemClick(this)
            }
        }
    }

}
