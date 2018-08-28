package com.thinkengineer.utc.cattrack

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
data class Data(var userID: String="", var catName: String="", var photoURI: String="", var catID: String="")

class MainActivity : AppCompatActivity() {
    private lateinit var displayName: TextView
    private lateinit var status: TextView
    private lateinit var cat1Button: Button
    private lateinit var backbutton: Button
    private lateinit var logout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var UID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        displayName = findViewById<TextView>(R.id.nameTextView)
        status = findViewById<TextView>(R.id.statusTextView)
        logout = findViewById<Button>(R.id.singoutButton)
        cat1Button = findViewById<Button>(R.id.cat1button)

        cat1Button.setOnClickListener(){
            setContentView(R.layout.activity_cat1profile)}

        logout.setOnClickListener(){
            auth.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        isLogin()

        UID = FirebaseAuth.getInstance().currentUser!!.uid
        System.out.println(UID);

        /* val catData = Data()
        catData.userID = userID_Field.text.toString()
        System.out.println(JSON.stringify(catData))
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.get("https://us-central1-te-cattrack.cloudfunctions.net/getCats", listOf("userID" to catData.userID )).responseString { request, response, result ->
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
        }*/
    }

}



private fun isLogin(){
    val intent = Intent(this@MainActivity, LoginActivity::class.java)

    auth.currentUser?.uid?.let { loadData(it)  } ?: startActivity(intent)

}

private fun loadData(userId: String){
    val dataListener = object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if(dataSnapshot.exists()){
                var user: User = dataSnapshot.getValue(User::class.java)!!
                displayName.text = user.displayName
                status.text = user.status
            }

        }
    }
    database.reference.child("user")
            .child(userId).addListenerForSingleValueEvent(dataListener)

}



}
