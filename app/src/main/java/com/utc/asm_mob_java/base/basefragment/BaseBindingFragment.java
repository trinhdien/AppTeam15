package com.utc.asm_mob_java.base.basefragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;

public abstract class BaseBindingFragment<T extends ViewDataBinding, K> extends BaseDataFragment {
    protected T mBinding;
    protected K mPresenter;
    protected AppCompatActivity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getIdLayoutRes(), container,
                false);
        initView();


        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public BaseBindingActivity<?, ?> getBaseBindingActivity() {
        if (getActivity() instanceof BaseBindingActivity) {
            return (BaseBindingActivity<?, ?>) getActivity();
        }
        return null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null && mPresenter instanceof BasePresenterForm) {
            ((BasePresenterForm<?>) mPresenter).unSubscription();
        }
    }

}
