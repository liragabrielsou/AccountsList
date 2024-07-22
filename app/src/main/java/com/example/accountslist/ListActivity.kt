package com.example.accountslist

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.accountslist.databinding.ActivityListBinding

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
        var adapter = setAdapter(listUsers)

        binding.listRegisters.adapter = adapter

        listUsers = database.readUsers()
        adapter = setAdapter(listUsers)
        binding.listRegisters.adapter = adapter

        binding.listRegisters.setOnItemClickListener { parent, view, position, id ->
            binding.id.text = listUsers.get(position).id.toString()
            this.position = position
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