package com.example.techtriv_fyp.Models

class Users {
    var Name:String?=null
    var age:String?=null
    var email:String?=null
    var password:String?=null
    constructor()
    constructor(Name: String?, age: String?, email: String?, password: String?) {
        this.Name = Name
        this.age = age
        this.email = email
        this.password = password
    }


}