package com.example.favoriteyoutubechannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.favoriteyoutubechannels.handlers.ChannelHandler
import com.example.favoriteyoutubechannels.model.TopChannel

class EditChannel : AppCompatActivity() {
    lateinit var nameTxt: EditText
    lateinit var linkTxt: EditText
    lateinit var reasonTxt: EditText
    lateinit var editBtn: Button
    lateinit var channelHandler: ChannelHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_channel)

        //get extras from intent
        val chanId = intent.getStringExtra("chanId")
        val position = intent.getIntExtra("position", 2)
        channelHandler = ChannelHandler()

        nameTxt = findViewById(R.id.edit_channel_name_txt)
        linkTxt = findViewById(R.id.edit_channel_link_txt)
        reasonTxt = findViewById(R.id.edit_reason_txt)

        nameTxt.setText(MainActivity.channels[position].name)
        linkTxt.setText(MainActivity.channels[position].link)
        reasonTxt.setText(MainActivity.channels[position].reason)

        editBtn = findViewById(R.id.edit_btn)

        editBtn.setOnClickListener {
            val name = nameTxt.text.toString()
            val link = linkTxt.text.toString()
            val reason = reasonTxt.text.toString()

            val channel = TopChannel(id = chanId, name = name, link = link, reason = reason)
            if (channelHandler.update(channel)) {
                Toast.makeText(applicationContext, "Channel Updated.", Toast.LENGTH_LONG).show()
            }
        }
    }
}