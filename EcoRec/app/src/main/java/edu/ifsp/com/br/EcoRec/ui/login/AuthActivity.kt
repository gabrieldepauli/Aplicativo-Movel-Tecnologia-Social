package edu.ifsp.com.br.EcoRec.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.ifsp.com.br.EcoRec.R
import edu.ifsp.com.br.EcoRec.databinding.AuthActivityBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: AuthActivityBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setupObservers()
        setupListeners()
    }

    private fun setupListeners(){
        binding.buttonLogin.setOnClickListener{
            logar()
        }
    }

    private fun logar(){
        val username = binding.textUsername.text.toString()
        val password = binding.textPassword.text.toString()
        val staylogged = binding.checkboxStayLogged.isChecked

        binding.textUsername.setText("")
        binding.textPassword.setText("")
        binding.checkboxStayLogged.isChecked = false

        if(username.isNotEmpty() && password.isNotEmpty()){
            viewModel.login(username, password, staylogged)
        }else{
            Toast.makeText(this, "Preencha os dados corretamente.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers(){
        viewModel.loggedIn.observe(this, Observer {
            if(it){
                setResult(RESULT_OK)
                finish()
            }else{
                Toast.makeText(this, "Login Incorreto!.", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.stayLoggedPreferences.observe(this, Observer {
            val stayLogged = it

            if(stayLogged){
                setResult(RESULT_OK)
                finish()
            }

            binding.checkboxStayLogged.isChecked = stayLogged
        })

        viewModel.dataPreferences.observe(this, Observer {
            val (username, password) = it
            binding.textUsername.setText(username)
            if(username.isNotEmpty()){
                binding.textPassword.setText(password)
            }
        })
    }
}