package com.aurora.store.view.ui.account

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aurora.store.R
import com.aurora.store.data.event.BusEvent
import com.aurora.store.databinding.FragmentGoogleBinding
import com.aurora.store.util.AC2DMUtil
import com.aurora.store.viewmodel.auth.AuthViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GoogleFragment : Fragment(R.layout.fragment_google) {

    private val args: GoogleFragmentArgs by navArgs()
    private val viewModel: AuthViewModel by activityViewModels()

    companion object {
        const val EMBEDDED_SETUP_URL =
            "https://accounts.google.com/EmbeddedSetup/identifier?flowName=EmbeddedSetupAndroid"
        const val AUTH_TOKEN = "oauth_token"
        private const val JS_SCRIPT =
            "(function() { return document.getElementById('profileIdentifier').innerHTML; })();"
    }

    private var _binding: FragmentGoogleBinding? = null
    private val binding: FragmentGoogleBinding
        get() = _binding!!

    private val cookieManager = CookieManager.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGoogleBinding.bind(view)

        binding.webview.apply {
            cookieManager.removeAllCookies(null)
            cookieManager.acceptThirdPartyCookies(this)
            cookieManager.setAcceptThirdPartyCookies(this, true)

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    if (newProgress != 0) {
                        binding.progressBar.also {
                            it.isVisible = newProgress < 100
                            it.isIndeterminate = false
                            it.max = 100
                            it.progress = newProgress
                        }
                    } else {
                        binding.progressBar.isIndeterminate = true
                    }
                }
            }

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    val cookies = CookieManager.getInstance().getCookie(url)
                    // cookies can be null if there is an error
                    if (cookies != null) {
                        val cookieMap = AC2DMUtil.parseCookieString(cookies)
                        if (cookieMap.isNotEmpty() && cookieMap[AUTH_TOKEN] != null) {
                            val oauthToken = cookieMap[AUTH_TOKEN]
                            evaluateJavascript(JS_SCRIPT) {
                                val email = it.replace("\"".toRegex(), "")
                                viewModel.buildAuthData(view.context, email, oauthToken)
                            }
                        }
                    }
                }
            }

            settings.apply {
                allowContentAccess = true
                databaseEnabled = true
                domStorageEnabled = true
                javaScriptEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) safeBrowsingEnabled = false
            }
            loadUrl(EMBEDDED_SETUP_URL)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceived(event: BusEvent) {
        when (event) {
            is BusEvent.GoogleAAS -> {
                if (event.success) {
                    viewModel.buildGoogleAuthData(event.email, event.aasToken)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toast_aas_token_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }

                when (args.destination) {
                    R.id.splashFragment -> {
                        findNavController().navigate(
                            GoogleFragmentDirections.actionGoogleFragmentToSplashFragment()
                        )
                    }

                    R.id.accountFragment -> {
                        findNavController().navigate(
                            GoogleFragmentDirections.actionGoogleFragmentToAccountFragment()
                        )
                    }
                }
            }

            else -> {}
        }
    }
}
