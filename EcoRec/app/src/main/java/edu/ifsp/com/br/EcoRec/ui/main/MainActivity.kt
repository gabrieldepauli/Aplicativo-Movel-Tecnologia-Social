package edu.ifsp.com.br.EcoRec.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import edu.ifsp.com.br.EcoRec.databinding.ActivityMainBinding
import edu.ifsp.com.br.EcoRec.ui.login.AuthActivity
import edu.ifsp.com.br.EcoRec.ui.menuADM.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoginLauncher()
        setupListeners()
    }

    private fun setupLoginLauncher() {
        loginLauncher = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK) {
                    Toast.makeText(this, "Login feito com sucesso!", Toast.LENGTH_SHORT).show()
                    val mIntent = Intent(this, RegisterActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
            }
        )
    }

    private fun setupListeners(){
        binding.buttonLoginAdm.setOnClickListener{
            val mIntent = Intent(this, AuthActivity::class.java)
            loginLauncher.launch(mIntent)
        }

        binding.buttonAcessarDados.setOnClickListener {

        }
    }
}