package com.ikapurwanti.foodappbinarchallenge.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ikapurwanti.foodappbinarchallenge.core.ViewHolderBinder
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemCategoriesBinding
import com.ikapurwanti.foodappbinarchallenge.model.Categories

class CategoriesListAdapter(
) : RecyclerView.Adapter<CategoriesItemListViewHolder>(){

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Categories>(){
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.image == newItem.image

        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesItemListViewHolder {
        return CategoriesItemListViewHolder(
            binding = ItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: CategoriesItemListViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data : List<Categories>){
        dataDiffer.submitList(data)
        notifyItemRangeChanged(0,data.size)
    }

}

class CategoriesItemListViewHolder(
    private val binding: ItemCategoriesBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Categories> {
    override fun bind(categories: Categories) {
        with(binding){
            ivCategories.load(categories.image)
            tvNameCategories.text = categories.name
        }
    }

}
