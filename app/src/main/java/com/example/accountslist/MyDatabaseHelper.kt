package com.example.accountslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.Intents.Insert

class MyDatabaseHelper (context: Context):
    SQLiteOpenHelper(context,"my_db.db",null,1){
    //Criação da tabela para Usuários
        val sql = arrayOf(
        "CREATE TABLE user("+
                "idUser INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT NOT NULL,"+
                "senha TEXT NOT NULL,"+
                "email TEXT NOT NULL,"+
                "telefone NOT NULL);"
    )

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE user")
        onCreate(db)
    }

    //Função de Leitura de Usuários
    fun readUsers():List<User>{
        val users= mutableListOf<User>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user",null)

        cursor.use{
            while (it.moveToNext()){
                var idIndex =it.getColumnIndex("idUser")
                var nomeIndex=it.getColumnIndex("nome")
                var senhaIndex=it.getColumnIndex("senha")
                var emailIndex =it.getColumnIndex("email")
                var telefoneIndex=it.getColumnIndex("telefone")

                users.add(
                    User(
                        it.getInt(idIndex),
                        it.getString(nomeIndex),
                        it.getString(senhaIndex),
                        it.getString(emailIndex),
                        it.getString(telefoneIndex),
                    )
                    )

            }
        }
        return users
    }

    fun readById(id:Int):List<User>{
        val users= mutableListOf<User>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT idUsers FROM user Where = '$id'",null)

        cursor.use{
            while (it.moveToNext()){
                var idIndex =it.getColumnIndex("idUser")
                var nomeIndex=it.getColumnIndex("nome")
                var senhaIndex=it.getColumnIndex("senha")
                var emailIndex =it.getColumnIndex("email")
                var telefoneIndex=it.getColumnIndex("telefone")

                users.add(
                    User(
                        it.getInt(idIndex),
                        it.getString(nomeIndex),
                        it.getString(senhaIndex),
                        it.getString(emailIndex),
                        it.getString(telefoneIndex),
                    )
                )

            }
        }
        return users
    }

    //Função de inseção de Usuários
    fun insertUser(nome:String,senha:String,email:String,telefone:String):Long{
        val user= ContentValues().apply {
            put("nome",nome)
            put("senha",senha)
            put("email",email)
            put("telefone",telefone)
        }
        return writableDatabase.insert("user",null,user)
    }

    //Função de Atualização de Usuários
    fun updateUser (user:User):Int{
        val values = ContentValues().apply {
            put("nome",user.username)
            put("senha",user.password)
            put("email",user.email)
            put("telefone",user.cellphone)
        }
        return writableDatabase.update(
            "user",
            values,
            "idUser = ?",
            arrayOf(user.id.toString())
        )
    }

    //Função de Exclusão de Usuários
    fun deleteUser (id:Int):Int{
        return writableDatabase.delete("user","idUser = ?", arrayOf(id.toString()))
    }

}