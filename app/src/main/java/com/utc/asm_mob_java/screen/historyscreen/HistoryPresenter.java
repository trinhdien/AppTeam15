package com.utc.asm_mob_java.screen.historyscreen;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryPresenter extends BasePresenterForm<HistoryView> {
    public ObservableField<BaseRecyclerView<History>> mAdapterHistory;
    private List<History> mListHistory;

    public HistoryPresenter(Context mContext, HistoryView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        mListHistory = new ArrayList<>();
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mListHistory.add(new History());
        mAdapterHistory = new ObservableField<>(new BaseRecyclerView<>(mActivity, mListHistory, R.layout.item_history));
    }
}
