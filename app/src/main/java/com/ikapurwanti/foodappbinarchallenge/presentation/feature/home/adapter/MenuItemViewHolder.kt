package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ikapurwanti.foodappbinarchallenge.core.ViewHolderBinder
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemGridMenuBinding
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemLinearMenuBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.toCurrencyFormat

class LinearMenuItemViewHolder(
    private val binding: ItemLinearMenuBinding,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        with(binding) {
            ivMenuImage.load(item.imageUrl)
            tvMenuName.text = item.name
            tvMenuPrice.text = item.price.toCurrencyFormat()
            tvMenuRating.text = item.rating.toString()
        }
        binding.root.setOnClickListener {
            onClickListener(item)
        }
    }
}

class GridMenuItemViewHolder(
    private val binding: ItemGridMenuBinding,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        with(binding) {
            ivMenuImage.load(item.imageUrl)
            tvMenuName.text = item.name
            tvMenuPrice.text = item.price.toCurrencyFormat()
            tvMenuRating.text = item.rating.toString()
        }
        binding.root.setOnClickListener {
            onClickListener(item)
        }
    }
}
