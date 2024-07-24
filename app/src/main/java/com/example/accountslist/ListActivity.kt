package com.example.accountslist

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.accountslist.databinding.ActivityListBinding
import com.google.android.material.snackbar.Snackbar

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    //Variavel de posição
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabaseHelper(this)
        var listUsers = database.readUsers()
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listUsers)
        binding.listRegisters.adapter = adapter



        binding.listRegisters.setOnItemClickListener { parent, view, position, id ->
            binding.id.text = listUsers.get(position).id.toString()
            this.position = position
        }

        binding.editButton.setOnClickListener{
            startActivity(Intent(this,UpdateActivity::class.java).putExtra("id",binding.id.text))
        }

        binding.deleteButton.setOnClickListener {
            val value = database.deleteUser((binding.id.text.toString().toInt()))

            if(value != 0){
                Snackbar.make(
                    binding.root,
                    "Usuário excluido !",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            listUsers = database.readUsers()
            adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listUsers)
            binding.listRegisters.adapter = adapter

            adapter.notifyDataSetChanged()

        }

        binding.homeButton.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun setAdapter(array:List<User>): ArrayAdapter<User> {
        return ArrayAdapter(this, android.R.layout.simple_list_item_1,array)
    }
}