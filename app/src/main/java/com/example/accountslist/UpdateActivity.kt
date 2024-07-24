package com.example.accountslist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.accountslist.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabaseHelper(this)

        val intentId = intent

        intentId.getStringExtra("id").toString().toInt()

        var listInput = database.readById(intentId.getStringExtra("id").toString().toInt())

        binding.inputName.setText(listInput.get(0).username)
        binding.inputPass.setText(listInput.get(0).password)
        binding.inputEmail.setText(listInput.get(0).email)
        binding.inputPhone.setText(listInput.get(0).cellphone)




        binding.updateButton.setOnClickListener {
            var id = intentId.getStringExtra("id").toString().toInt()
            var nome = binding.inputName.text.toString()
            var telefone = binding.inputPhone.text.toString()
            var email = binding.inputEmail.text.toString()
            var senha = binding.inputPass.text.toString()

            var user = User(id,nome,senha,email,telefone)

            database.updateUser(user)

            startActivity(Intent(this,ListActivity::class.java))
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}