package com.aurora.store.view.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.aurora.extensions.runOnUiThread
import com.aurora.extensions.toast
import com.aurora.store.R
import com.aurora.store.util.Preferences

class NetworkPreference : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_network, rootKey)

        val insecureAnonymous: Preference? =
            findPreference(Preferences.PREFERENCE_INSECURE_ANONYMOUS)

        insecureAnonymous?.let {
            it.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    runOnUiThread {
                        requireContext().toast(R.string.insecure_anonymous_apply)
                    }
                    false
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.pref_network_title)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}