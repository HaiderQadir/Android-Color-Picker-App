package com.hq.colorpicker

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText


/** @author Haider Qadir **/

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(this)


        val colorSlider = findViewById<SeekBar>(R.id.colorSlider)
        val tvColor = findViewById<TextView>(R.id.tvColor)
        val editText = findViewById<TextInputEditText>(R.id.editText)
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val view = findViewById<View>(R.id.view)

        tvColor.setOnClickListener {
            var clipboad: ClipboardManager =
                this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            var clip: ClipData = ClipData.newPlainText("label", tvColor.text.toString())
            clipboad.setPrimaryClip(clip)
        }

        editText.setText("ffffff")

        confirmButton.setOnClickListener {

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(confirmButton.windowToken, 0)
            try {
                var editTextString: String = editText.text.toString()
                confirmButton.setBackgroundColor(Color.parseColor("#$editTextString"));
                view.setBackgroundColor(Color.parseColor("#$editTextString")) // Change this line
                tvColor.text = "Color Hex: #$editTextString"
                tvColor.setBackgroundColor(Color.parseColor("#$editTextString")) // Change this line


            } catch (nfe: NumberFormatException) {
                Toast.makeText(this, nfe.toString(), Toast.LENGTH_SHORT).show()
            } catch (ia: IllegalArgumentException) {
                Toast.makeText(this, ia.toString(), Toast.LENGTH_SHORT).show()
            }

            /* var animate=TranslateAnimation(0f,0f,tvColor.height.toFloat(),0f)
             animate.duration=1000
             animate.fillAfter=true

             tvColor.startAnimation(animate)*/

            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
            tvColor.startAnimation(shake)


            /**
             *
             * fromXDelta	float: Change in X coordinate to apply at the start of the animation
            toXDelta	float: Change in X coordinate to apply at the end of the animation
            fromYDelta	float: Change in Y coordinate to apply at the start of the animation
            toYDelta	float: Change in Y coordinate to apply at the end of the animation
             */


        }

        val hexTemplate = "#%02X%02X%02X"

        colorSlider.max = 16777215 // Maximum value for RGB color

        colorSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Calculate the RGB components from the progress
                val red = Color.red(progress)
                val green = Color.green(progress)
                val blue = Color.blue(progress)

                tvColor.setBackgroundColor(Color.rgb(red, green, blue)) // Change this line
                view.setBackgroundColor(Color.rgb(red, green, blue)) // Change this line

                val hexValue = String.format(hexTemplate, red, green, blue)

                tvColor.text = "Color Hex: $hexValue"

                confirmButton.setBackgroundColor(Color.parseColor(hexValue))

                val hexTemplate2 = "%02X%02X%02X"

                val hexValue2 = String.format(hexTemplate2, red, green, blue)
                editText.setText(hexValue2)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }


}