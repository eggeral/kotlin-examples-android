package software.egger.jetpackvideoplayer.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import software.egger.jetpackvideoplayer.data.Video
import software.egger.jetpackvideoplayer.data.VideoRepository

class VideoPlayerViewModel(private val repository: VideoRepository, currentVideo: String) : ViewModel() {

    enum class PlayerState { Play, Pause }

    val currentVideo = MutableLiveData<String>()
    val playerState = MutableLiveData<PlayerState>()

    init {

        this.currentVideo.value = currentVideo

    }

    private fun findNext(videos: List<Video>?): Video? {
        if (videos == null || videos.isEmpty()) return null

        val videoIterator = videos.iterator()

        while (videoIterator.hasNext()) {
            val video = videoIterator.next()
            if (video.file.absolutePath == currentVideo.value)
                break
        }

        return if (videoIterator.hasNext())
            videoIterator.next()
        else
            videos.first()

    }

    fun nextVideo() {

        val next = findNext(repository.allVideos.value)
        if (next != null) currentVideo.value = next.file.absolutePath

    }

    fun previousVideo() {

        val previous = findNext(repository.allVideos.value?.asReversed())
        if (previous != null) currentVideo.value = previous.file.absolutePath

    }

}
