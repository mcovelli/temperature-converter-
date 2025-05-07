package com.example.tempteratureconverter

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.text.method.ScrollingMovementMethod
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

const val OUTPUT = "output_text"
const val HISTORY = "history_text"

class MainActivity : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var conversionRadioGroup: RadioGroup
    private lateinit var celsiusToFahrenheitRadio: RadioButton
    private lateinit var fahrenheitToCelsiusRadio: RadioButton
    private lateinit var outputTextView: TextView
    private lateinit var historyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.input_edit_text)
        conversionRadioGroup = findViewById(R.id.conversion_radio_group)
        celsiusToFahrenheitRadio = findViewById(R.id.celsiusToFahrenheit_radio_button)
        fahrenheitToCelsiusRadio = findViewById(R.id.fahrenheitToCelsius_radio_button)
        outputTextView = findViewById(R.id.output_textview)
        historyTextView = findViewById(R.id.history)

        historyTextView.movementMethod = ScrollingMovementMethod.getInstance()

        if (savedInstanceState != null) {
            outputTextView.text = savedInstanceState.getString(OUTPUT)
            historyTextView.text = savedInstanceState.getString(HISTORY)
        }
    }

    fun convertClick(view: View) {
        val inputStr = inputEditText.text.toString().trim()
        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        val inputTemp = inputStr.toDoubleOrNull() ?: return

        val outputTemp: Double
        val inputUnit: String
        val outputUnit: String

        if (celsiusToFahrenheitRadio.isChecked) {
            outputTemp = TempConverter().celsiusToFahrenheit(inputTemp)
            inputUnit = "°C"
            outputUnit = "°F"
        } else {
            outputTemp = TempConverter().fahrenheitToCelsius(inputTemp)
            inputUnit = "°F"
            outputUnit = "°C"
        }

        val formattedOutput = String.format(Locale.US, "%.1f", outputTemp)
        outputTextView.text = "$formattedOutput$outputUnit"
        val historyEntry = "$inputTemp$inputUnit → $formattedOutput$outputUnit"

        // **Insert new entry at the top instead of appending**
        historyTextView.text = historyEntry + "\n" + historyTextView.text.toString()

    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        outState.putString(OUTPUT, outputTextView.text.toString())
        outState.putString(HISTORY, historyTextView.text.toString())
    }
}