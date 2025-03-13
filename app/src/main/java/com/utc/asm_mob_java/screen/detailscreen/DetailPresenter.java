package com.utc.asm_mob_java.screen.detailscreen;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.Option;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.databinding.LayoutBottomSheetBuyBinding;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailPresenter extends BasePresenterForm<DetailView> {
    public ObservableField<String> tvNumber;
    public ObservableField<Product> mProduct;
    public ObservableField<BaseRecyclerView<Option>> adapterOption;
    public ObservableField<String> tvIndexImage;
    private List<Option> optionList;
    private SharedPrefManager mSharedPrefManager;

    public DetailPresenter(Context mContext, DetailView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        mProduct = new ObservableField<>();
        tvIndexImage = new ObservableField<>("0/0");
        optionList = new ArrayList<>();
        adapterOption = new ObservableField<>(new BaseRecyclerView<>(mActivity, optionList, R.layout.item_option));
        mSharedPrefManager = new SharedPrefManager(mActivity);
        tvNumber = new ObservableField<>("1");
        mProduct.set(mView.getProduct());
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
            sliderLayout.stopAutoCycle();
            sliderLayout.addSlider(sliderView);
        }
        tvIndexImage.set("1/" + drawableImages.length);
    }

    @Override
    public void onCancel() {
        super.onCancel();
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    public void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        LayoutBottomSheetBuyBinding binding = LayoutBottomSheetBuyBinding.inflate(mActivity.getLayoutInflater());
        bottomSheetDialog.setContentView(binding.getRoot());
        binding.setPresenter(this);
        binding.tvNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.btnClose.setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fakeData() {
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
        Objects.requireNonNull(adapterOption.get()).notifyDataSetChanged();
        mProduct.set(mView.getProduct());
    }

    public void onClickAddCart() {
        User user = GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class);
        List<Product> mListCart;
        if (CommonActivity.isNullOrEmpty(Objects.requireNonNull(user).getListCart())) {
            mListCart = new ArrayList<>();

        } else {
            mListCart = user.getListCart();
        }
        user.setListCart(mListCart);
        mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
    }

    public void onRemove() {
        if (Integer.parseInt(Objects.requireNonNull(tvNumber.get())) != 1) {
            tvNumber.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(tvNumber.get())) - 1));
        }
    }

    public void onAdd() {
        tvNumber.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(tvNumber.get())) + 1));
    }
}
