package com.ikapurwanti.foodappbinarchallenge.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.core.ViewHolderBinder
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemCheckoutListBinding
import com.ikapurwanti.foodappbinarchallenge.databinding.ItemListCartBinding
import com.ikapurwanti.foodappbinarchallenge.model.Cart
import com.ikapurwanti.foodappbinarchallenge.model.CartMenu
import com.ikapurwanti.foodappbinarchallenge.utils.doneEditing

class CartListAdapter(
    private val cartListener: CartListener? = null
) : RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartMenu>(){
        override fun areItemsTheSame(oldItem: CartMenu, newItem: CartMenu): Boolean {
            return oldItem.cart.id == newItem.cart.id &&
                    oldItem.menu.id == newItem.menu.id
        }
        override fun areContentsTheSame(oldItem: CartMenu, newItem: CartMenu): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) CartViewHolder(
            ItemListCartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CartCheckoutViewHolder(
            ItemCheckoutListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CartMenu>).bind(dataDiffer.currentList[position])
    }

    fun setData(data: List<CartMenu>) {
        dataDiffer.submitList(data)
    }


}

class CartViewHolder(
    private val binding : ItemListCartBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu>{
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding){
            ivMenuImage.load(item.menu.menuImg){
                crossfade(true)
            }
            tvMenuName.text = item.menu.name
            tvMenuPrice.text = "IDR ${(item.cart.itemQuantity * item.menu.price)}"
            tvCalculateOrder.text = item.cart.itemQuantity.toString()

        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.etNoteMenu.setText(item.cart.itemNotes)
        binding.etNoteMenu.doneEditing{
            binding.etNoteMenu.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etNoteMenu.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: CartMenu) {
        with(binding){
            tvMinOrder.setOnClickListener {cartListener?.onMinusTotalItemCartClicked(item.cart)}
            tvPlusOrder.setOnClickListener {cartListener?.onPlusTotalItemCartClicked(item.cart)}
            ivDelete.setOnClickListener {cartListener?.onRemoveCartClicked(item.cart)}
        }
    }

}

class CartCheckoutViewHolder(
    private val binding: ItemCheckoutListBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu>{
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding){
            ivMenuImage.load(item.menu.menuImg){
                crossfade(true)
            }
            tvMenuName.text = item.menu.name
            tvMenuPrice.text = "IDR ${(item.cart.itemQuantity * item.menu.price)}"
            tvTotalCalculateOrder.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.cart.itemQuantity.toString()
                )
        }
    }

    private fun setCartNotes(item: CartMenu){
        binding.tvNoteMenu.text = item.cart.itemNotes
    }

}

interface CartListener{
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}