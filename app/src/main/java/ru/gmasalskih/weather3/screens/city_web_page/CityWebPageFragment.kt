package ru.gmasalskih.weather3.screens.city_web_page

import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.databinding.FragmentCityWebPageBinding

class CityWebPageFragment : Fragment() {

    lateinit var binding: FragmentCityWebPageBinding
    lateinit var args: CityWebPageFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityWebPageBinding.inflate(inflater, container, false)
        args = CityWebPageFragmentArgs.fromBundle(arguments!!)
        binding.textView6.text = args.urlWebPage
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> startActivity(getShareIntent())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent() = ShareCompat
        .IntentBuilder
        .from(activity!!)
        .setText(args.urlWebPage)
        .setType("text/plain")
        .intent


//        return Intent(Intent.ACTION_SEND).apply {
//            type = "text/plain"
//            putExtra(Intent.EXTRA_TEXT, args.urlWebPage)
//        }

}