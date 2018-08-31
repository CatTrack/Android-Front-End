package com.thinkengineer.utc.cattrack

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thinkengineer.utc.cattrack.CatActivity;
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var displayName: TextView
    private lateinit var status: TextView
    private lateinit var cat1Button: Button
    private lateinit var backbutton: Button
    private lateinit var logout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        user = FirebaseAuth.getInstance().getCurrentUser().toString()
        displayName = findViewById<TextView>(R.id.nameTextView)
        status = findViewById<TextView>(R.id.statusTextView)
        logout = findViewById<Button>(R.id.singoutButton)
        cat1Button = findViewById<Button>(R.id.cat1button)
        print(user)
        print("test")
        cat1Button.setOnClickListener({
                val intent =  Intent(applicationContext, CatActivity::class.java)
                        intent.putExtra("catNum", "0")
                        intent.putExtra("userID", user)
                startActivity(intent)
            })

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

    }}
