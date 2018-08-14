package com.example.ben.firebase_test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.channels.SeekableByteChannel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val initialTextViewTranslationY = textView_progress.translationY

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_progress.text = progress.toString()

                val translationDistance = (initialTextViewTranslationY + progress * resources.getDimension(R.dimen.text_anim_step) * -1)

                textView_progress.animate().translationY(translationDistance)
                    if (!fromUser)
                        textView_progress.animate().setDuration(500).rotationBy(360f).translationY(initialTextViewTranslationY)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        button_reset.setOnClickListener { V ->
            if (seekBar.progress == 0) {
                System.out.println("current progress 0")
            } else {
                seekBar.progress = 0
                textView_progress.animate().setDuration(500).rotationBy(360f)
                        .translationY(initialTextViewTranslationY)
            }
        }
    }
}
