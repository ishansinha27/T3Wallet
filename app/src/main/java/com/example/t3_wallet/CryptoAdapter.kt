package com.example.t3_wallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CryptoAdapter(
    private var cryptoList: MutableList<CryptoItem>,
    private val onCryptoClick: (CryptoItem) -> Unit
) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.cryptoname)
        val price: TextView = view.findViewById(R.id.cryptoprice)
        val change: TextView = view.findViewById(R.id.cryptoChange)
        val image: ImageView = view.findViewById(R.id.cryptoimage)

        fun bind(cryptoItem: CryptoItem) {
            name.text = cryptoItem.cryptoName
            price.text = cryptoItem.cryptoPrice
            change.text = cryptoItem.cryptoChange
            image.setImageResource(cryptoItem.cryptoImage)

            itemView.setOnClickListener {
                onCryptoClick(cryptoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_item_list, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoList[position])
    }

    override fun getItemCount(): Int = cryptoList.size

    // Method to update the list and notify changes
    fun updateData(newCryptoList: List<CryptoItem>) {
        cryptoList.clear()
        cryptoList.addAll(newCryptoList)
        notifyDataSetChanged()
    }
}
