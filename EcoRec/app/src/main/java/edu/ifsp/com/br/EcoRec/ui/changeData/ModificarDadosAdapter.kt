package edu.ifsp.com.br.EcoRec.ui.changeData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.databinding.ItemModificarLayoutBinding

class ModificarDadosAdapter(private var dataset: List<Any>, private val listener: ItemClickListener) : RecyclerView.Adapter<ModificarDadosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemModificarLayoutBinding = ItemModificarLayoutBinding.bind(view)
    }

    fun update(dados: List<Any>) {
        dataset = dados
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_modificar_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        when (item) {
            is RecycleCenter -> {
                holder.binding.textId.text = item.id.toString()
                holder.binding.textNome.text = "Nome: ${item.name}\nLocalização: ${item.location}"
                holder.binding.imageDelete.setOnClickListener { listener.DeleteCenter(item.id) }
            }
            is RecycleMaterial -> {
                holder.binding.textId.text = item.id.toString()
                holder.binding.textNome.text = "Nome: ${item.name}"
                holder.binding.imageDelete.setOnClickListener { listener.DeleteMaterial(item.id) }
            }
        }
    }

}