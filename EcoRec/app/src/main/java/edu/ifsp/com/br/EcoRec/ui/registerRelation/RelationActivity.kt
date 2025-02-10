package edu.ifsp.com.br.EcoRec.ui.registerRelation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.databinding.ActivityRelationBinding
import edu.ifsp.com.br.EcoRec.ui.info.RecycleCenterAdapter
import edu.ifsp.com.br.EcoRec.ui.menuADM.RegisterActivity
import kotlinx.coroutines.launch

class RelationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelationBinding
    private lateinit var viewModel: RelationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RelationViewModel::class.java)
        viewModel.loadCenters()

        setupListeners()
        setupRecyclerView()
    }

    private fun setupListeners() {
        binding.buttonVoltar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupRecyclerView() {
        val adapter = RecycleCenterAdapter { centro -> mostrarMateriais(centro) }
        binding.recyclerViewCentros.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCentros.adapter = adapter

        viewModel.centers.observe(this) { centers ->
            adapter.submitList(centers)
        }
    }

    private fun mostrarMateriais(centro: RecycleCenter) {
        viewModel.getAllMaterials().observe(this) { allMaterials ->

            viewModel.getMaterialsForCenter(centro.id).observe(this) { relatedMaterials ->

                val materialNames = allMaterials.map { it.name }.toTypedArray()

                val selectedItems = BooleanArray(allMaterials.size) { material ->
                    relatedMaterials.any { it.id == allMaterials[material].id }
                }

                AlertDialog.Builder(this)
                    .setTitle("Selecione os Materiais para o Centro: ${centro.name}")
                    .setMultiChoiceItems(materialNames, selectedItems) { _, which, isChecked ->
                        selectedItems[which] = isChecked
                    }
                    .setPositiveButton("Confirmar") { _, _ ->
                        val selectedMaterials = allMaterials.filterIndexed { index, _ -> selectedItems[index] }

                        lifecycleScope.launch {
                            for (material in selectedMaterials) {
                                if (!relatedMaterials.contains(material)) {
                                    viewModel.addRelation(centro.id, material.id)
                                }
                            }

                            for (material in relatedMaterials) {
                                if (!selectedMaterials.contains(material)) {
                                    viewModel.removeRelation(centro.id, material.id)
                                }
                            }

                            Toast.makeText(this@RelationActivity, "Selecionado: ${selectedMaterials.joinToString { it.name }}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }
    }
}