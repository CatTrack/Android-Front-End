package com.thinkengineer.utc.cattrack

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import com.github.kittinunf.fuel.android.extension.jsonDeserializer
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_cat.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.JsonObject
import com.thinkengineer.utc.cattrack.CatActivity.c.getHTML
import khttp.get
import org.json.JSONObject
import java.net.URL
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection


val currentFirebaseUser = FirebaseAuth.getInstance().currentUser

class CatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)
        kotlin.run {
            Thread( Runnable{
            var catIntent = getIntent()
            var catID = catIntent.getStringExtra("catNum")
                val textView = findViewById<TextView>(R.id.name)
            val FUID = catIntent.getStringExtra("userID")
            // ...
            // Instantiate the RequestQueue.
            val Url = "http://us-central1-te-cattrack.cloudfunctions.net/getCatsAdv?userID=0"//?userID=" + FUID

            // Request a string response from the provided URL.
            val catList = getHTML(Url)
                print(catList)
                val parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(catList)
                val catListObject: JSONObject = parser.parse(stringBuilder) as JSONObject
                val CatObject: JSONObject = catListObject.getJSONObject("0")
            val Identity = CatObject.getJSONObject("Identifier")
            val name = Identity.getString("stringValue")
                runOnUiThread(Runnable { textView.text = name })
        }).start()}

// Add the request to the RequestQueue.

        }

    override fun getIntent(): Intent {
        return super.getIntent()
    }

    object c {

        @Throws(Exception::class)
        fun getHTML(urlToRead: String): String {
            val url = URL(urlToRead)
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestMethod("GET")
            val rd = BufferedReader(InputStreamReader(conn.getInputStream()))
            val result = rd.toString()

            return result
        }

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            println(getHTML(args[0]))
        }
    }



    }

