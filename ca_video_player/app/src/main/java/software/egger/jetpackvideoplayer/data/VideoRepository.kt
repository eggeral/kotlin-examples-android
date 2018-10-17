package software.egger.jetpackvideoplayer.data

import android.arch.lifecycle.MutableLiveData
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import software.egger.jetpackvideoplayer.utilities.runOnIoThread
import java.io.File

object VideoRepository {

    val allVideos = MutableLiveData<List<Video>>()
    private val supportedFormats = hashSetOf("3gp", "mp4", "ts", "webm", "mkv")

    private fun walkDirectory(dir: File, foundVideos: MutableList<Video>) {

        val fileList = dir.listFiles() ?: return

        for (file in fileList) {

            if (file.isDirectory) {
                walkDirectory(file, foundVideos)
            } else {
                if (supportedFormats.contains(file.extension)) {
                    val thumbnail = ThumbnailUtils.createVideoThumbnail(file.absolutePath, MediaStore.Images.Thumbnails.MINI_KIND)
                    if (thumbnail != null) {
                        foundVideos.add(Video(file, thumbnail, file.nameWithoutExtension))
                    }
                }
            }
        }

    }

    fun updateVideoFiles() {
        if (Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED) {

            val dir = Environment.getExternalStorageDirectory()
            runOnIoThread {

                val foundVideos = mutableListOf<Video>()
                walkDirectory(dir, foundVideos)
                allVideos.postValue(foundVideos)

            }

        }
    }

}
