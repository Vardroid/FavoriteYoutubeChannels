package com.example.favoriteyoutubechannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.favoriteyoutubechannels.handlers.ChannelHandler
import com.example.favoriteyoutubechannels.model.TopChannel

class AddChannel : AppCompatActivity() {
    lateinit var nameTxt: EditText
    lateinit var linkTxt: EditText
    lateinit var reasonTxt: EditText
    lateinit var addBtn: Button
    lateinit var channelHandler: ChannelHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_channel)

        channelHandler = ChannelHandler()

        nameTxt = findViewById(R.id.add_channel_name_txt)
        linkTxt = findViewById(R.id.add_channel_link_txt)
        reasonTxt = findViewById(R.id.add_reason_txt)

        addBtn = findViewById(R.id.add_btn)

        addBtn.setOnClickListener {
            val name = nameTxt.text.toString()
            val link = linkTxt.text.toString()
            val reason = reasonTxt.text.toString()

            val channel = TopChannel(name = name, link = link, reason = reason)
            if(channelHandler.create(channel)){
                Toast.makeText(applicationContext, "Channel Added.", Toast.LENGTH_LONG).show()
            }
        }
    }
}