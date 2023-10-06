package com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ikapurwanti.foodappbinarchallenge.core.ViewHolderBinder
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemGridMenuBinding
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemLinearMenuBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu

class LinearMenuItemViewHolder(
    private val binding : ItemLinearMenuBinding,
    private val onClickListener : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(menu: Menu) {
        with(binding) {
            ivMenuImage.load(menu.menuImg)
            tvMenuName.text = menu.name
            tvMenuPrice.text = "IDR ${menu.price}"
            tvMenuRating.text = menu.rating.toString()
        }
        binding.root.setOnClickListener{
            onClickListener(menu)
        }
    }
}

class GridMenuItemViewHolder(
    private val binding : ItemGridMenuBinding,
    private val onClickListener : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(menu: Menu) {
        with(binding) {
            ivMenuImage.load(menu.menuImg)
            tvMenuName.text = menu.name
            tvMenuPrice.text = "IDR ${menu.price}"
            tvMenuRating.text = menu.rating.toString()
        }
        binding.root.setOnClickListener{
            onClickListener(menu)
        }
    }
}