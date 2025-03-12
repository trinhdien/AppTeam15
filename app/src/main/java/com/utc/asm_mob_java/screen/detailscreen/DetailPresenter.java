package com.utc.asm_mob_java.screen.detailscreen;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.Option;

import java.util.ArrayList;
import java.util.List;

public class DetailPresenter extends BasePresenterForm<DetailView> {
    public ObservableField<BaseRecyclerView<Option>> adapterOption;
    public ObservableField<String> tvIndexImage;
    private List<Option> optionList;

    public DetailPresenter(Context mContext, DetailView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        tvIndexImage = new ObservableField<>("0/0");
        optionList = new ArrayList<>();
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        optionList.add(new Option());
        adapterOption = new ObservableField<>(new BaseRecyclerView<>(mActivity, optionList, R.layout.item_option));
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
            sliderLayout.stopAutoCycle();
            sliderLayout.addSlider(sliderView);
        }
        tvIndexImage.set("1/" + drawableImages.length);
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Khi đang cuộn
            }

            @Override
            public void onPageSelected(int position) {
                // Khi người dùng chuyển ảnh mới
                tvIndexImage.set(position + 1 + "/" + drawableImages.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Khi trạng thái cuộn thay đổi
            }
        });
    }

    public void showBottom() {
        mView.showBottomSheet();
    }

    public void closeBottom() {
        mView.closeBottomSheet();
    }

    @Override
    public void onCancel() {
        super.onCancel();
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }
}
