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

        binding.registerButton.setOnClickListener {
            if (!binding.inputName.text.toString().trim().isEmpty() &&
                !binding.inputPass.text.toString().trim().isEmpty() &&
                !binding.inputEmail.text.toString().trim().isEmpty()&&
                !binding.inputPhone.text.toString().trim().isEmpty()
            ) {

                val value = database.insertUser(
                    binding.inputName.text.toString()
                    ,binding.inputPass.text.toString()
                    ,binding.inputEmail.text.toString()
                    ,binding.inputPhone.text.toString())

                if(value > -1){
                    Snackbar.make(
                        binding.root,
                        "Usuário " + binding.inputName.text.toString() +" foi cadastrado com sucesso!",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(resources.getColor(R.color.LightYellow))
                        .setTextColor(resources.getColor(R.color.white))
                        .show()
                }

                database.readUsers()

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
            finish()
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