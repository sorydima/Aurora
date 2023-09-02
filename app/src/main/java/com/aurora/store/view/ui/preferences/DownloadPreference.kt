package com.aurora.store.view.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.aurora.store.R
import com.aurora.store.util.Preferences


class DownloadPreference : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_download, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.pref_app_download)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        val downloadExternalPreference: SwitchPreferenceCompat? =
            findPreference(Preferences.PREFERENCE_DOWNLOAD_EXTERNAL)

        val autoDeletePreference: SwitchPreferenceCompat? =
            findPreference(Preferences.PREFERENCE_AUTO_DELETE)


        downloadExternalPreference?.let { switchPreferenceCompat ->
            switchPreferenceCompat.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    val checked = newValue.toString().toBoolean()
                    autoDeletePreference?.let {
                        if (checked) {
                            it.isEnabled = true
                        } else {
                            it.isEnabled = false
                            it.isChecked = true
                        }
                    }

                    true
                }
        }
    }
}