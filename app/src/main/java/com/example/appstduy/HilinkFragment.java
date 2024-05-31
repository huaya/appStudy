package com.example.appstduy;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.appstduy.databinding.FragmentHilinkBinding;
import com.example.appstduy.util.DensityUtil;
import com.example.appstduy.view.MessageView;

import java.util.ArrayList;
import java.util.List;


public class HilinkFragment extends Fragment {
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
            binding.imageView.setVisibility(View.INVISIBLE);
            binding.messageView2.setVisibility(View.VISIBLE);
            int duration = 450;
            handler.removeCallbacks(task);

            TransitionDrawable transitionDrawable = (TransitionDrawable) requireContext().getDrawable(R.drawable.transition);
            binding.messageView2.setBackground(transitionDrawable);
            transitionDrawable.startTransition(duration);

            ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, 300);
            ValueAnimator valueAnimator2 = ValueAnimator.ofInt(40, 25);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(binding.messageTitle2, "alpha", 0.0f, 1.0f);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(binding.unreadTitle, "alpha", 0.0f, 1.0f);
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(binding.unreadTitle, "TranslationX", 120, 0);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(binding.unreadTitle, "TranslationY", -55, 0);

            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(binding.messageUnread, "alpha", 1f, 1.0f);
            ObjectAnimator animatorY2 = ObjectAnimator.ofFloat(binding.messageUnread, "TranslationY", -70, 0);

            ObjectAnimator messageLineY = ObjectAnimator.ofFloat(binding.messageLine, "TranslationY", -100, 0);
            ObjectAnimator messageLineAlpha = ObjectAnimator.ofFloat(binding.messageLine, "alpha", 0.0f, 1.0f);


            ObjectAnimator transTitle = ObjectAnimator.ofFloat(binding.messageTitle, "TranslationX", 0, -120);
            ObjectAnimator alphaTitle = ObjectAnimator.ofFloat(binding.messageTitle, "alpha", 1.0f,  0.0f, 0.0f);
            MessageView messageView = (MessageView) binding.messageSwitcher.getCurrentView();
            ObjectAnimator alphaMessage = ObjectAnimator.ofFloat(binding.messageView, "alpha", 0.8f, 0.0f);

            ObjectAnimator alphaColon = ObjectAnimator.ofFloat(messageView.getColonView(), "alpha", 1.0f, 0.0f);
            ObjectAnimator alphaContent = ObjectAnimator.ofFloat(messageView.getContentView(), "alpha", 1.0f, 0.0f);
            ObjectAnimator transContentX = ObjectAnimator.ofFloat(messageView.getContentView(), "TranslationX", 0, -100);
            ObjectAnimator transContentY = ObjectAnimator.ofFloat(messageView.getContentView(), "TranslationY", 0, 100);
            ObjectAnimator alphaTitle2 = ObjectAnimator.ofFloat(messageView.getTitleView(), "alpha", 1.0f, 0.0f);
            ObjectAnimator transTitleX = ObjectAnimator.ofFloat(messageView.getTitleView(), "TranslationX", 0, -100);
            ObjectAnimator transTitleY = ObjectAnimator.ofFloat(messageView.getTitleView(), "TranslationY", 0, 30);

            ViewGroup.LayoutParams layoutParams = binding.messageView.getLayoutParams();
            int height = layoutParams.height;

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);
            animatorSet.playTogether(valueAnimator1, valueAnimator2, objectAnimator, objectAnimator2, animatorX, animatorY,
                    transTitle, alphaTitle, alphaColon, alphaContent, transContentX, transContentY, alphaMessage,
                    alphaTitle2, transTitleX, transTitleY, messageLineY, messageLineAlpha, objectAnimator3, animatorY2);

            valueAnimator1.addUpdateListener(animation -> {
                int curValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams1 = binding.messageView2.getLayoutParams();
                layoutParams1.height = height + curValue;
                binding.messageView2.setLayoutParams(layoutParams1);
            });

            valueAnimator2.addUpdateListener(animation -> {
                int curValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams imageLayout = binding.imageView2.getLayoutParams();
                int curLay = DensityUtil.dip2px(requireContext(), curValue);
                imageLayout.height = curLay;
                imageLayout.width = curLay;
                binding.imageView2.setLayoutParams(imageLayout);
            });
            animatorSet.start();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHilinkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}