package com.utc.asm_mob_java.screen.historyscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.Order;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.screen.detailscreen.DetailActivity;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.DateUtils;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HistoryPresenter extends BasePresenterForm<HistoryView> {
    public ObservableBoolean isEmpty;
    public ObservableField<BaseRecyclerView<Order>> mAdapterOrder;
    private List<Order> mListOrder;
    private SharedPrefManager mSharedPrefManager;
    private User mUser;

    public HistoryPresenter(Context mContext, HistoryView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        isEmpty = new ObservableBoolean(false);
        mSharedPrefManager = new SharedPrefManager(mActivity);
        mListOrder = new ArrayList<>();
        mUser = GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class);
        mListOrder = Objects.requireNonNull(mUser).getListOrder();
        if (CommonActivity.isNullOrEmpty(mListOrder)) {
            isEmpty.set(true);
        }else {
            mListOrder.sort(sortByLatestTimestamp());
        }
        mAdapterOrder = new ObservableField<>(new BaseRecyclerView<>(mActivity, mListOrder, R.layout.item_history));
        Objects.requireNonNull(mAdapterOrder.get()).setListenerRecyclerView(new OnListenerRecyclerView<Order>() {
            @Override
            public void onClickItem(Order item, int position) {
                super.onClickItem(item, position);
                goToDetail(item.getProduct());
            }
        });
    }

    private void goToDetail(Product item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.ITEM, GsonUtils.Object2String(item));
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(Constants.BundleKey.ITEM, bundle);
        mActivity.startActivity(intent);
    }

    private static Comparator<Order> sortByLatestTimestamp() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.DATE_PATTERN_1);
        return (o1, o2) -> {
            try {
                Date date1 = formatter.parse(o1.getDateTime());
                Date date2 = formatter.parse(o2.getDateTime());
                if (date1 == null || date2 == null) {
                    return 0;
                }
                return date2.compareTo(date1);
            } catch (ParseException e) {
                return 0;
            }
        };
    }
}
