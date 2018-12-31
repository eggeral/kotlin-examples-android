package software.egger.libgol

import kotlinx.cinterop.cValuesOf
import platform.CoreGraphics.*
import platform.UIKit.UIGraphicsGetCurrentContext

fun Rectangle.asCGRect() = CGRectMake(
        x = this.left,
        y = this.top,
        width = this.right - this.left,
        height = this.bottom - this.top
)

class IOSCanvas : CommonCanvas {

    override fun drawRect(rectangle: Rectangle, commonPaint: CommonPaint) {
        val context = UIGraphicsGetCurrentContext() ?: return


        val rgb = CGColorSpaceCreateDeviceRGB()
        val values = cValuesOf (0.0, 1.0, 0.0, 1.0)
        val color = CGColorCreate(rgb, values)

        CGContextSetFillColorWithColor(context, color)

        CGContextFillRect(context, rectangle.asCGRect())

    }

}