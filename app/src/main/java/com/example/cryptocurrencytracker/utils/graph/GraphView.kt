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
    private var xMin = 0.0
    private var xMax = 0.0
    private var yMin = 0.0
    private var yMax = 0.0
    private var relativeHeight = 0.0

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
        xMin = newDataSet.minByOrNull { it.xVal }?.xVal ?: 0.0
        xMax = newDataSet.maxByOrNull { it.xVal }?.xVal ?: 0.0
        yMin = newDataSet.minByOrNull { it.yVal }?.yVal ?: 0.0
        yMax = newDataSet.maxByOrNull { it.yVal }?.yVal ?: 0.0
        relativeHeight = yMax - yMin
        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dataSet.forEachIndexed { index, currentDataPoint ->

            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY = currentDataPoint.yVal.toRealY()
                val endX = nextDataPoint.xVal.toRealX()
                val endY = nextDataPoint.yVal.toRealY()
                canvas.drawLine(
                    startX.toFloat(),
                    startY.toFloat(),
                    endX.toFloat(),
                    endY.toFloat(),
                    dataPointLinePaint
                )
            }
        }

        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    private fun Double.toRealX() = this / xMax * width
    private fun Double.toRealY() = (yMax - this) / relativeHeight * height
}