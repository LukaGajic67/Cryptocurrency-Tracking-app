package com.example.cryptocurrencytracker.utils.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GraphView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {
    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0f
    private var xMax = 0f
    private var yMin = 0f
    private var yMax = 0f

    private val dataPointPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val dataPointFillPaint = Paint().apply {
        color = Color.WHITE
    }

    private val dataPointLinePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 3f
    }

    fun setData(newDataSet: List<DataPoint>) {
        xMin = newDataSet.minByOrNull { it.xVal }?.xVal ?: 0f
        xMax = newDataSet.maxByOrNull { it.xVal }?.xVal ?: 0f
        yMin = newDataSet.minByOrNull { it.yVal }?.yVal ?: 0f
        yMax = newDataSet.maxByOrNull { it.yVal }?.yVal ?: 0f
        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dataSet.forEachIndexed { index, currentDataPoint ->
            val realX = currentDataPoint.xVal.toRealX()
            val realY = currentDataPoint.yVal.toRealY()

            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY  = currentDataPoint.yVal.toRealY()
                val endX  = nextDataPoint.xVal.toRealX()
                val endY = nextDataPoint.yVal.toRealY()
                canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint)
            }

            canvas.drawCircle(realX, realY, 5f, dataPointFillPaint)
            canvas.drawCircle(realX, realY, 5f, dataPointPaint)
        }

        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    private fun Float.toRealX() = this / xMax * width
    private fun Float.toRealY() = this / yMax * height
}