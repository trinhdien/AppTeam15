package com.utc.asm_mob_java.screen.morescreen;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.DeliveryAddress;

import java.util.ArrayList;
import java.util.List;

public class MorePresenter extends BasePresenterForm<MoreView> {
    public ObservableField<BaseRecyclerView<DeliveryAddress>> mAdapterAddress;
    private List<DeliveryAddress> mListAddress;

    public MorePresenter(Context mContext, MoreView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        mListAddress = new ArrayList<>();
        mListAddress.add(new DeliveryAddress());
        mListAddress.add(new DeliveryAddress());
        mListAddress.add(new DeliveryAddress());
        mListAddress.add(new DeliveryAddress());
        mListAddress.add(new DeliveryAddress());
        mListAddress.add(new DeliveryAddress());
        mListAddress.add(new DeliveryAddress());
        mAdapterAddress = new ObservableField<>(new BaseRecyclerView<>(mActivity, mListAddress, R.layout.item_delivery_address));
    }
}
