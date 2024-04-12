package com.example.appstduy;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.appstduy.R;

import org.jetbrains.annotations.Nullable;


/**
 * @content:注意:因为是在固定开发板用,没怎么考虑适配情况. * 1.表盘大小是根据控件xml布局文件的width决定
 * * 2.控件宽度太小可能刻度文字显示不全,可以看情况调整下文字大小.
 */
public class CanvaTestView extends View {
    private static final String TAG = "DashboardView";
    private Paint arcPaint;
    //圆环的角度
    private int SWEEPANGLE = 280;
    //刻度画笔
    private Paint pointerPaint;

    private Context mContext;

    //圆环半径
    private int mRadius;
    //圆环的宽度
    int arcW = 50;
    //发光的宽度
    int gleamyArcW = arcW * 3;
    //刻度宽度
    int minScalew = 5;
    int maxScalew = 5;
    //刻度的长度
    int maxScaleLength = 5;
    int minScaleLength = -30;
    //发光圆环的半径
    private int mRadiusG;
    //阴影宽度
    private int shade_w = 40;

    private float currentDegree = 0;
    //指针当前的角度.
    private int startAngele = 90 + (360 - SWEEPANGLE) / 2;
    //仪表盘显示的数字
    private String speed = "0";
    private Paint mTextPaint;
    private Paint mPaint;

    private Paint mPointerPaint;

    private Paint mCenterRingPaint;

    private ValueAnimator mAnim;
    private int clockPointNum = 40;

    public CanvaTestView(Context context) {
        super(context);
        init(context);
    }


    public CanvaTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        //外层动态圆环画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);//画线模式
        mPaint.setStrokeWidth(arcW);//线宽度
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        //圆环画笔
        arcPaint = new Paint();
        arcPaint.setStyle(Paint.Style.STROKE);//画线模式
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(arcW);//线宽度
        arcPaint.setColor(getResources().getColor(R.color.kedu));
        arcPaint.setAlpha(80);
        arcPaint.setAntiAlias(true);
        //刻度
        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setColor(getResources().getColor(R.color.blue));
        pointerPaint.setTextSize(40);
        pointerPaint.setTextAlign(Paint.Align.RIGHT);

        //文字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(60);

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(true);

        mCenterRingPaint = new Paint();
        mCenterRingPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        mRadius = (getMeasuredWidth() / 2) - 30;
        mRadiusG = mRadius - gleamyArcW / 2;
        shade_w = (int) (mRadius * 0.4);
        //最外层白色动态圆环
        drawDynamicArcs(canvas);
        //圆环
        drawArcs(canvas);
        //刻度
        drawDegree(canvas);

