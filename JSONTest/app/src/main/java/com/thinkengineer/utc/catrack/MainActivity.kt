package com.thinkengineer.utc.catrack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON


@Serializable
data class Data(var userID: String="", var catName: String="", var photoURI: String="", var catID: String="")

@Serializable
data class Cats(var cats: List<String>)


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newCat_Button.setOnClickListener {
            newCat()
        }

        getCats_Button.setOnClickListener {
            getCats()
        }

        getCatLocation_Button.setOnClickListener {
            getCatLocation()
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


    private fun getCats() {
        val catData = Data()
        catData.userID = userID_Field.text.toString()
        System.out.println(JSON.stringify(catData))
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.get("https://us-central1-te-cattrack.cloudfunctions.net/getCats", listOf("userID" to catData.userID )).responseString { request, response, result ->
            System.out.println(result)
            result.fold({ cats ->
                System.out.println(cats)
                /* Give object name so it matches Data class */
                var catObject = JSON.parse<Cats>("{\"cats\": $cats}")
                // Blame Andrejus

                var catsFormatted = ""
                catObject.cats.forEach { cat ->
                    catsFormatted += "\n$cat"
                }
                responseOutput.text = catsFormatted
            }, {
                error -> responseOutput.text = error.toString()
            })
        }
    }


    private fun getCatLocation() {
        val catData = Data()
        catData.userID = userID_Field.text.toString()
        catData.catID = catID_Field.text.toString()
        System.out.println(JSON.stringify(catData))
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.get("https://us-central1-te-cattrack.cloudfunctions.net/getCatLocation").body(JSON.stringify(catData)).response { request, response, result ->
            System.out.println(request)
            System.out.println(response)
            System.out.println(result)
        }
}}
