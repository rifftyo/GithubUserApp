package com.rifftyo.githubuserapp.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.rifftyo.githubuserapp.ViewModel.ViewModelFactory
import com.rifftyo.githubuserapp.ViewModel.ViewModelTheme
import com.rifftyo.githubuserapp.databinding.ActivityThemeBinding
import com.rifftyo.githubuserapp.utils.SettingPreferences
import com.rifftyo.githubuserapp.utils.dataStore

class ThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModelTheme = ViewModelProvider(this, ViewModelFactory(pref)).get(ViewModelTheme::class.java)

        viewModelTheme.getThemeSettings().observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked= true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener {_: CompoundButton?, isChecked: Boolean ->
            viewModelTheme.saveThemeSetting(isChecked)
        }
    }
}