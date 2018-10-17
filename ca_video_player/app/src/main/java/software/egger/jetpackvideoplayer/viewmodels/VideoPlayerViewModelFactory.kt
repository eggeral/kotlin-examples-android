package software.egger.jetpackvideoplayer.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import software.egger.jetpackvideoplayer.data.VideoRepository


class VideoPlayerViewModelFactory(
        private val repository: VideoRepository,
        private val currentVideo: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = VideoPlayerViewModel(repository, currentVideo) as T
}
