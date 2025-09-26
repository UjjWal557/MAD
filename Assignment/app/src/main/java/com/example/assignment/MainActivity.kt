package com.example.assignment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import android.widget.*
import com.google.android.material.materialswitch.MaterialSwitch


class MainActivity : AppCompatActivity() {

    private lateinit var cipherSpinner: Spinner
    private lateinit var inputText: EditText
    private lateinit var keyInput: EditText
    private lateinit var modeSwitch: MaterialSwitch
    private lateinit var translateButton: Button
    private lateinit var outputText: TextView
    private lateinit var copyButton: Button
    private val cipherOptions = listOf("Caesar", "Vigenere","PlayFair")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        cipherSpinner = findViewById(R.id.cipherSpinner)
        inputText = findViewById(R.id.inputText)
        keyInput = findViewById(R.id.keyInput)
        modeSwitch = findViewById(R.id.modeSwitch)
        translateButton = findViewById(R.id.translateButton)
        outputText = findViewById(R.id.outputText)
        copyButton = findViewById(R.id.copyButton)

        setupSpinner()
        setupTranslateButton()
        setupCopyButton()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cipherOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cipherSpinner.adapter = adapter

        cipherSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = cipherOptions[position]
                keyInput.visibility = if (selected == "Caesar" || selected == "Vigenere" || selected == "PlayFair")
                    View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupTranslateButton() {
        translateButton.setOnClickListener {
            val text = inputText.text.toString()
            val key = keyInput.text.toString()
            val isEncrypt = modeSwitch.isChecked
            val selectedCipher = cipherSpinner.selectedItem.toString()

            val result = when (selectedCipher) {
                "Caesar" -> {
                    val shift = key.toIntOrNull() ?: 0
                    if (isEncrypt) caesarEncrypt(text, shift) else caesarDecrypt(text, shift)
                }
                "Vigenere" -> {
                    if (isEncrypt) vigenereEncrypt(text, key) else vigenereDecrypt(text, key)
                }
                "PlayFair" -> {
                    if (isEncrypt) playfairEncrypt(text,key) else playfairDecrypt(text,key)
                }
                else -> "Cipher not implemented"
            }

            outputText.text = result
        }
    }

    private fun setupCopyButton() {
        copyButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Encrypted Text", outputText.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    // Caesar Cipher Logic
    private fun caesarEncrypt(text: String, shift: Int): String {
        return text.map {
            when {
                it.isUpperCase() -> ((it - 'A' + shift) % 26 + 'A'.code).toChar()
                it.isLowerCase() -> ((it - 'a' + shift) % 26 + 'a'.code).toChar()
                else -> it
            }
        }.joinToString("")
    }

    private fun caesarDecrypt(text: String, shift: Int): String {
        return caesarEncrypt(text, 26 - (shift % 26))
    }

    // Vigenère Cipher Logic
    private fun vigenereEncrypt(text: String, key: String): String {
        val keyRepeat = key.filter { it.isLetter() }.lowercase().repeat((text.length / key.length) + 1)
        return text.mapIndexed { i, c ->
            if (c.isLetter()) {
                val shift = keyRepeat[i] - 'a'
                if (c.isUpperCase()) ((c - 'A' + shift) % 26 + 'A'.code).toChar()
                else ((c - 'a' + shift) % 26 + 'a'.code).toChar()
            } else c
        }.joinToString("")
    }

    private fun vigenereDecrypt(text: String, key: String): String {
        val keyRepeat = key.filter { it.isLetter() }.lowercase().repeat((text.length / key.length) + 1)
        return text.mapIndexed { i, c ->
            if (c.isLetter()) {
                val shift = keyRepeat[i] - 'a'
                if (c.isUpperCase()) ((c - 'A' - shift + 26) % 26 + 'A'.code).toChar()
                else ((c - 'a' - shift + 26) % 26 + 'a'.code).toChar()
            } else c
        }.joinToString("")
    }

    fun playfairEncrypt(plainText: String, key: String): String {
        val matrix = createMatrix(key)
        val pairs = preparePlaintext(plainText)
        return pairs.joinToString("") { (a, b) -> encryptPair(matrix, a, b) }
    }

    // 🔧 Create 5x5 matrix from key
    fun createMatrix(key: String): List<List<Char>> {
        val cleanedKey = key.lowercase().replace('j', 'i')
        val seen = mutableSetOf<Char>()
        val ukey = StringBuilder()

        for (ch in cleanedKey) {
            if (ch in 'a'..'z' && seen.add(ch)) {
                ukey.append(ch)
            }
        }

        for (ch in 'a'..'z') {
            if ((ch == 'j' && 'i' in seen) || !seen.add(ch)) continue
            ukey.append(if (ch == 'j') 'i' else ch)
        }

        return List(5) { i -> List(5) { j -> ukey[5 * i + j] } }
    }

    // 🔧 Prepare plaintext into digraphs
    fun preparePlaintext(pt: String): List<Pair<Char, Char>> {
        val cleaned = pt.lowercase().replace('j', 'i').filter { it in 'a'..'z' }
        val pairs = mutableListOf<Pair<Char, Char>>()
        var i = 0

        while (i < cleaned.length) {
            val a = cleaned[i]
            val b = if (i + 1 < cleaned.length) cleaned[i + 1] else 'x'
            if (a == b) {
                pairs.add(Pair(a, 'x'))
                i += 1
            } else {
                pairs.add(Pair(a, b))
                i += 2
            }
        }

        return pairs
    }

    // 🔧 Find position of character in matrix
    fun position(matrix: List<List<Char>>, ch: Char): Pair<Int, Int> {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == ch) return Pair(i, j)
            }
        }
        throw IllegalArgumentException("Character $ch not found in matrix")
    }

    // 🔧 Encrypt a pair of characters
    fun encryptPair(matrix: List<List<Char>>, a: Char, b: Char): String {
        val (r1, c1) = position(matrix, a)
        val (r2, c2) = position(matrix, b)

        return when {
            r1 == r2 -> "${matrix[r1][(c1 + 1) % 5]}${matrix[r2][(c2 + 1) % 5]}"
            c1 == c2 -> "${matrix[(r1 + 1) % 5][c1]}${matrix[(r2 + 1) % 5][c2]}"
            else -> "${matrix[r1][c2]}${matrix[r2][c1]}"
        }
    }

    fun playfairDecrypt(cipherText: String, key: String): String {
        val matrix = createMatrix(key)
        val pairs = cipherText.lowercase().chunked(2).map { Pair(it[0], it[1]) }
        return pairs.joinToString("") { (a, b) -> decryptPair(matrix, a, b) }
    }

    // 🔧 Decrypt a pair of characters
    fun decryptPair(matrix: List<List<Char>>, a: Char, b: Char): String {
        val (r1, c1) = position(matrix, a)
        val (r2, c2) = position(matrix, b)

        return when {
            r1 == r2 -> "${matrix[r1][(c1 + 4) % 5]}${matrix[r2][(c2 + 4) % 5]}" // shift left
            c1 == c2 -> "${matrix[(r1 + 4) % 5][c1]}${matrix[(r2 + 4) % 5][c2]}" // shift up
            else -> "${matrix[r1][c2]}${matrix[r2][c1]}" // swap columns
        }
    }


}