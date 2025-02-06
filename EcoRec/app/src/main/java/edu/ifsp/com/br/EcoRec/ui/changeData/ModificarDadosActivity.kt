package edu.ifsp.com.br.EcoRec.ui.changeData

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
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
                Toast.makeText(this, "Erro ao deletar o item!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.updatedItem.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Item editado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao editar o item!", Toast.LENGTH_SHORT).show()
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

        viewModel.toDeleteC.observe(this, Observer { center ->
            center?.let {
                showDeleteConfirm(it.id, false)
            }
        })

        viewModel.toDeleteM.observe(this, Observer { material ->
            material?.let {
                showDeleteConfirm(it.id, true)
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
        viewModel.notifyDeleteMaterial(id)
    }

    override fun DeleteCenter(id: Int) {
        viewModel.notifyDeleteCenter(id)
    }

    override fun UpdateMaterial(material: RecycleMaterial) {
        handleEditSite(material)
    }

    override fun UpdateCenter(center: RecycleCenter) {
        handleEditSite(center)
    }

    private fun handleEditSite(item: Any) {
        val inflater = layoutInflater
        val view: View
        val builder = AlertDialog.Builder(this)

        if (item is RecycleCenter) {
            view = inflater.inflate(R.layout.edit_item_layout, null)
            val nameField = view.findViewById<EditText>(R.id.edittext_name)
            val addressField = view.findViewById<EditText>(R.id.edittext_address)
            addressField.visibility = View.VISIBLE

            nameField.setText(item.name)
            addressField.setText(item.location)

            builder.setTitle("Editar Centro de Reciclagem")
                .setView(view)
                .setPositiveButton("Salvar") { dialog, _ ->
                    val updatedCenter = item.copy(
                        name = nameField.text.toString(),
                        location = addressField.text.toString()
                    )
                    viewModel.updateCenter(updatedCenter.id, updatedCenter.name, updatedCenter.location)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }

        } else if (item is RecycleMaterial) {
            view = inflater.inflate(R.layout.edit_item_layout, null)
            val nameField = view.findViewById<EditText>(R.id.edittext_name)

            nameField.setText(item.name)

            builder.setTitle("Editar Material Reciclável")
                .setView(view)
                .setPositiveButton("Salvar") { dialog, _ ->
                    val updatedMaterial = item.copy(
                        name = nameField.text.toString()
                    )
                    viewModel.updateMaterial(updatedMaterial.id, updatedMaterial.name)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showDeleteConfirm(id: Int, isMaterial: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar Exclusão")
        builder.setMessage("Realmente deseja excluir esse dado?")

        builder.setPositiveButton("Excluir") { dialog, _ ->
            if (isMaterial) {
                viewModel.deleteMaterial(id)
            } else {
                viewModel.deleteCenter(id)
            }
            dialog.dismiss()
        }

        builder.setNeutralButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}