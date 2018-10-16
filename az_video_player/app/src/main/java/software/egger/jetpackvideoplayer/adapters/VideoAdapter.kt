package software.egger.jetpackvideoplayer.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import software.egger.jetpackvideoplayer.data.Video
import software.egger.jetpackvideoplayer.databinding.ListItemVideoBinding
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import software.egger.jetpackvideoplayer.R
import software.egger.jetpackvideoplayer.VideoPlayerFragment
import java.io.File


class VideoAdapter : ListAdapter<Video, VideoAdapter.ViewHolder>(VideoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        holder.apply {
            bind(createOnClickListener(video.file), video)
            itemView.tag = video
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(file: File): View.OnClickListener {

        val bundle = bundleOf(
                VideoPlayerFragment.argFilename to file.absolutePath
        )

        return Navigation.createNavigateOnClickListener(
                R.id.action_video_list_fragment_to_video_player_fragment, bundle)
    }

    class ViewHolder(private val binding: ListItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Video) {
            binding.apply {
                clickListener = listener
                video = item
                executePendingBindings()
            }
        }

    }
}