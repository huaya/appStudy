package com.example.appstduy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.appstduy.util.DensityUtil;

/**
 * 圆形仪表盘
 * Created by zhuwentao on 2017-08-26.
 */
public class CircleMeterView extends View {
    private static final String TAG = "CircleMeterView";

    // 外圆刻度画笔
    private Paint mScalePaint;

    // 外圆刻度数值画笔
    private Paint mScaleTextPaint;

    // 内圆画笔
    private Paint mInsidePaint;

    // 中间数值画笔
    private Paint mTextPaint;

    // 指针画笔
    private Paint mPointerPaint;

    // View宽
    private float mWidth;

    // View高
    private float mHeight;

    // 外刻度圆进度
    private float mProgress = 0;

    // 内刻度圆进度
    private float mInsideProgress = 0;

    // 中间显示的数值
    private float value = 0;

    private int scaleColor;

    private int scaleTextColor;

    private int insideCircleColor;

    private int textSize;

    private int textColor;

    private int pointerColor;

    private Context mContext;

    public CircleMeterView(Context context) {
        this(context, null);
    }

    public CircleMeterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置属性
        scaleColor = Color.BLUE;
        scaleTextColor = Color.WHITE;
        insideCircleColor = Color.BLUE;
        textSize = 36;
        textColor = Color.WHITE;
        pointerColor = Color.RED;

