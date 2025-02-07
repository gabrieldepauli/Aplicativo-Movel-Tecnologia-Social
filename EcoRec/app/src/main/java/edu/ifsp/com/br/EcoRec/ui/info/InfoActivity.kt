package edu.ifsp.com.br.EcoRec.ui.info

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterMaterialRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository
import edu.ifsp.com.br.EcoRec.databinding.ActivityInfoBinding
import edu.ifsp.com.br.EcoRec.ui.main.MainActivity
import kotlinx.coroutines.launch

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding
    private lateinit var viewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = InfoViewModel(
            RecycleCenterMaterialRepository(this),
            RecycleCenterRepository(this),
            RecycleMaterialRepository(this)
        )

        setupRecyclerView()
        setupSpinner()
        setupBackButton()
    }

    private fun setupRecyclerView() {
        val adapter = RecycleCenterAdapter { centro -> mostrarMateriais(centro.id) }
        binding.recyclerViewCentros.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCentros.adapter = adapter

        lifecycleScope.launch {
            val centros = viewModel.getCentros()
            adapter.submitList(centros)
        }
    }

    private fun setupSpinner() {
        lifecycleScope.launch {
            val materiais = viewModel.getTodosMateriais().toMutableList()
            materiais.add(0, RecycleMaterial(0, "Todos os Materiais"))

            val adapterSpinner = ArrayAdapter(
                this@InfoActivity,
                R.layout.item_spinner,
                materiais.map { it.name }
            )
            adapterSpinner.setDropDownViewResource(R.layout.item_spinner)
            binding.spinnerMateriais.adapter = adapterSpinner

            binding.spinnerMateriais.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val materialSelecionado = materiais[position]
                    if (materialSelecionado.id == 0) {
                        carregarTodosOsCentros()
                    } else {
                        filtrarCentrosPorMaterial(materialSelecionado.id)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    carregarTodosOsCentros()
                }
            }
        }
    }

    private fun setupBackButton() {
        binding.buttonVoltar.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }

    private fun filtrarCentrosPorMaterial(materialId: Int) {
        lifecycleScope.launch {
            val centrosFiltrados = viewModel.getCentrosPorMaterial(materialId)
            (binding.recyclerViewCentros.adapter as RecycleCenterAdapter).submitList(centrosFiltrados)
        }
    }

    private fun mostrarMateriais(centroId: Int) {
        lifecycleScope.launch {
            val materiais = viewModel.getMateriaisDoCentro(centroId)
            exibirDialogoMateriais(materiais)
        }
    }

    private fun carregarTodosOsCentros() {
        lifecycleScope.launch {
            val todosCentros = viewModel.getCentros()
            (binding.recyclerViewCentros.adapter as RecycleCenterAdapter).submitList(todosCentros)
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