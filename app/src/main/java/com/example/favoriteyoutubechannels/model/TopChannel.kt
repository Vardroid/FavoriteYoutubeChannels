package com.example.favoriteyoutubechannels.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class TopChannel (var id: String? = "", var name: String? = "", var link: String? = "", var reason: String? = "") {

}