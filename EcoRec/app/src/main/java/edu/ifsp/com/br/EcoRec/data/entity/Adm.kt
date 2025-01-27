package edu.ifsp.com.br.EcoRec.data.entity

object Adm {
    val login = "admim"
    val password = "admim"
    fun authenticate(inputLogin: String, inputPassword: String): Boolean {
        return inputLogin == login && inputPassword == password
    }
}