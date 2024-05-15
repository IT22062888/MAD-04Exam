package com.example.notessqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Class for managing the SQLite database for notes

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db" // Database name
        private const val DATABASE_VERSION = 1 // Database version
        private const val TABLE_NAME = "allnotes" // Table name for storing all notes
        private const val COLUMN_ID = "id" // Column name for note ID
        private const val COLUMN_TITLE = "title" // Column name for note title
        private const val COLUMN_CONTENT = "content" // Column name for note content
    }

    // Method called when the database is created
    override fun onCreate(db: SQLiteDatabase?) {
        // SQL query to create the notes table
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery) // Execute the SQL query to create the table
    }

    // Method called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // SQL query to drop the existing notes table if it exists
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery) // Execute the SQL query to drop the table
        onCreate(db) // Recreate the database tables
    }

    // Method to insert a new note into the database
    fun insertNote(note: Note) {
        val db = writableDatabase // Get a writable database instance
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title) // Put the note title into the ContentValues
            put(COLUMN_CONTENT, note.content) // Put the note content into the ContentValues
        }
        db.insert(TABLE_NAME, null, values) // Insert the note into the database
        db.close() // Close the database connection
    }

    // Method to retrieve all notes from the database
    fun getAllNotes(): List<Note> {
        val noteList = mutableListOf<Note>() // Initialize a list to store notes
        val db = readableDatabase // Get a readable database instance
        val query = "SELECT * FROM $TABLE_NAME" // SQL query to select all notes
        val cursor = db.rawQuery(query, null) // Execute the query and get the result as a Cursor

        // Iterate through the cursor to retrieve note data
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) // Get note ID
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)) // Get note title
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)) // Get note content

            val note = Note(id, title, content) // Create a Note object with retrieved data
            noteList.add(note) // Add the note to the list
        }
        cursor.close() // Close the cursor
        db.close() // Close the database connection
        return noteList // Return the list of notes
    }

    // Method to update an existing note in the database
    fun updateNote(note: Note) {
        val db = writableDatabase // Get a writable database instance
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title) // Put the updated note title into the ContentValues
            put(COLUMN_CONTENT, note.content) // Put the updated note content into the ContentValues
        }
        val whereClause = "$COLUMN_ID = ?" // Define the WHERE clause for the update query
        val whereArgs = arrayOf(note.id.toString()) // Define the WHERE clause arguments
        db.update(TABLE_NAME, values, whereClause, whereArgs) // Update the note in the database
        db.close() // Close the database connection
    }

    // Method to retrieve a note by its ID from the database
    fun getNoteByID(noteId: Int): Note {
        val db = readableDatabase // Get a readable database instance
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId" // SQL query to select a note by ID
        val cursor = db.rawQuery(query, null) // Execute the query and get the result as a Cursor
        cursor.moveToFirst() // Move the cursor to the first row

        // Retrieve note data from the cursor
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) // Get note ID
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)) // Get note title
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)) // Get note content

        cursor.close() // Close the cursor
        db.close() // Close the database connection
        return Note(id, title, content) // Return the retrieved note
    }

    // Method to delete a note from the database by its ID
    fun deleteNote(noteId: Int){
        val db = writableDatabase // Get a writable database instance
        val whereClause = "$COLUMN_ID = ?" // Define the WHERE clause for the delete query
        val whereArgs = arrayOf(noteId.toString()) // Define the WHERE clause arguments
        db.delete(TABLE_NAME, whereClause, whereArgs) // Delete the note from the database
        db.close() // Close the database connection
    }

}
