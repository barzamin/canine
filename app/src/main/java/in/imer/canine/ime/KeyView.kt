package `in`.imer.canine.ime

import `in`.imer.canine.R
import android.content.Context
import android.graphics.*
import android.graphics.drawable.PaintDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider

class KeyView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var backgroundDrawable = PaintDrawable().apply {
        setCornerRadius(resources.getDimension(R.dimen.key_borderRadius))
        setTint(Color.rgb(0.3f, 0.0f, 0.4f))
    }

    init {
        elevation = 6.0f
        background = backgroundDrawable;
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        outlineProvider = KeyViewOutline(w, h)
    }

    /**
     * Automatically sets the text size of [boxPaint] for given [text] so it fits within the given
     * bounds.
     *
     * Implementation based on this blog post by Lucas (SketchingDev), written on Aug 20, 2015
     *  https://sketchingdev.co.uk/blog/resizing-text-to-fit-into-a-container-on-android.html
     *
     * @param boxPaint The [Paint] object which the text size should be applied to.
     * @param boxWidth The max width for the surrounding box of [text].
     * @param boxHeight The max height for the surrounding box of [text].
     * @param text The text for which the size should be calculated.
     */
    private fun fitTextPaint(boxPaint: Paint, boxWidth: Float, boxHeight: Float, text: String, multiplier: Float = 1.0f): Float {
        var stage = 1
        var textSize = 0.0f
        val tempRect = Rect()
        while (stage < 3) {
            if (stage == 1) {
                textSize += 10.0f
            } else if (stage == 2) {
                textSize -= 1.0f
            }
            boxPaint.textSize = textSize
            boxPaint.getTextBounds(text, 0, text.length, tempRect)
            val fits = tempRect.width() < boxWidth && tempRect.height() < boxHeight
            if (stage == 1 && !fits || stage == 2 && fits) {
                stage++
            }
        }
        textSize *= multiplier
        boxPaint.textSize = textSize
        return textSize
    }


    private val labelPaint = Paint().apply {
        color = Color.BLACK
        alpha = 255
        isAntiAlias = true
        isFakeBoldText = false
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimension(R.dimen.key_textSize)
        typeface = Typeface.DEFAULT
    }
    private val subLabelPaint = Paint().apply {
        color = Color.WHITE
        alpha = 120
        isAntiAlias = true
        isFakeBoldText = false
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimension(R.dimen.key_textHintSize)
        typeface = Typeface.MONOSPACE
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        val label = "puppy"
        val subLabel = "dog"

        if (label != null) {
            fitTextPaint(labelPaint, measuredWidth * 1.0f, measuredHeight * 1.0f, label, 1.0f)

            val centerX = measuredWidth / 2.0f
            val centerY = measuredHeight / 2.0f + (labelPaint.textSize - labelPaint.descent()) / 2
            canvas.drawText(label, centerX, centerY, labelPaint)
        }

        if (subLabel != null) {
//            fitTextPaint(subLabelPaint, 4.0f, 2.0f, subLabel, 1.0f)

            val centerX = measuredWidth * 5.0f / 6.0f
            val centerY = measuredHeight * 1.0f / 6.0f + (subLabelPaint.textSize - subLabelPaint.descent()) / 2
            canvas.drawText(subLabel, centerX, centerY, subLabelPaint)
        }
    }

    private inner class KeyViewOutline(
        private val width: Int,
        private val height: Int,
    ) : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            view ?: return
            outline ?: return

            outline.setRoundRect(
                0, 0, width, height,
                view.resources.getDimension(R.dimen.key_borderRadius)
            )
        }
    }
}