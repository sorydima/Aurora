package com.aurora.store.view.ui.preferences

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.aurora.extensions.isTAndAbove
import com.aurora.store.R
import com.aurora.store.util.Preferences
import com.aurora.store.util.save
import java.util.Locale


class UIPreference : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_ui, rootKey)

        findPreference<Preference>("PREFERENCE_APP_LANGUAGE")?.apply {
            if (isTAndAbove()) {
                summary = Locale.getDefault().displayName
                setOnPreferenceClickListener {
                    startActivity(Intent(Settings.ACTION_APP_LOCALE_SETTINGS).apply {
                        data = Uri.parse("package:" + requireContext().packageName)
                    })
                    true
                }
            } else {
                isVisible = false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.pref_ui_title)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        val themePreference: ListPreference? = findPreference(Preferences.PREFERENCE_THEME_TYPE)
        themePreference?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                val themeId = Integer.parseInt(newValue.toString())

                save(Preferences.PREFERENCE_THEME_TYPE, themeId)
                requireActivity().recreate()
                true
            }
        }

        val accentPreference: ListPreference? = findPreference(Preferences.PREFERENCE_THEME_ACCENT)
        accentPreference?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                val accentId = Integer.parseInt(newValue.toString())

                save(Preferences.PREFERENCE_THEME_ACCENT, accentId)
                requireActivity().recreate()
                true
            }
        }
    }
}
