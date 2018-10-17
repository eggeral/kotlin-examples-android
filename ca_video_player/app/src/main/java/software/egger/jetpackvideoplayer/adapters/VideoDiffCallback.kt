package software.egger.jetpackvideoplayer.adapters

import android.support.v7.util.DiffUtil
import software.egger.jetpackvideoplayer.data.Video

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {

    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.file == newItem.file
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }

}