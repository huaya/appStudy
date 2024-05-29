package com.example.appstduy.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appstduy.R;

public class MessageView extends LinearLayout {

    private TextView titleView;

    private TextView contentView;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.i("MessageView", "oncreate");
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.message_view, this, true);
        titleView = findViewById(R.id.title);
        contentView = findViewById(R.id.content);
    }

    private TextView initTextView() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(16);
        textView.setTypeface(Typeface.SANS_SERIF);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxLines(1);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    public void refreshText(String title, String content) {
        titleView.setText(title);
        contentView.setText(content);
    }
}
