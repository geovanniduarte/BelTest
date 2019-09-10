class BrujulaView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var backColor = Color.WHITE
    private var borderColor = Color.BLACK
    private var needleNColor = Color.RED
    private var needleSColor = Color.BLUE
    private var borderWidth = 4.0f
    private var size = 400

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val radius = size / 2f
            drawCircleBackground(it, radius)
            drawCoordinatesLetters(it, radius)
            drawNeedles(it, radius)
            drawCenter(it, radius)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    private fun drawCircleBackground(canvas: Canvas, radius: Float) {

        paint.color = backColor
        paint.style = Paint.Style.FILL


        canvas.drawCircle(size / 2f, size / 2f, radius, paint)


        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth


        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private  fun drawCenter(canvas: Canvas, radius: Float) {
        paint.style = Paint.Style.FILL
        paint.color = borderColor
        canvas.drawCircle(size / 2f, size / 2f, radius / 10f, paint)
    }

    private fun drawCoordinatesLetters(canvas: Canvas, radius: Float) {
        val textSize = size / 4f
        val textCenterOffSet = textSize/ 3f
        val textBorderOffSet = textSize / 10f
        paint.color = borderColor
        paint.textSize = textSize
        paint.style = Paint.Style.FILL

        canvas.drawText("N", (size / 2f) - textCenterOffSet, textSize - textBorderOffSet , paint)
        canvas.drawText("S", (size / 2f) - textCenterOffSet, (radius * 2) - textBorderOffSet, paint)

        canvas.drawText("E", (radius * 2) - (textSize - (textBorderOffSet * 3)),(size / 2f) + textCenterOffSet, paint)
        canvas.drawText("O", 0F + textBorderOffSet,(size / 2f) + textCenterOffSet, paint)
    }

    private fun drawNeedles(canvas: Canvas, radius: Float) {
        paint.color = needleNColor
        paint.style = Paint.Style.FILL
        paint.strokeWidth = radius / 30


        canvas.drawLine(size / 2f, size / 2f, size / 2f, (radius / 2f), paint) //linea al norte

        paint.color = needleSColor
        canvas.drawLine(size / 2f, size / 2f, size / 2f, (size / 2f) +  (radius / 2f), paint) //linea al sur
    }
}