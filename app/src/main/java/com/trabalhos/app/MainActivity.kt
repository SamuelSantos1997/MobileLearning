package com.trabalhos.app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var editTitle: EditText
    private lateinit var radioGroupStatus: RadioGroup
    private lateinit var radioGroupPriority: RadioGroup
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var btnChooseDate: Button
    private lateinit var btnChooseTime: Button
    private lateinit var btnCancel: Button
    private lateinit var btnReset: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnModernArt: Button

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Trabalhos"

        initViews()
        setupListeners()
        updateDateTimeDisplay()
    }

    private fun initViews() {
        editTitle = findViewById(R.id.editTitle)
        radioGroupStatus = findViewById(R.id.radioGroupStatus)
        radioGroupPriority = findViewById(R.id.radioGroupPriority)
        tvDate = findViewById(R.id.tvDate)
        tvTime = findViewById(R.id.tvTime)
        btnChooseDate = findViewById(R.id.btnChooseDate)
        btnChooseTime = findViewById(R.id.btnChooseTime)
        btnCancel = findViewById(R.id.btnCancel)
        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnModernArt = findViewById(R.id.btnModernArt)
    }

    private fun setupListeners() {
        btnChooseDate.setOnClickListener { showDatePicker() }
        btnChooseTime.setOnClickListener { showTimePicker() }
        btnCancel.setOnClickListener { finish() }
        btnReset.setOnClickListener { resetForm() }
        btnSubmit.setOnClickListener { submitForm() }
        btnModernArt.setOnClickListener {
            startActivity(Intent(this, ModernArtActivity::class.java))
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            calendar.set(Calendar.YEAR, y)
            calendar.set(Calendar.MONTH, m)
            calendar.set(Calendar.DAY_OF_MONTH, d)
            updateDateTimeDisplay()
        }, year, month, day)

        datePicker.show()
    }

    private fun showTimePicker() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(this, { _, h, m ->
            calendar.set(Calendar.HOUR_OF_DAY, h)
            calendar.set(Calendar.MINUTE, m)
            calendar.set(Calendar.SECOND, 0)
            updateDateTimeDisplay()
        }, hour, minute, true)

        timePicker.show()
    }

    private fun updateDateTimeDisplay() {
        tvDate.text = dateFormat.format(calendar.time)
        tvTime.text = timeFormat.format(calendar.time)
    }

    private fun resetForm() {
        editTitle.text.clear()
        radioGroupStatus.check(R.id.radioNotDone)
        radioGroupPriority.check(R.id.radioMedium)
        calendar.timeInMillis = System.currentTimeMillis()
        updateDateTimeDisplay()
        Toast.makeText(this, "Formulário resetado", Toast.LENGTH_SHORT).show()
    }

    private fun submitForm() {
        val title = editTitle.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um título", Toast.LENGTH_SHORT).show()
            return
        }

        val statusId = radioGroupStatus.checkedRadioButtonId
        val status = findViewById<RadioButton>(statusId).text.toString()

        val priorityId = radioGroupPriority.checkedRadioButtonId
        val priority = findViewById<RadioButton>(priorityId).text.toString()

        val date = tvDate.text.toString()
        val time = tvTime.text.toString()

        val message = "Trabalho: $title\nStatus: $status\nPrioridade: $priority\nData: $date\nHora: $time"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
