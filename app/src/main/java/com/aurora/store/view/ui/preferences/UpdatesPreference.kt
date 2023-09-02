package com.aurora.store.view.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.aurora.store.R
import com.aurora.store.data.work.UpdateWorker
import com.aurora.store.util.Preferences.PREFERENCE_UPDATES_CHECK

class UpdatesPreference : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_updates, rootKey)

        findPreference<SwitchPreferenceCompat>(PREFERENCE_UPDATES_CHECK)
            ?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue.toString().toBoolean()) {
                    UpdateWorker.scheduleAutomatedCheck(requireContext())
                } else {
                    UpdateWorker.cancelAutomatedCheck(requireContext())
                }
                true
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.title_updates)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}