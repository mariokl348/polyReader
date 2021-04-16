package com.company.epubreader.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.epubreader.R
import com.example.androidepubreader.epubreader.EpubReader
import com.folioreader.FolioReader
import kotlinx.android.synthetic.main.activity_open.*

class OpenActivity : AppCompatActivity() {
    private lateinit var mReader: EpubReader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        buttonGo.setOnClickListener{



            mReader= EpubReader(this)

            mReader.open("file:///android_asset/Alices_Adventures_in_Wonderland.epub","#32a852")



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mReader.close()
    }
}