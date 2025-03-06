package com.example.instaclone.models

class Reel {
    var reelurl:String?=null
    var caption:String?=null
    var profileLink:String?=null

    constructor()

    constructor(reelurl: String?, caption: String?) {
        this.reelurl = reelurl
        this.caption = caption

    }


    constructor(reelurl: String?) {
        this.reelurl =reelurl

    }

    constructor(reelurl: String?, caption: String?, profileLink: String?) {
        this.reelurl = reelurl
        this.caption = caption
        this.profileLink = profileLink
    }


}