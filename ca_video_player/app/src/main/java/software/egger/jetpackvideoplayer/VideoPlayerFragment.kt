package software.egger.jetpackvideoplayer

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import software.egger.jetpackvideoplayer.data.VideoRepository
import software.egger.jetpackvideoplayer.databinding.FragmentVideoPlayerBinding
import software.egger.jetpackvideoplayer.viewmodels.VideoPlayerViewModel
import software.egger.jetpackvideoplayer.viewmodels.VideoPlayerViewModel.PlayerState.*
import software.egger.jetpackvideoplayer.viewmodels.VideoPlayerViewModelFactory
import android.media.AudioManager

class VideoPlayerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val filename = requireNotNull(arguments).getString(argFilename)!!
        val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val factory = VideoPlayerViewModelFactory(VideoRepository, filename)
        val videoPlayerViewModel = ViewModelProviders.of(this, factory)
                .get(VideoPlayerViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentVideoPlayerBinding>(
                inflater, R.layout.fragment_video_player, container, false).apply {
            viewModel = videoPlayerViewModel
            playPauseClickListener = View.OnClickListener { _ ->
                val playerState = videoPlayerViewModel.playerState
                when (playerState.value) {
                    Play -> {
                        if (videoView.canPause()) {
                            playerState.value = Pause
                            videoView.pause()
                        }
                    }
                    Pause -> {
                        videoView.start()
                        playerState.value = Play
                    }
                }
            }

            volumeDownClickListener = View.OnClickListener { _ ->
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
            }

            volumeUpClickListener = View.OnClickListener { _ ->
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
            }

            nextClickListener = View.OnClickListener {_ ->
                videoPlayerViewModel.nextVideo()
                videoPlayerViewModel.playerState.value = Play
            }

            previousClickListener = View.OnClickListener {_ ->
                videoPlayerViewModel.previousVideo()
                videoPlayerViewModel.playerState.value = Play
            }

            videoPlayerViewModel.playerState.value = Play
            setLifecycleOwner(this@VideoPlayerFragment)
        }

        return binding.root
    }

    companion object {
        const val argFilename = "filename"
    }

}