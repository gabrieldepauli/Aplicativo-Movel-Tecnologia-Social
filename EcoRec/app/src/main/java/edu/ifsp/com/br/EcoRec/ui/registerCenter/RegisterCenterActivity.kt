package edu.ifsp.com.br.EcoRec.ui.registerCenter

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
import edu.ifsp.com.br.EcoRec.databinding.RegisterActivityBinding
import edu.ifsp.com.br.EcoRec.ui.menuADM.RegisterActivity

class RegisterCenterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterCenterBinding
    private lateinit var viewModel: RegisterCenterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterCenterViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.insertResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Centro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                binding.textNome.text?.clear()
                binding.textEndereco.text?.clear()
            } else {
                Toast.makeText(this, "Erro ao cadastrar centro!", Toast.LENGTH_SHORT).show()
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
        var enderecoCenter = binding.textEndereco.text.toString()

        if (nomeCenter.isEmpty() || enderecoCenter.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.insertCenter(nomeCenter, enderecoCenter)
    }

}