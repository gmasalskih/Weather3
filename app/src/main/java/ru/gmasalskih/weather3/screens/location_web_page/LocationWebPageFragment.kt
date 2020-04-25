package ru.gmasalskih.weather3.screens.location_web_page

import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.databinding.FragmentLocationWebPageBinding

class LocationWebPageFragment : Fragment() {

    lateinit var binding: FragmentLocationWebPageBinding
    lateinit var args: LocationWebPageFragmentArgs
    lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationWebPageBinding.inflate(inflater, container, false)
        webView = binding.webPageWeather
        arguments?.let {
            args = LocationWebPageFragmentArgs.fromBundle(it)
        }
        webView.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(args.urlWebPage)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                activity?.let {
                    startActivity(getShareIntent(it))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(activity: FragmentActivity) = ShareCompat
        .IntentBuilder
        .from(activity)
        .setText(args.urlWebPage)
        .setType("text/plain")
        .intent
}