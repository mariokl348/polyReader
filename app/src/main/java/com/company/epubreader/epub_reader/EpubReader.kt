package com.example.androidepubreader.epubreader

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.folioreader.Config
import com.folioreader.FolioReader
import com.folioreader.model.HighLight
import com.folioreader.model.locators.ReadLocator
import com.folioreader.ui.base.OnSaveHighlight
import com.folioreader.util.AppUtil
import com.folioreader.util.OnHighlightListener
import com.folioreader.util.ReadLocatorListener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class EpubReader(context: Context) : OnHighlightListener, ReadLocatorListener,
    FolioReader.OnClosedListener {

    private val mFolioReader: FolioReader = FolioReader.get()
        .setOnHighlightListener(this)
        .setReadLocatorListener(this)
        .setOnClosedListener(this)
    private val mContext: Context = context
    private var mConfig: Config? = null

    init {
        getHighlightsAndSave()
    }

    fun open(bookPath: String,  themeColor: String) {
        mConfig = AppUtil.getSavedConfig(mContext)
        if (mConfig == null) mConfig = Config()
        mConfig!!.allowedDirection = Config.AllowedDirection.VERTICAL_AND_HORIZONTAL
        mConfig!!.setThemeColorInt(Color.parseColor(themeColor))
        val readLocator: ReadLocator = getLastReadLocator()
        mFolioReader.setReadLocator(readLocator)
        mFolioReader.setConfig(mConfig, true)
            .openBook(bookPath)
    }

    private fun getLastReadLocator(): ReadLocator {
        val jsonString: String = this.loadAssetTextAsString("Locators/LastReadLocators/last_read_locator_1.json")!!
        return ReadLocator.fromJson(jsonString)!!
    }

    fun close() {
        mFolioReader.close()
    }

    private fun loadAssetTextAsString(name: String): String? {
        var `in`: BufferedReader? = null
        try {
            val buf = StringBuilder()
            val `is`: InputStream = mContext.assets.open(name)
            `in` = BufferedReader(InputStreamReader(`is`))
            var str: String? = null
            var isFirst = true
            while ({ str = `in`.readLine(); str }() != null) {
                if (isFirst) isFirst = false else buf.append('\n')
                buf.append(str)
            }
            return buf.toString()
        } catch (e: IOException) {
            Log.e("Reader", "Error opening asset $name")
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    Log.e("Reader", "Error closing asset $name")
                }
            }
        }
        return null
    }

    /*
     * For testing purpose, we are getting dummy highlights from asset. But you can get highlights from your server
     * On success, you can save highlights to FolioReader DB.
     */
    private fun getHighlightsAndSave() {
        Thread(Runnable {
            var highlightList: ArrayList<HighLight?>? = null
            val objectMapper = ObjectMapper()
            try {
                highlightList = objectMapper.readValue(
                    loadAssetTextAsString("highlights/highlights_data.json"),
                    object : TypeReference<List<HighlightData?>?>() {})
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (highlightList == null) {
                mFolioReader.saveReceivedHighLights(highlightList, OnSaveHighlight {
                    //You can do anything on successful saving highlight list
                })
            }
        }).start()
    }

    override fun onHighlight(highlight: HighLight?, type: HighLight.HighLightAction?) {

    }

    override fun saveReadLocator(readLocator: ReadLocator?) {

    }

    override fun onFolioReaderClosed() {

    }
}