package com.example.appstduy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstduy.databinding.FragmentHilinkBinding;
import com.example.appstduy.util.DensityUtil;
import com.example.appstduy.util.LogUtils;
import com.example.appstduy.view.MessageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HilinkFragment extends Fragment {
    private static final String TAG = "HilinkFragment";

    private static final int DURATION = 450;

    private FragmentHilinkBinding binding;

    private Handler handler = new Handler(Looper.getMainLooper());

    private int marker;

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (marker < texts.size()) {
                MessageView messageView = (MessageView) binding.messageSwitcher.getNextView();
                messageView.refreshText("妈妈", texts.get(marker++));
                binding.messageSwitcher.showNext();
            } else {
                marker = 0;
                MessageView messageView = (MessageView) binding.messageSwitcher.getNextView();
                messageView.refreshText("妈妈", texts.get(marker));
                binding.messageSwitcher.showNext();
            }
            handler.postDelayed(this, 4000);
        }
    };

    private List<String> texts = new ArrayList<String>() {
        {
            add("从四个“一”，读懂中阿命运共同体");
            add("朝鲜称通过气球向韩国投放大量污物热");
            add("全椒县委书记被免 新书记火速到岗热");
            add("多项增长数据折射中国经济强劲动力");
            add("歼20首飞试飞员称“下一代快来了”");
            add("女子离婚时才知医生老公是无业游民");
            add("法国做好准备承认巴勒斯坦国热");
            add("67岁中国老人赴美探亲遭枪杀");
        }
    };

    public HilinkFragment() {
    }

    public static HilinkFragment newInstance(String param1, String param2) {
        HilinkFragment fragment = new HilinkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.messageSwitcher.setFactory(() -> new MessageView(getContext()));
        handler.post(task);
        binding.showSwitcher.setOnClickListener(v -> {
            binding.messageView.setVisibility(View.VISIBLE);
            binding.messageView2.setVisibility(View.INVISIBLE);
        });
        binding.messageView2.setVisibility(View.INVISIBLE);

        binding.showUnread.setOnClickListener(v -> {
            binding.messageView2.setVisibility(View.VISIBLE);
            handler.removeCallbacks(task);
            doShowUnreadAnimator();
        });
    }

    private void doShowUnreadAnimator() {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator readedAlphaAnimator = ObjectAnimator.ofFloat(binding.messageView, "alpha", 1.0f, 0.0f);
        ObjectAnimator unreadAlphaAnimator = ObjectAnimator.ofFloat(binding.messageView2, "alpha", 0.0f, 1.0f);
        Collections.addAll(animators, readedAlphaAnimator, unreadAlphaAnimator);

        ValueAnimator heightValueAnimator = ValueAnimator.ofInt(binding.messageView.getMeasuredHeight(),  binding.messageView2.getMeasuredHeight());
        heightValueAnimator.addUpdateListener(animation -> {
            int curValue = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams1 = binding.messageView2.getLayoutParams();
            layoutParams1.height = curValue;
            binding.messageView2.setLayoutParams(layoutParams1);
        });
        Collections.addAll(animators, heightValueAnimator);

        ValueAnimator imageLayoutValueAnimator = ValueAnimator.ofInt(40, 25);
        imageLayoutValueAnimator.addUpdateListener(animation -> {
            int curValue = (int) animation.getAnimatedValue();
            int curLay = DensityUtil.dip2px(requireContext(), curValue);
            ViewGroup.LayoutParams unreadImageLayout = binding.imageView2.getLayoutParams();
            unreadImageLayout.height = curLay;
            unreadImageLayout.width = curLay;
            binding.imageView2.setLayoutParams(unreadImageLayout);
            ViewGroup.LayoutParams readedImageLayout = binding.imageView.getLayoutParams();
            readedImageLayout.height = curLay;
            readedImageLayout.width = curLay;
            binding.imageView.setLayoutParams(readedImageLayout);
        });
        Collections.addAll(animators, imageLayoutValueAnimator);

        MessageView messageView = (MessageView) binding.messageSwitcher.getCurrentView();

        ObjectAnimator unreadAnimatorX = ObjectAnimator.ofFloat(binding.unreadTitle, "TranslationX", 120, 0);
        ObjectAnimator unreadAnimatorY = ObjectAnimator.ofFloat(binding.unreadTitle, "TranslationY", -55, 0);
        ObjectAnimator readedAnimatorX = ObjectAnimator.ofFloat(messageView.getTitleView(), "TranslationX", 0, -90);
        ObjectAnimator readedAnimatorY = ObjectAnimator.ofFloat(messageView.getTitleView(), "TranslationY", 0, 45);
        Collections.addAll(animators,  unreadAnimatorX, unreadAnimatorY, readedAnimatorX, readedAnimatorY);

        ObjectAnimator messageLineY = ObjectAnimator.ofFloat(binding.messageLine, "TranslationY", -100, 0);
        Collections.addAll(animators, messageLineY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.playTogether(animators);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                binding.messageView.setVisibility(View.INVISIBLE);
                resetViewParams();
            }
        });
        animatorSet.start();
    }

    private void resetViewParams() {
        binding.messageView.setAlpha(1.0f);
        binding.messageView2.setAlpha(1.0f);

        ViewGroup.LayoutParams layoutParams1 = binding.messageView2.getLayoutParams();
        layoutParams1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        binding.messageView2.setLayoutParams(layoutParams1);

        int unreadImageLay = DensityUtil.dip2px(requireContext(), 25);
        int readImageLay = DensityUtil.dip2px(requireContext(), 40);
        ViewGroup.LayoutParams unreadImageLayout = binding.imageView2.getLayoutParams();
        unreadImageLayout.height = unreadImageLay;
        unreadImageLayout.width = unreadImageLay;
        binding.imageView2.setLayoutParams(unreadImageLayout);
        ViewGroup.LayoutParams readedImageLayout = binding.imageView.getLayoutParams();
        readedImageLayout.height = readImageLay;
        readedImageLayout.width = readImageLay;
        binding.imageView.setLayoutParams(readedImageLayout);

        MessageView messageView = (MessageView) binding.messageSwitcher.getCurrentView();

        binding.unreadTitle.setTranslationX(0);
        binding.unreadTitle.setTranslationY(0);
        messageView.getTitleView().setTranslationX(0);
        messageView.getTitleView().setTranslationY(0);

        binding.messageLine.setTranslationY(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHilinkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}