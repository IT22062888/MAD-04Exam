package com.example.notessqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding
import com.example.notessqlite.NoteDatabaseHelper
import com.example.notessqlite.Note

// Activity for updating an existing note

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding // Declare a variable for view binding
    private lateinit var db: NoteDatabaseHelper // Declare a variable for database helper
    private var noteId: Int = -1 // Variable to store the ID of the note being updated

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater) // Inflate the layout using view binding
        setContentView(binding.root) // Set the content view to the root of the inflated layout

        db = NoteDatabaseHelper(this) // Initialize the database helper

        noteId = intent.getIntExtra("note_id", -1) // Get the note ID passed from the intent
        if (noteId == -1){
            finish() // Finish the activity if note ID is not valid
            return
        }
        val note = db.getNoteByID(noteId) // Get the note from the database using its ID
        binding.updateTitleEditText.setText(note.title) // Set the title EditText with the note's title
        binding.updateContentEditText.setText(note.content) // Set the content EditText with the note's content

        binding.updateSaveButton.setOnClickListener { // Set click listener for the save button
            val newTitle = binding.updateTitleEditText.text.toString() // Get the new title from the EditText
            val newContent = binding.updateContentEditText.text.toString() // Get the new content from the EditText
            val updateNote = Note(noteId, newTitle, newContent) // Create a new Note object with updated data
            db.updateNote(updateNote) // Update the note in the database
            finish() // Finish the activity
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show() // Show a toast message indicating that changes are saved
        }
    }
}
