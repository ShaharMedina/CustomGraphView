package com.example.shaharmedina.mygraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by shaharmedina on 30/07/16.
 */
public class LineGraphView extends View {

    private static final int MIN_LINES = 4;
    private static final int MAX_LINES = 7;
    private static final int[] DISTANCES = { 1, 2, 5 };

    private float[] datapoints = new float[] {};//save all the point in the graph
    private Paint paint = new Paint();

    public LineGraphView(Context context) {
        super(context);
    }

    public LineGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGraphData(float[] data) {
        this.datapoints = data.clone();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawLineGraph(canvas);
    }

    //draw the background X Y system
    private void drawBackground(Canvas canvas) {
        float maxValue = getMax(datapoints);
        int range = getLineDistance(maxValue);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        for (int y = 0; y < maxValue; y += range) {
            final float yPos = getYPos(y);
            canvas.drawText(""+y,0,yPos,paint);
            canvas.drawLine(0, yPos, getWidth(), yPos, paint);
        }
        canvas.drawLine(getLeft(),getPaddingTop(),getLeft(),getHeight(),paint);
    }

    //return the distance between the line
    private int getLineDistance(float maxValue) {
        int distance;
        int distanceIndex = 0;
        int distanceMultiplier = 1;
        int numberOfLines = MIN_LINES;

        do {
            distance = DISTANCES[distanceIndex] * distanceMultiplier;
            numberOfLines = (int) Math.ceil(maxValue / distance);

            distanceIndex++;
            if (distanceIndex == DISTANCES.length) {
                distanceIndex = 0;
                distanceMultiplier *= 10;
            }
        } while (numberOfLines < MIN_LINES || numberOfLines > MAX_LINES);

        return distance;
    }

    //draw the graph on the background
    private void drawLineGraph(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getXPos(0), getYPos(datapoints[0]));
        for (int i = 1; i < datapoints.length; i++) {
            path.lineTo(getXPos(i), getYPos(datapoints[i]));
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(0xFF33B5E5);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4, 2, 2, 0x80000000);
        canvas.drawPath(path, paint);
        paint.setShadowLayer(0, 0, 0, 0);
    }

    //return the max number from data
    private float getMax(float[] array) {
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    //return y position
    private float getYPos(float value) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float maxValue = getMax(datapoints);

        // scale it to the view size
        value = (value / maxValue) * height;

        // invert it so that higher values have lower y
        value = height - value;

        // offset it to adjust for padding
        value += getPaddingTop();

        return value;
    }

    //return x position
    private float getXPos(float value) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = datapoints.length - 1;

        // scale it to the view size
        value = (value / maxValue) * width;

        // offset it to adjust for padding
        value += getPaddingLeft();

        return value;
    }

}
