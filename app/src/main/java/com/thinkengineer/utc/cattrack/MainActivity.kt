package com.thinkengineer.utc.cattrack

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.LinearLayout.LayoutParams
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var displayName: TextView
    private lateinit var status: TextView
    private lateinit var cat1Button: Button
    private lateinit var backbutton: Button
    private lateinit var logout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        displayName = findViewById<TextView>(R.id.nameTextView)
        status = findViewById<TextView>(R.id.statusTextView)
        logout = findViewById<Button>(R.id.singoutButton)
        cat1Button = findViewById<Button>(R.id.cat1button)

         //do something with response
            Fuel.get("https://us-central1-te-cattrack.cloudfunctions.net/getCatsAdv?userID=0").response { request, response, result ->
                println(result)
                val catsObject: JSONObject = result as JSONObject
                }

        cat1Button.setOnClickListener(){
            setContentView(R.layout.activity_cat1profile)}

        val text_view: TextView = TextView(this)

        // Creating a LinearLayout.LayoutParams object for text view
        var params : LayoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, // This will define text view width
                LayoutParams.WRAP_CONTENT // This will define text view height
        )

        // Add margin to the text view
        params.setMargins(10,10,10,10)

        // Now, specify the text view width and height (dimension)
        text_view.layoutParams = params

        // Display some text on the newly created text view
        text_view.text = "Cat"

        // Set the text view font/text size
        text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP,30F)

        // Set the text view text color
        text_view.setTextColor(Color.WHITE)

        // Change the text view background color
        text_view.setBackgroundColor(Color.parseColor("#ff006e"))

        // Put some padding on text view text
        text_view.setPadding(50,10,10,10)

        // Set a click listener for newly generated text view
        text_view.setOnClickListener{
            Toast.makeText(this,text_view.text,Toast.LENGTH_SHORT).show()
        }

        // Finally, add the text view to the view group
        catsLayout.addView(text_view)
        logout.setOnClickListener(){
            auth.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        isLogin()

    }



    private fun isLogin(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)

        auth.currentUser?.uid?.let { loadData(it)  } ?: startActivity(intent)

    }

    private fun loadData(userId: String){
        val dataListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                return //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
