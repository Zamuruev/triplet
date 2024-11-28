package com.triplet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.triplet.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataStore: DataStore<Preferences>

    private val fileName = "characters.txt"
    private val internalFileName = "internal_characters.txt"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)

        dataStore = DataStoreManager.getDataStore()

        loadSettings()

        binding.fontSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.fontSizeLabel2.text = "Font size: ${progress + 10}sp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Сохранение настроек
        binding.saveSettingsBtn.setOnClickListener {
            saveSettings()
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }

        checkFileStatus()

        binding.deleteFileButton.setOnClickListener {
            deleteFile()
        }

        binding.restoreFileButton.setOnClickListener {
            restoreFile()
        }

        return binding.root
    }

    private fun loadSettings() {
        val username = sharedPreferences.getString("username", "Default User")
        binding.nicknameInput.setText(username)

        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                val fontSize = preferences[intPreferencesKey("font_size")] ?: 16
                binding.fontSizeSeekBar.progress = fontSize - 10
            }
        }
    }

    private fun saveSettings() {
        val username = binding.nicknameInput.text.toString()
        sharedPreferences.edit().putString("username", username).apply()

        val fontSize = binding.fontSizeSeekBar.progress + 10
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey("font_size")] = fontSize
            }
        }
    }

    private fun checkFileStatus() {
        val file = File(requireContext().getExternalFilesDir(null), fileName)
        val internalFile = File(requireContext().filesDir, internalFileName)

        if (file.exists()) {
            binding.fileStatusText.text = "File exists in external storage"

            binding.deleteFileButton.visibility = View.VISIBLE

            if (internalFile.exists()) {
                binding.restoreFileButton.visibility = View.GONE
            }

        } else {
            binding.fileStatusText.text = "File does not exist in external storage"
            binding.deleteFileButton.visibility = View.GONE
            binding.restoreFileButton.visibility = View.GONE
        }
    }

    private fun deleteFile() {
        val file = File(requireContext().getExternalFilesDir(null), fileName)
        if (file.exists()) {
            saveFileToInternalStorage(file)
            file.delete()
            binding.fileStatusText.text = "File deleted from external storage"
            binding.deleteFileButton.visibility = View.GONE
            binding.restoreFileButton.visibility = View.VISIBLE
        }
    }

    private fun saveFileToInternalStorage(file: File) {
        try {
            val inputStream = FileInputStream(file)
            val internalFile = File(requireContext().filesDir, internalFileName)
            val outputStream = FileOutputStream(internalFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun restoreFile() {
        val internalFile = File(requireContext().filesDir, internalFileName)
        if (internalFile.exists()) {
            val file = File(requireContext().getExternalFilesDir(null), fileName)
            try {
                val inputStream = FileInputStream(internalFile)
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
                inputStream.close()
                outputStream.close()
                binding.fileStatusText.text = "File restored to external storage"
                binding.restoreFileButton.visibility = View.GONE
                binding.deleteFileButton.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

