package com.company.epubreader.ui.main.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.company.epubreader.R
import com.company.epubreader.ui.Constants.BASE_URL
import com.company.epubreader.ui.GridSpacingItemDecoration
import com.company.epubreader.ui.adapters.BooksAdapter
import com.company.epubreader.ui.models.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vishnusivadas.advanced_httpurlconnection.FetchData
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.reflect.Type


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sorts = arrayOf("Latest", "Oldest")

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sorts,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerSort.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCategory.adapter = adapter
        }


        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {

            val fetchData = FetchData(BASE_URL + "get_ebooks.php")
            if (fetchData.startFetch()) {
                if (fetchData.onComplete()) {
                    val result = fetchData.result
                    val gson = Gson()

                    val listType: Type = object : TypeToken<ArrayList<Book>>() {}.type

                    val books = gson.fromJson<ArrayList<Book>>(result, listType)

                    val bookAdapter = BooksAdapter(books)

                    bookRv.adapter = bookAdapter

                    bookRv.addItemDecoration(GridSpacingItemDecoration(2, 20, true, 0))

                    bookRv.layoutManager = GridLayoutManager(requireContext(), 2)

                    bookAdapter.onItemClick = { book ->

                        val bundle = bundleOf(
                            "id" to book.id,
                            "title" to book.title,
                            "author" to book.author,
                            "image" to book.image,
                            "summary" to book.summary,
                            "year" to book.year,
                            "publisher" to book.publisher,
                            "narrator" to book.narrator,
                            "category" to book.category,
                            "epub" to book.epub,
                            "audio" to book.audio
                        )

                        findNavController().navigate(R.id.toBookDetils, bundle)
                    }


                }
            }

        }


        val books = arrayListOf<Book>()


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}