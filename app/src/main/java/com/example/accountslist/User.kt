package com.example.accountslist

class User (var id:Int,var username:String,var password:String,var email:String,var cellphone:String){
    override fun toString(): String {
        return "$username"
    }
}