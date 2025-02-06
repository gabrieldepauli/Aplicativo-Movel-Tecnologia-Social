package edu.ifsp.com.br.EcoRec.ui.changeData

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.databinding.ActivityModificarDadosBinding
import edu.ifsp.com.br.EcoRec.databinding.ActivityRegisterMaterialBinding
import edu.ifsp.com.br.EcoRec.ui.menuADM.RegisterActivity
import edu.ifsp.com.br.EcoRec.ui.registerMaterial.RegisterMaterialViewModel

class ModificarDadosActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityModificarDadosBinding
    private lateinit var viewModel: ModificarDadosViewModel
    private lateinit var adapter: ModificarDadosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarDadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ModificarDadosViewModel::class.java)

        binding.buttonVoltar.setOnClickListener {
            val mIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mIntent)
            finish()
        }

        setupRecyclerView()
        setupObservers()
        configSpinner()
    }

    private fun setupRecyclerView() {
        adapter = ModificarDadosAdapter(mutableListOf(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        viewModel.deletedItem.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Item deletado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao deltar o item!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.centers.observe(this, Observer {
            if (binding.spinner.selectedItemPosition == 0) {
                adapter.update(it)
            }
        })

        viewModel.materials.observe(this, Observer {
            if (binding.spinner.selectedItemPosition == 1) {
                adapter.update(it)
            }
        })
    }

    private fun configSpinner() {
        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.setSelection(0)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Toast.makeText(this@ModificarDadosActivity, "Centros selecionado", Toast.LENGTH_SHORT).show()
                        viewModel.loadCenters()
                    }
                    1 -> {
                        Toast.makeText(this@ModificarDadosActivity, "Materiais selecionado", Toast.LENGTH_SHORT).show()
                        viewModel.loadMaterials()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    override fun DeleteMaterial(id: Int) {
        viewModel.deleteMaterial(id)
    }

    override fun DeleteCenter(id: Int) {
        viewModel.deleteCenter(id)
    }
}