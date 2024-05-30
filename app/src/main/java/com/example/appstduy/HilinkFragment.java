package com.example.appstduy;

import android.animation.AnimatorSet;
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
                messageView.refreshText("妈妈：", texts.get(marker++));
                binding.messageSwitcher.showNext();
            } else {
                marker = 0;
                MessageView messageView = (MessageView) binding.messageSwitcher.getNextView();
                messageView.refreshText("妈妈：", texts.get(marker));
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
        binding.showUnread.setOnClickListener(v -> {
            ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, 300);
            valueAnimator1.setDuration(450);

            ValueAnimator valueAnimator2 = ValueAnimator.ofInt(40, 25);
            valueAnimator2.setDuration(450);

            TransitionDrawable transitionDrawable = (TransitionDrawable) requireContext().getDrawable(R.drawable.transition);
            binding.messageView.setBackground(transitionDrawable);
            transitionDrawable.startTransition(450);

            ViewGroup.LayoutParams layoutParams = binding.messageView.getLayoutParams();
            int height = layoutParams.height;

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(valueAnimator1, valueAnimator2);

            valueAnimator1.addUpdateListener(animation -> {
                int curValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams1 = binding.messageView.getLayoutParams();
                layoutParams1.height = height + curValue;
                binding.messageView.setLayoutParams(layoutParams1);
            });

            valueAnimator2.addUpdateListener(animation -> {
                int curValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams imageLayout = binding.imageView.getLayoutParams();
                int curLay = DensityUtil.dip2px(requireContext(), curValue);
                imageLayout.height = curLay;
                imageLayout.width = curLay;
                binding.imageView.setLayoutParams(imageLayout);
            });
            animatorSet.start();

            binding.messageView.setVisibility(View.VISIBLE);
            binding.messageView2.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHilinkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}