        initUI();
    }

    private void initUI() {
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mContext = getContext();

        // 刻度圆画笔
        mScalePaint = new Paint();
        mScalePaint.setAntiAlias(true);
        mScalePaint.setStrokeWidth(DensityUtil.dip2px(mContext, 2));
        mScalePaint.setColor(scaleColor);
        mScalePaint.setStrokeCap(Paint.Cap.ROUND);
        mScalePaint.setStyle(Paint.Style.STROKE);

        // 刻度文字画笔
        mScaleTextPaint = new Paint();
        mScaleTextPaint.setAntiAlias(true);
        mScaleTextPaint.setStrokeWidth(DensityUtil.dip2px(mContext, 2));
        mScaleTextPaint.setColor(scaleTextColor);
        mScaleTextPaint.setTextSize(24);
        mScaleTextPaint.setStyle(Paint.Style.FILL);

        // 中间值的画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(DensityUtil.dip2px(mContext, 2));
        mTextPaint.setTextSize(36);
        mTextPaint.setColor(textColor);
        mTextPaint.setStrokeJoin(Paint.Join.ROUND);
        mTextPaint.setStyle(Paint.Style.FILL);

        // 内部扇形刻度画笔
        mInsidePaint = new Paint();
        mInsidePaint.setAntiAlias(true);
        mInsidePaint.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        mInsidePaint.setColor(insideCircleColor);
        mInsidePaint.setStyle(Paint.Style.FILL);

        // 指针画笔
        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setStrokeWidth(DensityUtil.dip2px(mContext, 2));
        mPointerPaint.setColor(pointerColor);
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setVisibility(View.VISIBLE);
        super.onDraw(canvas);
        drawArcScale(canvas);
        drawArcInside(canvas);
        drawInsideSumText(canvas);
        drawPointer(canvas);
    }

    /**
     * 画外圆和文字刻度
     */
    private void drawArcScale(Canvas canvas) {
        canvas.save();

        canvas.rotate(-30, mWidth / 2, mHeight / 2);

        // 最外圆的线条宽度，避免线条粗时被遮蔽
        float scaleWidth = mScalePaint.getStrokeWidth();

        canvas.drawArc(new RectF(scaleWidth, scaleWidth, mWidth - scaleWidth, mHeight - scaleWidth), 180, 240, false, mScalePaint);

        // 定义文字旋转回的角度
        int rotateValue = 30;
        // 总八个等分，每等分5个刻度，所以总共需要40个刻度
        for (int i = 0; i <= 40; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(scaleWidth, mHeight / 2, DensityUtil.dip2px(mContext, 15), mHeight / 2, mScalePaint);

                // 画文字
                String text = String.valueOf(i / 5);
                Rect textBound = new Rect();
                mScaleTextPaint.getTextBounds(text, 0, text.length(), textBound);   // 获取文字的矩形范围
                int textWidth = textBound.right - textBound.left;  // 获得文字宽度
                int textHeight = textBound.bottom - textBound.top;  // 获得文字高度

                canvas.save();
                canvas.translate(DensityUtil.dip2px(mContext, 15) + textWidth + DensityUtil.dip2px(mContext, 5), mHeight / 2);  // 移动画布的圆点

                if (i == 0) {
                    // 如果刻度为0，则旋转度数为30度
                    canvas.rotate(rotateValue);
                } else {
                    // 大于0的刻度，需要逐渐递减30度
                    canvas.rotate(rotateValue);
                }
                rotateValue = rotateValue - 30;

                canvas.drawText(text, -textWidth / 2, textHeight / 2, mScaleTextPaint);
                canvas.restore();

            } else {
                canvas.drawLine(scaleWidth, mHeight / 2, DensityUtil.dip2px(mContext, 10), mHeight / 2, mScalePaint);
            }
            canvas.rotate(6, mWidth / 2, mHeight / 2);
        }
        canvas.restore();
    }

    /**
     * 画内圆刻度
     */
    private void drawArcInside(Canvas canvas) {
        canvas.save();

        canvas.rotate(-30, mWidth / 2, mHeight / 2);
        for (int i = 0; i < 100; i++) {
            if (mInsideProgress > i) {
                // 大于外圆刻度6时显示红色
                if (i <= 75) {
                    mInsidePaint.setColor(insideCircleColor);
                } else {
                    mInsidePaint.setColor(Color.RED);
                }
            } else {
                mInsidePaint.setColor(Color.LTGRAY);
            }
            canvas.drawLine(DensityUtil.dip2px(mContext, 40), mHeight / 2, DensityUtil.dip2px(mContext, 50), mHeight / 2, mInsidePaint);
            canvas.rotate(2.4f, mWidth / 2, mHeight / 2);
        }

        canvas.restore();
    }

    /**
     * 画内部数值
     */
    private void drawInsideSumText(Canvas canvas) {
        canvas.save();

        if (mInsideProgress > 75) {
            mTextPaint.setColor(Color.RED);
        } else {
            mTextPaint.setColor(Color.BLACK);
        }
        // 获取文字居中显示需要的参数
        String showValue = String.valueOf(value);
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(showValue, 0, showValue.length(), textBound);    // 获取文字的矩形范围
        float textWidth = textBound.right - textBound.left;  // 获得文字宽
        float textHeight = textBound.bottom - textBound.top; // 获得文字高
        canvas.drawText(showValue, mWidth / 2 - textWidth / 2, mHeight / 2 + textHeight + DensityUtil.dip2px(mContext, 45), mTextPaint);

        canvas.restore();
    }

    /**
     * 画指针
     */
    private void drawPointer(Canvas canvas) {
        canvas.save();

        // 旋转到0的位置
        canvas.rotate(-30, mWidth / 2, mHeight / 2);
        canvas.rotate(mProgress, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2 - DensityUtil.dip2px(mContext, 60), mHeight / 2, mWidth / 2, mHeight / 2, mPointerPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, DensityUtil.dip2px(mContext, 5), mPointerPaint);

        canvas.restore();
    }


    /**
     * 设置进度
     */
    public void setProgress(int progress) {
        Log.i(TAG, "setProgress");

        // 内部刻度的进度
        this.mInsideProgress = progress;

        // 指针显示的进度
        this.mProgress = (float) progress * 2.4f;

        // 设置中间文字显示的数值
        this.value = (float) (progress * 0.08);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int myWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int myHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int myHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 获取宽度
        if (myWidthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = myWidthSpecSize;
        } else {
            // wrap_content
            mWidth = DensityUtil.dip2px(mContext, 120);
        }

        // 获取高度
        if (myHeightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = myHeightSpecSize;
        } else {
            // wrap_content
            mHeight = DensityUtil.dip2px(mContext, 120);
        }

        // 设置该view的宽高
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }
}