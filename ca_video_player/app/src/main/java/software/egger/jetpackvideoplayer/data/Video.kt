package software.egger.jetpackvideoplayer.data

import android.graphics.Bitmap
import java.io.File

data class Video(
        val file: File,
        val thumbnail: Bitmap,
        val name: String
)