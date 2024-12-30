import android.widget.TextView

class CryptoAdapter(
    private val cryptoList: List<CryptoItem>,
    private val onCryptoClick: (CryptoItem) -> Unit // Lambda for handling clicks
) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.crypto_name)
        val price : TextView = view.findViewById(R.id.cryptoprice)
        val change:TextView=view.findViewById(R.id.change)
        val image: ImageView = view.findViewById(R.id.cryptoimage)

        fun bind(cryptoItem: CryptoItem) {
            name.text = cryptoItem.name
            price.text = cryptoItem.price
            change.text=cryptoItem.change
            cryptoItem.image?.let { image.setImageResource(cryptoItem.image) }

            itemView.setOnClickListener {
                onCryptoClick(cryptoItem) // Trigger click event with crypto item
            }
        }
    }

    override fun onCrea teViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_item_list, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoList[position])
    }

    override fun getItemCount(): Int = cryptoList.size
}
