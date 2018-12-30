package software.egger.libgolandroid

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import software.egger.libgol.CommonCanvas
import software.egger.libgol.CommonColor
import software.egger.libgol.CommonPaint
import software.egger.libgol.Rectangle

fun Rectangle.asRectF() = RectF(
        this.left.toFloat(),
        this.top.toFloat(),
        this.right.toFloat(),
        this.bottom.toFloat()
)

fun CommonColor.asColorInt() = Color.rgb(
        this.red,
        this.green,
        this.blue)

fun CommonPaint.asPaint() = Paint().apply {
    color = this@asPaint.color.asColorInt()
}

class AndroidCanvas : CommonCanvas {

    var canvas: Canvas? = null

    override fun drawRect(rectangle: Rectangle, commonPaint: CommonPaint) {
        val canvas = requireNotNull(canvas)
        canvas.drawRect(rectangle.asRectF(), Paint().apply { color = Color.DKGRAY; style = Paint.Style.FILL })
    }

}