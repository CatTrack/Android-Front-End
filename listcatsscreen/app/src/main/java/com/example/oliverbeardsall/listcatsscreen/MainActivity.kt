package com.example.oliverbeardsall.listcatsscreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listcats)
        var counter: Int = 1;

    // Set a click listener for button widget
        // Create a new TextView instance programmatically
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
        text_view.text = "Hi, i am a TextView. Number : $counter"

        // Set the text view font/text size
        text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP,30F)

        // Set the text view text color
        text_view.setTextColor(Color.RED)

        // Make the text viw text bold italic
        text_view.setTypeface(text_view.typeface,Typeface.BOLD_ITALIC)

        // Change the text view font
        text_view.setTypeface(Typeface.MONOSPACE)

        // Change the text view background color
        text_view.setBackgroundColor(Color.YELLOW)

        // Put some padding on text view text
        text_view.setPadding(50,10,10,10)

        // Set a click listener for newly generated text view
        text_view.setOnClickListener{
            Toast.makeText(this,text_view.text,Toast.LENGTH_SHORT).show()
        }

        // Finally, add the text view to the view group
        root_layout.addView(text_view)

        // Increment the counter
        counter++
    }
}