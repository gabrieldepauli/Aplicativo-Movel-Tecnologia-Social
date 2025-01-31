package edu.ifsp.com.br.EcoRec.ui.registerMaterial

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
import edu.ifsp.com.br.EcoRec.databinding.ActivityRegisterCenterBinding
import edu.ifsp.com.br.EcoRec.databinding.ActivityRegisterMaterialBinding
import edu.ifsp.com.br.EcoRec.ui.menuADM.RegisterActivity
import edu.ifsp.com.br.EcoRec.ui.registerCenter.RegisterCenterViewModel

class RegisterMaterialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterMaterialBinding
    private lateinit var viewModel: RegisterMaterialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterMaterialViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.insertResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Material cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                binding.textNome.text?.clear()
            } else {
                Toast.makeText(this, "Erro ao cadastrar o material!", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setupListeners(){
        binding.buttonRegister.setOnClickListener {
            registrar()
        }

        binding.buttonVoltar.setOnClickListener {
            val mIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }

    private fun registrar(){
        var nomeCenter = binding.textNome.text.toString()

        if (nomeCenter.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.insertMaterial(nomeCenter)
    }
}