package com.example.notessqlite

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notessqlite.databinding.ActivityAddNoteBinding
import com.example.notessqlite.Note
import com.example.notessqlite.NoteDatabaseHelper

// Import necessary packages and classes

class AddNoteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding // Declare a variable for view binding
    private lateinit var db: NoteDatabaseHelper // Declare a variable for database helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater) // Inflate the layout using view binding
        setContentView(binding.root) // Set the content view to the root of the inflated layout

        db = NoteDatabaseHelper(this) // Initialize the database helper

        binding.saveButton.setOnClickListener { // Set click listener for the save button
            val title = binding.titleEditText.text.toString() // Get the text from title EditText
            val content = binding.contentEditText.text.toString() // Get the text from content EditText
            val note = Note(0, title, content) // Create a Note object with the provided title and content
            db.insertNote(note) // Insert the note into the database
            finish() // Finish the activity
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show() // Show a toast message indicating that the note is saved
        }

    }
}
