package com.example.adam.jsontest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import com.github.kittinunf.fuel.Fuel
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON

@Serializable
data class data(var userID: String="", var catName: String="", var photoURI: String="")

class MainActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        button = findViewById(R.id.button)

        button.setOnClickListener {

            val catData = data()
            catData.userID = "1"
            catData.catName = "John Cena"
            catData.photoURI = "John Pic"
            System.out.println(JSON.stringify(catData))
            Fuel.post("https://us-central1-te-cattrack.cloudfunctions.net/newCat").body(JSON.stringify(catData)).response { request, response, result ->
                System.out.println(request)
                System.out.println(response)
                System.out.println(result)
            }


    }
    }



}
