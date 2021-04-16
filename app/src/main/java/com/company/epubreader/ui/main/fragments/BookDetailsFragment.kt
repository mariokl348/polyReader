package com.company.epubreader.ui.main.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.company.epubreader.R
import com.company.epubreader.ui.Constants
import com.download.library.DownloadImpl
import com.download.library.DownloadListenerAdapter
import com.download.library.Extra
import com.example.androidepubreader.epubreader.EpubReader
import com.squareup.picasso.Picasso
import com.thin.downloadmanager.*
import kotlinx.android.synthetic.main.fragment_book_details.*
import java.io.File
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var REQUEST_CODE_WRITE_STORAGE_PERMISION = 105
    var REQUEST_CODE_WRITE_STORAGE_PERMISION_AUDIO = 107

    val REQUIRED_SDK_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
    private val DOWNLOAD_THREAD_POOL_SIZE = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val epubReader: EpubReader by lazy { EpubReader(requireContext()) }

    private lateinit var epub: String
    private lateinit var audio: String
    private var idBook: Int = 0

    private var downloadId1 = 0
    private val downloadManager: ThinDownloadManager =
        ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE)
    private var statusToast: Toast? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arguments?.let {

            idBook = it.getInt("id")


            val title = it.getString("title")
            val author = it.getString("author")
            val image = it.getString("image")
            val summary = it.getString("summary")
            val year = it.getInt("year")
            val publisher = it.getString("publisher")
            val narrator = it.getString("narrator")
            val category = it.getString("category")
            epub = it.getString("epub")
           audio = it.getString("audio")



            Picasso.get().load(Constants.BASE_URL + image).into(bookImage)

            titleTxt.text = title
            authorTxt.text = author

            summaryTxt.text = summary
            authorReal.text = author
            yearTxt.text = year.toString()
            publisherTxt.text = publisher
            narratorTxt.text = narrator
            categoryTxt.text = category

            readImg.setOnClickListener {

                checkPermission()

            }

            listenImg.setOnClickListener {

                checkPermissionAudio()

            }


        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_WRITE_STORAGE_PERMISION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile()
                }
            }

            REQUEST_CODE_WRITE_STORAGE_PERMISION_AUDIO -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadAudio()
                }

            }

        }
    }


    private fun downloadFile() {


        val folder = File(Environment.getExternalStorageDirectory().toString() + "/" + "Epub")
        if (!folder.exists()) {
            Log.i("askjbgver", "create")
            folder.mkdirs()
        } else {
            Log.i("askjbgver", "not")
        }


        val fileName = "$idBook _epub_file.epub"


        val fileExisitencePath = File(
            Environment.getExternalStorageDirectory().toString() + "/" + "Epub/" + fileName
        )
        if (!fileExisitencePath.exists()) {

            val myDownloadStatusListener = MyDownloadDownloadStatusListenerV1(
                requireActivity(),
                idBook,
                epubReader,
                statusToast, MEDIA_TYPE.EPUB, requireView()
            )

            val urlOfTheFile = Constants.BASE_URL + epub
            val retryPolicy: RetryPolicy = DefaultRetryPolicy()

            val downloadUri: Uri = Uri.parse(urlOfTheFile)

            val destinationUri: Uri = Uri.parse(folder.path + "/" + fileName)


            val downloadRequest1 = DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setRetryPolicy(retryPolicy)
                .setDownloadContext("Download1")
                .setStatusListener(myDownloadStatusListener)


            if (downloadManager.query(downloadId1) == DownloadManager.STATUS_NOT_FOUND) {

                Toast.makeText(requireContext(), "Download started", Toast.LENGTH_SHORT).show()

                downloadId1 = downloadManager.add(downloadRequest1)
                Log.i("asjdh", "Download starting:")
            }


        } else {

            Log.i("askhbvr", "already exist open file")

            epubReader.open(fileExisitencePath.path, "#32a852")
        }


    }


    private fun checkPermission() {
        val missingPermissions = arrayListOf<String>()
        for (permission in REQUIRED_SDK_PERMISSIONS) {
            val result = ContextCompat.checkSelfPermission(requireContext(), permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission)
            }
        }

        if (!missingPermissions.isEmpty()) {
            val permissions = missingPermissions.toTypedArray()
            requestPermissions(permissions, REQUEST_CODE_WRITE_STORAGE_PERMISION)

        } else {

            val grantResults = IntArray(REQUIRED_SDK_PERMISSIONS.size)

            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)

            onRequestPermissionsResult(
                REQUEST_CODE_WRITE_STORAGE_PERMISION, REQUIRED_SDK_PERMISSIONS,
                grantResults
            )

        }


    }

    private fun checkPermissionAudio() {
        val missingPermissions = arrayListOf<String>()
        for (permission in REQUIRED_SDK_PERMISSIONS) {
            val result = ContextCompat.checkSelfPermission(requireContext(), permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission)
            }
        }

        if (!missingPermissions.isEmpty()) {
            val permissions = missingPermissions.toTypedArray()
            requestPermissions(permissions, REQUEST_CODE_WRITE_STORAGE_PERMISION_AUDIO)

        } else {

            val grantResults = IntArray(REQUIRED_SDK_PERMISSIONS.size)

            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)

            onRequestPermissionsResult(
                REQUEST_CODE_WRITE_STORAGE_PERMISION_AUDIO, REQUIRED_SDK_PERMISSIONS,
                grantResults
            )

        }


    }


    class MyDownloadDownloadStatusListenerV1(
        private val activity: FragmentActivity,
        private val id_book: Int,
        private val epubReader: EpubReader,
        private var downdloadToast: Toast?,
        private val type: MEDIA_TYPE,
        private val view: View
    ) : DownloadStatusListenerV1 {


        override fun onDownloadComplete(downloadRequest: DownloadRequest?) {


            downloadRequest?.let {
                val id = downloadRequest.downloadId

                Log.i("asjdh", "Download complete:" + id)

                downdloadToast?.cancel()
                downdloadToast = Toast.makeText(activity, "Download complete", Toast.LENGTH_SHORT)
                downdloadToast?.show()
                downdloadToast?.cancel()

                if (type == MEDIA_TYPE.EPUB) {
                    val fileName = "$id_book _epub_file.epub"


                    val fileExisitencePath = File(
                        Environment.getExternalStorageDirectory()
                            .toString() + "/" + "Epub/" + fileName
                    )

                    if (fileExisitencePath.exists()) {
                        epubReader.open(fileExisitencePath.path, "#32a852")
                    }
                } else {

                    val fileName = "$id_book _epub_file.mp3"


                    val fileExisitencePath = File(
                        Environment.getExternalStorageDirectory()
                            .toString() + "/" + "Epub/" + fileName
                    )

                    if (fileExisitencePath.exists()) {

                        Log.i("sahjbdwrv", "1:" + fileExisitencePath)

                        val bundle =
                            bundleOf("audio" to Uri.fromFile(fileExisitencePath).toString())

                        Navigation.findNavController(view).navigate(R.id.toPlayer, bundle)
                    }

                }


            }


        }

        override fun onDownloadFailed(
            downloadRequest: DownloadRequest?,
            errorCode: Int,
            errorMessage: String?
        ) {
            downloadRequest?.let {
                val id = downloadRequest.downloadId
                Log.i("asjdh", "Download failded:" + id)
                downdloadToast?.cancel()
                downdloadToast = Toast.makeText(activity, "Download failed", Toast.LENGTH_SHORT)
                downdloadToast?.show()
            }

        }

        override fun onProgress(
            downloadRequest: DownloadRequest?,
            totalBytes: Long,
            downloadedBytes: Long,
            progress: Int
        ) {


            downloadRequest?.let {
                val id = downloadRequest.downloadId
                Log.i("asjdh", "Download progress:" + id)

                downdloadToast?.cancel()
                downdloadToast = Toast.makeText(activity, "Downloading", Toast.LENGTH_SHORT)
                downdloadToast?.show()


            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        epubReader.close()
        downloadManager.release()
    }

    fun downloadAudio() {

        val folder = File(Environment.getExternalStorageDirectory().toString() + "/" + "Epub")
        if (!folder.exists()) {
            Log.i("askjbgver", "create")
            folder.mkdirs()
        } else {
            Log.i("askjbgver", "not")
        }


        val fileName = "$idBook _epub_file.mp3"


        val fileExisitencePath = File(
            Environment.getExternalStorageDirectory().toString() + "/" + "Epub/" + fileName
        )
        if (!fileExisitencePath.exists()) {


            val myDownloadStatusListener = MyDownloadDownloadStatusListenerV1(
                requireActivity(),
                idBook,
                epubReader,
                statusToast, MEDIA_TYPE.AUDIO, requireView()
            )

            val urlOfTheFile = Constants.BASE_URL + audio
            val retryPolicy: RetryPolicy = DefaultRetryPolicy()

            val downloadUri: Uri = Uri.parse(urlOfTheFile)


            val destinationUri: Uri = Uri.parse(folder.path + "/" + fileName )


            val downloadRequest1 = DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setRetryPolicy(retryPolicy)
                .setDownloadContext("Download1")
                .setStatusListener(myDownloadStatusListener)




            if (downloadManager.query(downloadId1) == DownloadManager.STATUS_NOT_FOUND) {

                Toast.makeText(requireContext(), "Download started", Toast.LENGTH_SHORT).show()

                downloadId1 = downloadManager.add(downloadRequest1)
                Log.i("asjdh", "Download starting:")
            }


        } else {

            Log.i("sahjbdwrv", "2:" + fileExisitencePath)


            val bundle = bundleOf("audio" to Uri.fromFile(fileExisitencePath).toString())
            findNavController().navigate(R.id.toPlayer, bundle)
        }


    }

    enum class MEDIA_TYPE {
        EPUB, AUDIO
    }


}