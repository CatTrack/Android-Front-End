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

import kotlinx.android.synthetic.main.activity_cat.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.json.JSONObject
import java.net.URL

val currentFirebaseUser = FirebaseAuth.getInstance().currentUser

class CatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)
        var catIntent = getIntent()
        var catID = catIntent.getIntExtra("catNum", 1)
        val textView = findViewById<TextView>(R.id.name)
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
// ...

// Instantiate the RequestQueue.
        val url = "https://us-central1-te-cattrack.cloudfunctions.net/listCats?userID="+currentFirebaseUser

// Request a string response from the provided URL.
        val catList = URL(url).getContent()
        val json: JSONObject = catList as JSONObject
        val catObject: JSONObject = json.getJSONObject(catID.toString())
        val name = catObject.getString("Identifier")
        textView.text = name

// Add the request to the RequestQueue.

        }

    override fun getIntent(): Intent {
        return super.getIntent()
    }



    }

