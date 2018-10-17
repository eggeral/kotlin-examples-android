package software.egger.jetpackvideoplayer.adapters

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v4.media.BaseMediaPlayer
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import software.egger.jetpackvideoplayer.R
import software.egger.jetpackvideoplayer.viewmodels.VideoPlayerViewModel

@BindingAdapter("imageFromBitmap")
fun imageFromBitmap(view: ImageView, bitmap: Bitmap?) {

    if (bitmap == null) return

    Glide.with(view.context)
            .load(bitmap)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
}


@BindingAdapter("iconFromPlayerState")
fun iconFromPlayerState(button: Button, playerState: VideoPlayerViewModel.PlayerState?) {

    if (playerState == null) return

    when (playerState) {

        VideoPlayerViewModel.PlayerState.Pause -> button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0)
        VideoPlayerViewModel.PlayerState.Play -> button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause, 0, 0, 0)

    }

}

@BindingAdapter("videoPathFromCurrentVideo")
fun videoPathFromCurrentVideo(videoView: VideoView, currentVideo: String?) {

    if (currentVideo == null) return

    videoView.setVideoPath(currentVideo)
    videoView.start()

}

