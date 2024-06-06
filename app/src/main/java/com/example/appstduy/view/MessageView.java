package com.example.appstduy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appstduy.R;

public class MessageView extends LinearLayout {

    private TextView titleView;

    private TextView colonView;

    private TextView contentView;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.i("MessageView", "oncreate");
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.message_view, this, true);
        titleView = findViewById(R.id.title);
        contentView = findViewById(R.id.content);
        colonView = findViewById(R.id.colon);
    }

    public void refreshText(String title, String content) {
        titleView.setText(title);
        contentView.setText(content);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getColonView() {
        return colonView;
    }

    public TextView getContentView() {
        return contentView;
    }
}
