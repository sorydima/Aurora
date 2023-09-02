package com.aurora.store.view.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.aurora.store.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)

        findPreference<Preference>("pref_filter")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.filterPreference)
            true
        }
        findPreference<Preference>("pref_install")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.installationPreference)
            true
        }
        findPreference<Preference>("pref_ui")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.UIPreference)
            true
        }
        findPreference<Preference>("pref_download")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.downloadPreference)
            true
        }
        findPreference<Preference>("pref_network")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.networkPreference)
            true
        }
        findPreference<Preference>("pref_updates")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.updatesPreference)
            true
        }
        findPreference<Preference>("pref_advanced")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.advancedPreference)
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.title_settings)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}
