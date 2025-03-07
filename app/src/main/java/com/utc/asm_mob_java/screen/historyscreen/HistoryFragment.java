package com.utc.asm_mob_java.screen.historyscreen;

import android.os.Bundle;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.HistoryFragmentBinding;

public class HistoryFragment extends BaseBindingFragment<HistoryFragmentBinding, HistoryPresenter> implements HistoryView {
    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getIdLayoutRes() {
        return R.layout.history_fragment;
    }

    @Override
    protected void initData() {
        mPresenter = new HistoryPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showMessage() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErr() {

    }
}
