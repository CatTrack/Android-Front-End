package com.example.adam.jsontest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import com.github.kittinunf.fuel.Fuel
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON

@Serializable
data class userNameDataClass(var name: String="")

class MainActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var button: Button

    private val userName: userNameDataClass = userNameDataClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            sendData()
        }
    }

    private fun sendData() {
        userName.name = name.text.toString()

        System.out.println(userName.name)
    }


}
