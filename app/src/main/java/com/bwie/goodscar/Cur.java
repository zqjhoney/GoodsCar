package com.bwie.goodscar;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 张乔君 on 2017/10/25.
 */

public class Cur extends View {

    public static final float RADIUS=50f;
    private Point currentPoint;
    private circleColor currentColor;
    private Paint paint=new Paint();

    public Cur(Context context) {
        super(context);
    }

    public Cur(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Cur(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    //确定控件的位置和颜色
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint==null){
            currentColor=new circleColor(Color.BLUE);
            paint.setColor(currentColor.getColor());
            currentPoint=new Point(RADIUS,RADIUS);
            canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),RADIUS,paint);
            starAnim();
        }else {
            paint.setColor(currentColor.getColor());
            canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),RADIUS,paint);
        }
    }
    //开始设计动画的效果
    private void starAnim() {
        Point startPoint=new Point(RADIUS,RADIUS);
        Point endPoint=new Point(getWidth()-RADIUS,getHeight()-RADIUS);
        final ValueAnimator value=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation!=null){
                    currentPoint=(Point)animation.getAnimatedValue();
                    invalidate();
                }
            }
        });
        ValueAnimator valueAnimator=ValueAnimator.ofObject(new ColorEvalustor(),new circleColor(Color.BLUE),new circleColor(Color.RED));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentColor=(circleColor)animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet set=new AnimatorSet();
        set.play(value).with(valueAnimator);
        set.setDuration(3000);
        set.start();
    }
    //自己定义的模式里面计算开始到结束，原点和颜色的差值
    class PointEvaluator implements TypeEvaluator{

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoin=(Point)startValue;
            Point endPoin=(Point)endValue;
            float x = startPoin.getX() + fraction * (endPoin.getX() - startPoin.getX());
            float y = startPoin.getY() + fraction * (endPoin.getY() - startPoin.getY());
            return new Point(x,y);
        }
    }
    class ColorEvalustor implements TypeEvaluator {
        int count;
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            circleColor startColor=(circleColor)startValue;
            circleColor endColor=(circleColor)endValue;

            int alpha=(int)(Color.alpha(startColor.getColor())+fraction*(Color.alpha(endColor.getColor())-Color.alpha(startColor.getColor())));
            int red=(int)(Color.red(startColor.getColor())+fraction*(Color.red(endColor.getColor())-Color.red(startColor.getColor())));
            int green=(int)(Color.green(startColor.getColor())+fraction*(Color.green(endColor.getColor())-Color.green(endColor.getColor())));
            int blue=(int)(Color.blue(startColor.getColor())+fraction*(Color.blue(endColor.getColor())-Color.blue(endColor.getColor())));


            return new circleColor(Color.argb(alpha,red,green,blue));
        }
    }
    public class circleColor{
        private int color;

        @Override
        public String toString() {
            return "circleColor{" +
                    "color=" + color +
                    '}';
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public circleColor(int color) {
            this.color = color;
        }
    }

    public class Point {
        private float x;
        private float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
