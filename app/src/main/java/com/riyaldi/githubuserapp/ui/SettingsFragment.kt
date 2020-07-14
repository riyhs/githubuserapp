package com.riyaldi.githubuserapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.services.AlarmReceiver
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminderPreference: SwitchPreferenceCompat
    private lateinit var REMINDER: String
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        alarmReceiver = AlarmReceiver()

        init()
        setSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == REMINDER) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)

            val state : Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(REMINDER, false)

            setReminder(state)
        }
    }

    private fun init() {
        REMINDER = resources.getString(R.string.reminder_key)
        reminderPreference = findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let { alarmReceiver.setRepeatingAlarm(it) }
            Toast.makeText(context, state.toString(), Toast.LENGTH_SHORT).show()
        } else {
            context?.let { alarmReceiver.cancelAlarm(it) }
            Toast.makeText(context, state.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sh.getBoolean(REMINDER, false)
    }
}