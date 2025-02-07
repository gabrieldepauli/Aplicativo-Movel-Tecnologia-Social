package edu.ifsp.com.br.EcoRec.ui.registerRelation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.databinding.ActivityRegisterMaterialBinding
import edu.ifsp.com.br.EcoRec.databinding.ActivityRelationBinding
import edu.ifsp.com.br.EcoRec.ui.menuADM.RegisterActivity
import edu.ifsp.com.br.EcoRec.ui.registerMaterial.RegisterMaterialViewModel

class RelationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelationBinding
    private lateinit var viewModel: RelationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RelationViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.insertResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Relação cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                binding.textIdCenter.text?.clear()
                binding.textIdMaterial.text?.clear()
            } else {
                Toast.makeText(this, "Erro ao relacionar os itens!", Toast.LENGTH_SHORT).show()
                binding.textIdCenter.text?.clear()
                binding.textIdMaterial.text?.clear()
            }
        })

        viewModel.findCenter.observe(this, Observer { success ->
            if (!success) {
                Toast.makeText(this, "ID do centro não encontrado!", Toast.LENGTH_SHORT).show()
                binding.textIdCenter.text?.clear()
            }
        })

        viewModel.findMaterial.observe(this, Observer { success ->
            if (!success) {
                Toast.makeText(this, "ID do material não encontrado!", Toast.LENGTH_SHORT).show()
                binding.textIdMaterial.text?.clear()
            }
        })
    }


    private fun setupListeners(){
        binding.buttonRelacionar.setOnClickListener {
            relacionar()
        }

        binding.buttonVoltar.setOnClickListener {
            val mIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }

    private fun relacionar() {
        val idCenterText = binding.textIdCenter.text.toString()
        val idMaterialText = binding.textIdMaterial.text.toString()

        if (idCenterText.isEmpty() || idMaterialText.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val idCenter = idCenterText.toInt()
        val idMaterial = idMaterialText.toInt()

        viewModel.insertRelation(idCenter, idMaterial)
    }

}