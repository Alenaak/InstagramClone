package com.example.instaclone.models

class User {
    var photo:String?=null
    var username:String?=null
    var email:String?=null
    var password:String?=null

    constructor()

    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }

    constructor(photo: String?, username: String?, email: String?, password: String?) {
        this.photo = photo
        this.username = username
        this.email = email
        this.password = password
    }

    constructor(password: String?, email: String?) {
        this.password = password
        this.email = email
    }


}