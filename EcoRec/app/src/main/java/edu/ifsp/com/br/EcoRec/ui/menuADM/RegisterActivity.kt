package edu.ifsp.com.br.EcoRec.ui.menuADM

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.databinding.ActivityMainBinding
import edu.ifsp.com.br.EcoRec.databinding.RegisterActivityBinding
import edu.ifsp.com.br.EcoRec.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterActivityBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners(){
        binding.buttonLogout.setOnClickListener{
            logout()
        }
    }

    private fun logout(){
        viewModel.logout()

        val mIntent = Intent(this, MainActivity::class.java)
        Toast.makeText(this, "Logout feito com sucesso!.", Toast.LENGTH_SHORT).show()
        startActivity(mIntent)
        finish()
    }
}