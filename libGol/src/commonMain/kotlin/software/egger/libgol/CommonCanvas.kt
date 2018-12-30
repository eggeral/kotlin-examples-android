package software.egger.libgol

interface CommonCanvas {
    fun drawRect(rectangle: Rectangle, commonPaint: CommonPaint)

}

class Rectangle(val left: Double, val top: Double, val right: Double, val bottom: Double)

enum class Style {
    Fill
}

class CommonPaint {
    var color = CommonColor(0x00, 0x00, 0x00)
    var style = Style.Fill
    var strokeWidth = 1.0
}

data class CommonColor(val red: Int, val green: Int, val blue: Int, val alpha: Float = 1.0f)

