package edu.ifsp.com.br.EcoRec.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter

class RecycleCenterAdapter(private val onClick: (RecycleCenter) -> Unit) :
    ListAdapter<RecycleCenter, RecycleCenterAdapter.ViewHolder>(CentroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_center, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val centro = getItem(position)
        holder.bind(centro, onClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(centro: RecycleCenter, onClick: (RecycleCenter) -> Unit) {
            itemView.findViewById<TextView>(R.id.textNomeCentro).apply {
                text = centro.name
                setTextColor(itemView.context.getColor(R.color.black))
            }
            itemView.findViewById<TextView>(R.id.textEnderecoCentro).apply {
                text = centro.location
                setTextColor(itemView.context.getColor(R.color.black))
            }
            itemView.setOnClickListener { onClick(centro) }
        }
    }
}

class CentroDiffCallback : DiffUtil.ItemCallback<RecycleCenter>() {
    override fun areItemsTheSame(oldItem: RecycleCenter, newItem: RecycleCenter): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecycleCenter, newItem: RecycleCenter): Boolean =
        oldItem == newItem
}
