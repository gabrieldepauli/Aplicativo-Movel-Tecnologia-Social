package edu.ifsp.com.br.EcoRec.ui.info

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterMaterialRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository
import edu.ifsp.com.br.EcoRec.databinding.ActivityInfoBinding
import kotlinx.coroutines.launch

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding
    private lateinit var viewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = InfoViewModel(RecycleCenterMaterialRepository(this), RecycleCenterRepository(this), RecycleMaterialRepository(this))

        val adapter = RecycleCenterAdapter { centro ->
            mostrarMateriais(centro.id)
        }

        binding.recyclerViewCentros.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCentros.adapter = adapter

        // Observa os dados dos centros de reciclagem
        lifecycleScope.launch {
            val centros = viewModel.getCentros()
            adapter.submitList(centros)
        }
    }

    private fun mostrarMateriais(centroId: Int) {
        lifecycleScope.launch {
            val materiais = viewModel.getMateriaisDoCentro(centroId)
            exibirDialogoMateriais(materiais)
            }
        }
    private fun exibirDialogoMateriais(materiais: List<RecycleMaterial>) {
        val nomes = materiais.joinToString("\n") { it.name }
        AlertDialog.Builder(this)
            .setTitle("Materiais Recicláveis")
            .setMessage(nomes.ifEmpty { "Nenhum material encontrado." })
            .setPositiveButton("OK", null)
            .show()
    }
}