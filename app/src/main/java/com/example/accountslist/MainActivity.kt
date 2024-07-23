package com.example.accountslist

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.accountslist.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabaseHelper(this)


        //Variaveis para uso de inputs facilitados
        val inputName = binding.inputName.text.toString()
        val inputPass = binding.inputPass.text.toString()
        val inputEmail = binding.inputEmail.text.toString()
        val inputPhone = binding.inputPhone.text.toString()

        binding.registerButton.setOnClickListener {
            if (!inputName.trim().isEmpty() &&
                !inputPass.trim().isEmpty()&&
                !inputEmail.isEmpty()&&
                !inputPhone.trim().isEmpty()
            ) {

                val value = database.insertUser(inputName,inputPass,inputEmail,inputPhone)

                if(value > -1){
                    Snackbar.make(
                        binding.root,
                        "Usuário " + inputName +" foi cadastrado com sucesso!",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.LightYellow))
                        .setTextColor(resources.getColor(R.color.white))
                        .show()
                }

                //adapter.notifyDataSetChanged()
                ClearFields()
            } else {
                Snackbar.make(binding.root, "Campos Vazios!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.AcrilycRed))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }
        }

        binding.listButton.setOnClickListener{
            startActivity(Intent(this,ListActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun ClearFields(){
        binding.inputName.setText("")
        binding.inputPass.setText("")
        binding.inputEmail.setText("")
        binding.inputPhone.setText("")
    }
}