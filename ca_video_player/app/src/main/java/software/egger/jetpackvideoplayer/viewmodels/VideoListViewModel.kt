package software.egger.jetpackvideoplayer.viewmodels

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import software.egger.jetpackvideoplayer.data.Video
import software.egger.jetpackvideoplayer.data.VideoRepository

class VideoListViewModel(repository: VideoRepository) : ViewModel() {

    private val videoList = MediatorLiveData<List<Video>>()

    init {
        videoList.addSource(repository.allVideos, videoList::setValue)
    }

    fun getAllVideos() = videoList

}
