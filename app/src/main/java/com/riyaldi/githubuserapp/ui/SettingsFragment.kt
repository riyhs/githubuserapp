package com.riyaldi.githubuserapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.riyaldi.githubuserapp.R
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var reminderPreference: SwitchPreferenceCompat
    private val title = "Github User App Reminder"
    private val message = "Hei!, come to search github user account."


    //notification
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "reminder channel"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        init()
        setSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        REMINDER = resources.getString(R.string.reminder_key)
        reminderPreference = findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            setNotification()
        } else {
            Toast.makeText(context, "Off", Toast.LENGTH_LONG).show()
        }
    }

    private fun setNotification() {
        val intent = Intent(activity, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(activity)
            .addParentStack(MainActivity::class.java)
            .addNextIntent(intent)
            .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = context?.let { NotificationCompat.Builder(it, CHANNEL_ID) }
            ?.setContentIntent(pendingIntent)
            ?.setContentTitle(title)
            ?.setSmallIcon(R.drawable.ic_notifications)
            ?.setContentText(message)
            ?.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            ?.setSound(alarmSound)
            ?.setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder?.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder?.build()
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == REMINDER) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)

            val state : Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(REMINDER, false)

            setReminder(state)

            Toast.makeText(context, state.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sh.getBoolean(REMINDER, false)
    }
}