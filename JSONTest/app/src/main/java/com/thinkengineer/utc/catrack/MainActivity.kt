package com.thinkengineer.utc.catrack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
data class Data(var userID: String="", var catName: String="", var photoURI: String="")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            newCat()
        }
    }

    private fun newCat() {
        val catData = Data()
        catData.userID = userID_Field.text.toString()
        catData.catName = catName_Field.text.toString()
        catData.photoURI = photoURI_Field.text.toString()
        System.out.println(JSON.stringify(catData))
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.post("https://us-central1-te-cattrack.cloudfunctions.net/newCat").body(JSON.stringify(catData)).response { request, response, result ->
            System.out.println(request)
            System.out.println(response)
            System.out.println(result)
        }
    }
}
