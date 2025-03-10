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
import com.utc.asm_mob_java.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePresenter extends BasePresenterForm<HomeView> {
    public ObservableField<BaseRecyclerView<Product>> mAdapter;
    private List<Product> mList;

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
        fakeData();
    }

    public void loadLocalImages(SliderLayout sliderLayout) {
        int[] drawableImages = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_4, R.drawable.poster_6, R.drawable.poster_7, R.drawable.poster_8};
        for (int image : drawableImages) {
            DefaultSliderView sliderView = new DefaultSliderView(mActivity);
            sliderView.image(image);
            sliderView.setProgressBarVisible(true);
            sliderView.setOnSliderClickListener(slider -> {

            });

            sliderView.setRequestOption(new RequestOptions().centerCrop());
//            sliderLayout.stopAutoCycle();
            sliderLayout.addSlider(sliderView);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fakeData() {
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        mList.add(new Product());
        Objects.requireNonNull(mAdapter.get()).notifyDataSetChanged();
    }

    private void goToDetail(Product item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BundleKey.ITEM, item);
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(Constants.BundleKey.ITEM, bundle);
        mActivity.startActivity(intent);
    }
}