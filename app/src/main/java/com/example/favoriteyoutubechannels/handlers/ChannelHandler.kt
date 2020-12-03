package com.example.favoriteyoutubechannels.handlers

import com.example.favoriteyoutubechannels.model.TopChannel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChannelHandler {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var channelRef: DatabaseReference

    init {
        channelRef = database.getReference("channels")
    }

    fun create(channel: TopChannel): Boolean {
        val id = channelRef.push().key
        channel.id = id

        channelRef.child(id!!).setValue(channel)
        return true
    }

    fun update(channel: TopChannel): Boolean {
        channelRef.child(channel.id!!).setValue(channel)
        return true
    }

    fun delete(channel: TopChannel): Boolean {
        channelRef.child(channel.id!!).removeValue()
        return true
    }
}