package com.example.favoriteyoutubechannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditChannel : AppCompatActivity() {
    lateinit var editSongBtn: Button
    lateinit var titleTxt: EditText
    lateinit var artistTxt: EditText
    lateinit var albumTxt: EditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_channel)


    }
}