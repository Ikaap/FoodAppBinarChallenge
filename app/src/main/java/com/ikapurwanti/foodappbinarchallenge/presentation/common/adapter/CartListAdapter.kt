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
import com.ikapurwanti.foodappbinarchallenge.utils.doneEditing
import com.ikapurwanti.foodappbinarchallenge.utils.toCurrencyFormat

class CartListAdapter(
    private val cartListener: CartListener? = null
) : RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>(){
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
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
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }

    fun setData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

}

class CartViewHolder(
    private val binding : ItemListCartBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart>{
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding){
            ivMenuImage.load(item.menuImgUrl){
                crossfade(true)
            }
            tvMenuName.text = item.menuName
            tvMenuPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
            tvCalculateOrder.text = item.itemQuantity.toString()

        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNoteMenu.setText(item.itemNotes)
        binding.etNoteMenu.doneEditing{
            binding.etNoteMenu.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNoteMenu.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding){
            tvMinOrder.setOnClickListener {cartListener?.onMinusTotalItemCartClicked(item)}
            tvPlusOrder.setOnClickListener {cartListener?.onPlusTotalItemCartClicked(item)}
            ivDelete.setOnClickListener {cartListener?.onRemoveCartClicked(item)}
        }
    }

}

class CartCheckoutViewHolder(
    private val binding: ItemCheckoutListBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart>{
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding){
            ivMenuImage.load(item.menuImgUrl){
                crossfade(true)
            }
            tvMenuName.text = item.menuName
            tvMenuPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
            tvTotalCalculateOrder.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.itemQuantity.toString()
                )
        }
    }

    private fun setCartNotes(item: Cart){
        binding.tvNoteMenu.text = item.itemNotes
    }

}

interface CartListener{
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}