        drawCenterRing(canvas);
        // 指针
//        drawPointer(canvas);
        drawPointer2(canvas);
    }

    private void drawCenterRing(Canvas canvas) {
        canvas.save();
        mCenterRingPaint.setStyle(Paint.Style.STROKE);//画线模式
        mCenterRingPaint.setStrokeWidth(50);//线宽度
        mCenterRingPaint.setStrokeCap(Paint.Cap.ROUND);

        int ringWidth = 100;
        RectF rectF = new RectF(getMeasuredWidth() / 2 - ringWidth, getMeasuredHeight() / 2 - ringWidth,
                getMeasuredWidth() / 2 + ringWidth, getMeasuredHeight() / 2 + ringWidth);
        mCenterRingPaint.setColor(Color.parseColor("#278EF7"));
        canvas.drawArc(rectF, 0, 360, true, mCenterRingPaint);

        int ringWidth2 = 50;
        mCenterRingPaint.setStyle(Paint.Style.FILL);//画线模式
        RectF rectF2 = new RectF(getMeasuredWidth() / 2 - ringWidth2, getMeasuredHeight() / 2 - ringWidth2,
                getMeasuredWidth() / 2 + ringWidth2, getMeasuredHeight() / 2 + ringWidth2);
        mCenterRingPaint.setColor(Color.parseColor("#195EA1"));
        canvas.drawArc(rectF2, 0, 360, true, mCenterRingPaint);
        canvas.restore();
    }

    private void drawPointer2(Canvas canvas) {
        canvas.save();
        mPointerPaint.setColor(Color.RED);
        // 初始时旋转到0的位置
        canvas.rotate(310, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        canvas.rotate(currentDegree, getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        canvas.drawLine(getMeasuredWidth() / 2,  500, getMeasuredHeight() / 2, getMeasuredWidth() / 2,
                mPointerPaint);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 8, mPointerPaint);

        canvas.restore();
    }

    private void drawPointer(Canvas canvas) {
        canvas.rotate(310, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        canvas.rotate(currentDegree, getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        int pointerWidth = 20;

        mPointerPaint.setStyle(Paint.Style.FILL);//画线模式
        RectF rectF = new RectF(getMeasuredWidth() / 2 - pointerWidth, getMeasuredHeight() / 2 - pointerWidth,
                getMeasuredWidth() / 2 + pointerWidth, getMeasuredHeight() / 2 + pointerWidth);

        Path path1 = new Path();
        path1.moveTo(getMeasuredHeight() / 2 - mRadiusG + minScaleLength - 5, getMeasuredHeight() / 2); // 顶点
        path1.lineTo(getMeasuredWidth() / 2, getMeasuredHeight() / 2); // 左下角
        path1.lineTo(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - pointerWidth); // 右下角
        path1.close(); // 关闭路径形成三角形

        mPointerPaint.setColor(Color.parseColor("#E0DDD4"));
        canvas.drawPath(path1, mPointerPaint);
        canvas.drawArc(rectF, 270, 90, true, mPointerPaint);

        Path path2 = new Path();
        path2.moveTo(getMeasuredHeight() / 2 - mRadiusG + minScaleLength, getMeasuredHeight() / 2); // 顶点
        path2.lineTo(getMeasuredWidth() / 2, getMeasuredHeight() / 2); // 左下角
        path2.lineTo(getMeasuredWidth() / 2, getMeasuredHeight() / 2 + pointerWidth); // 右下角
        path2.close(); // 关闭路径形成三角形

        mPointerPaint.setColor(Color.parseColor("#F6EFE9"));
        canvas.drawPath(path2, mPointerPaint);
        canvas.drawArc(rectF, 0, 90, true, mPointerPaint);

        mPointerPaint.setColor(Color.parseColor("#C0B6B4"));
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 10, mPointerPaint);
    }

    private void drawDynamicArcs(Canvas canvas) {
        canvas.save();
        //半径
//        int dyRaduis = (int) (getMeasuredWidth() / 2 * 0.88);
        int dyRaduis = mRadius;
        int[] colorSweep = new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#0541ED")};
        float[] position = new float[]{0f, 0.5f};
        SweepGradient mShader = new SweepGradient(getMeasuredWidth() / 2, getMeasuredHeight() / 2, colorSweep, position);

        //旋转渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(startAngele - 6, canvas.getWidth() / 2, canvas.getHeight() / 2);
        mShader.setLocalMatrix(matrix);
        mPaint.setShader(mShader);

        RectF rectF = new RectF();
        rectF.left = (float) (getMeasuredWidth() / 2 - dyRaduis);
        rectF.top = (float) (getMeasuredHeight() / 2 -dyRaduis);
        rectF.right = (float) (getMeasuredWidth() / 2 + dyRaduis);
        rectF.bottom = (float) (getMeasuredHeight() / 2 + dyRaduis);
        canvas.drawArc(rectF, startAngele, currentDegree, false, mPaint);
        canvas.restore();
    }

    private void drawArcs(Canvas canvas) {
        canvas.save();
        RectF rectF = new RectF(getMeasuredWidth() / 2 - mRadius, getMeasuredHeight() / 2 - mRadius, getMeasuredWidth() / 2 + mRadius, getMeasuredHeight() / 2 + mRadius);

        canvas.drawArc(rectF, 90 + (360 - SWEEPANGLE) / 2, SWEEPANGLE, false, arcPaint);
        canvas.restore();
    }

    private void drawDegree(Canvas canvas) {
        pointerPaint.setColor(Color.parseColor("#ffffff"));
        pointerPaint.setTextSize(40);
        pointerPaint.clearShadowLayer();
        pointerPaint.setTextAlign(Paint.Align.RIGHT);

        canvas.save();
        //原点移到空间中心点
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        canvas.rotate((360 - SWEEPANGLE) / 2 + 90);
        //设置刻度文字颜色大小.
        mTextPaint.reset();
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setTextSize(20);
        mTextPaint.clearShadowLayer();
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setAntiAlias(true);
        for (int i = 0; i < clockPointNum; i++) {
            if (i % 4 == 0) {     //长表针
                pointerPaint.setStrokeWidth(maxScalew);
                canvas.drawLine(mRadiusG + arcW, maxScalew / 2, mRadiusG + maxScaleLength, maxScalew / 2, pointerPaint);

                drawPointerText(canvas, mRadiusG - arcW, maxScalew / 2, i);

            } else {    //短表针
                pointerPaint.setStrokeWidth(minScalew);
                canvas.drawLine(mRadiusG + arcW, maxScalew / 2, mRadiusG - minScaleLength, maxScalew / 2, pointerPaint);
            }

            canvas.rotate((float) SWEEPANGLE / (float) clockPointNum);
        }
        //最后一根
        canvas.drawLine(mRadiusG + arcW, -maxScalew / 2, mRadiusG + maxScaleLength, -maxScalew / 2, pointerPaint);

        drawPointerText(canvas, mRadiusG - arcW, maxScalew / 2, 40);
        canvas.restore();
        count = 0;
    }

    int count = 0;

    private void drawPointerText(Canvas canvas, int x, int y, int i) {
        //动态设置刻度文字颜色。
        float a = (float) SWEEPANGLE / (float) clockPointNum;

        if (currentDegree > a * i) mTextPaint.setColor(Color.WHITE);
        else mTextPaint.setColor(Color.parseColor("#ffffff"));

        if (i != 0) count += 10;
        //canvas.drawText(String.valueOf(count), x - maxScaleLength, y, mTextPaint);

        //保存状态
        canvas.save();

        canvas.translate(mRadiusG - maxScaleLength, y);
        // 90 + (360 - SWEEPANGLE) / 2 = 130
        canvas.rotate(-(130f + a * i));

        String str = String.valueOf(count);

        //文字宽
        float text_w = mTextPaint.measureText(str);
        //文字baseline在y轴方向的位置
        float baseLineY = Math.abs(mTextPaint.ascent() + mTextPaint.descent()) / 2;
        if (i >= 0 && i <= 8) {
            mTextPaint.setTextAlign(Paint.Align.LEFT);
        } else if (i > 8 && i <= 20) {
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            baseLineY = baseLineY;
        } else if (i > 20) {
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
        }
        mTextPaint.setTextSize(30);
        canvas.drawText(String.valueOf(count / 10), 5, baseLineY, mTextPaint);
        //恢复对canvas操作
        canvas.restore();
    }

    /**
     * 外部更新刻度.
     *
     * @param speedssss .
     */
    public void udDataSpeed(int speedssss) {
        Log.i(TAG, "speedssss::" + speedssss);
        float a = SWEEPANGLE / 180f;
        if (speedssss < 0) throw new IllegalArgumentException("----speed不能小于0----");
        speed = String.valueOf(speedssss);
        startAnimation(currentDegree, (float) speedssss * a);
    }

    //指针+阴影偏移动画.
    private void startAnimation(float start, float end) {
        if (mAnim != null) {
            if (mAnim.isRunning() || mAnim.isStarted()) {
                mAnim.cancel();
                mAnim.removeAllUpdateListeners();
            }
            boolean running = mAnim.isRunning();
            boolean started = mAnim.isStarted();
            Log.d(TAG, "startAnimation: running:" + running + "--started" + started);
        }
        mAnim = ValueAnimator.ofFloat(start, end);
        mAnim.setDuration(500);
        mAnim.addUpdateListener(valueAnimator -> {
            currentDegree = (float) mAnim.getAnimatedValue();
            invalidate();
        });
        mAnim.start();
    }

    /**
     * 退出动画.
     */
    public void closeAnimation() {
        if (mAnim != null) {
            mAnim.cancel();
            mAnim.removeAllUpdateListeners();
        }
    }
}