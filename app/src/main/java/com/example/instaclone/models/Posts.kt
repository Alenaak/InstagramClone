package com.example.instaclone.models

class Posts {
    var posturl:String?=null
    var caption:String?=null
    var uid:String?=null
    var time:String?=null

    constructor()

    constructor(posturl: String?, caption: String?) {
        this.posturl = posturl
        this.caption = caption

    }


    constructor(posturl: String?) {
        this.posturl =posturl

    }

    constructor(posturl: String?, caption: String?, uid: String?, time: String?) {
        this.posturl = posturl
        this.caption = caption
        this.uid = uid
        this.time = time
    }


}