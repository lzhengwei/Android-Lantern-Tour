package com.example.user.demo_float_drawerlayout_n;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.view.MotionEvent;
import android.view.View;

public class PathView extends View {
    private Path mPath = new Path();
    private Matrix matrix = new Matrix();
    private Bitmap bitmap;

    private static final int RADIUS = 200;

    private static final int FACTOR = 5;
    private int mCurrentX, mCurrentY;  
    public PathView(Context context) {
        super(context);  
        mPath.addCircle(RADIUS, RADIUS, RADIUS, Direction.CW);
        matrix.setScale(FACTOR, FACTOR);  
          
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lanternmapmax);
    }     
      
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = (int) event.getX();  
        mCurrentY = (int) event.getY();  
          
        invalidate();  
        return true;  
    }  
      
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.translate(mCurrentX - RADIUS, mCurrentY - RADIUS);  
        canvas.clipPath(mPath);
        canvas.translate(RADIUS-mCurrentX*FACTOR, RADIUS-mCurrentY*FACTOR);  
        canvas.drawBitmap(bitmap, matrix, null);          
    }  
}  
