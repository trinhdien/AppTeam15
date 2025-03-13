package com.utc.asm_mob_java.screen.homescreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.screen.detailscreen.DetailActivity;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePresenter extends BasePresenterForm<HomeView> {
    public ObservableField<BaseRecyclerView<Product>> mAdapter;
    private List<Product> mList;
    private SharedPrefManager mSharedPrefManager;

    public HomePresenter(Context mContext, HomeView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerView<>(mActivity, mList, R.layout.item_product));
        Objects.requireNonNull(mAdapter.get()).setListenerRecyclerView(new OnListenerRecyclerView<Product>() {
            @Override
            public void onClickItem(Product item, int position) {
                super.onClickItem(item, position);
                goToDetail(item);
            }
        });
        mSharedPrefManager = new SharedPrefManager(mActivity);
        fakeData();
    }

    public void loadLocalImages(SliderLayout sliderLayout) {
        int[] drawableImages = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_3, R.drawable.poster_4, R.drawable.poster_9, R.drawable.poster_6, R.drawable.poster_7, R.drawable.poster_8};
        for (int image : drawableImages) {
            DefaultSliderView sliderView = new DefaultSliderView(mActivity);
            sliderView.image(image);
            sliderView.setProgressBarVisible(true);
            sliderView.setOnSliderClickListener(slider -> {

            });

            sliderView.setRequestOption(new RequestOptions().centerCrop());
            sliderLayout.addSlider(sliderView);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fakeData() {
        if (!CommonActivity.isNullOrEmpty(mSharedPrefManager.getListProduct())) {
            mList.addAll(Objects.requireNonNull(GsonUtils.String2ListObject(mSharedPrefManager.getListProduct(), Product[].class)));
        } else {
            mList.add(new Product(R.drawable.poster_1, "Gấu bông 1", "200000", "1000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_2, "Gấu bông 2", "300000", "2000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_3, "Gấu bông 3", "400000", "3000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_4, "Gấu bông 4", "500000", "4000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_5, "Gấu bông 5", "600000", "5000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_6, "Gấu bông 6", "700000", "6000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_7, "Gấu bông 7", "800000", "7000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mList.add(new Product(R.drawable.poster_8, "Gấu bông 8", "900000", "8000", "Đây 1 chú gấu siêu cute hãy sở hữu nó"));
            mSharedPrefManager.saveListProduct(GsonUtils.Object2String(mList));
        }

        Objects.requireNonNull(mAdapter.get()).notifyDataSetChanged();
    }

    private void goToDetail(Product item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.ITEM, GsonUtils.Object2String(item));
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(Constants.BundleKey.ITEM, bundle);
        mActivity.startActivity(intent);
    }
}