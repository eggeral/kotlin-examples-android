package software.egger.jetpackvideoplayer.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import software.egger.jetpackvideoplayer.data.VideoRepository


class VideoListViewModelFactory(
        private val repository: VideoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = VideoListViewModel(repository) as T
}
