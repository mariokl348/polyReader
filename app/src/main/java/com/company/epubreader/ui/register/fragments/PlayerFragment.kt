package com.company.epubreader.ui.register.fragments

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.company.epubreader.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment(R.layout.fragment_player) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var exoplayerView: SimpleExoPlayerView? = null
    private var exoplayer: SimpleExoPlayer? = null
    private var playbackStateBuilder: PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {


            exoplayerView = requireView().findViewById(R.id.exoPlayer)


            val url = it.getString("audio")

            if(url.isNullOrEmpty()){

            } else{

                Log.i("asdkhbvw",":"+url)
                initializePlayer(url)
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
         * @return A new instance of fragment PlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initializePlayer(url: String) {
        val trackSelector = DefaultTrackSelector()
        exoplayer = ExoPlayerFactory.newSimpleInstance(requireContext(), trackSelector)
        exoplayerView?.player = exoplayer

        val userAgent = Util.getUserAgent(requireContext(), "Exo")
        val mediaUri = Uri.parse(url)
        val mediaSource = ExtractorMediaSource(
            mediaUri,
            DefaultDataSourceFactory(requireContext(), userAgent),
            DefaultExtractorsFactory(),
            null,
            null
        )

        exoplayer?.prepare(mediaSource)

        val componentName = ComponentName(requireContext(), "Exo")
        mediaSession = MediaSessionCompat(requireContext(), "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(
            PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                    PlaybackStateCompat.ACTION_FAST_FORWARD
        )

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    override fun onDestroy() {
        super.onDestroy()
        exoplayer?.release()
    }


}

