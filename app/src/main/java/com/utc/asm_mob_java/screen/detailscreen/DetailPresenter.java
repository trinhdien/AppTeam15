package com.utc.asm_mob_java.screen.detailscreen;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.Cart;
import com.utc.asm_mob_java.data.model.Option;
import com.utc.asm_mob_java.data.model.Order;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.databinding.LayoutBottomSheetBuyBinding;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DetailPresenter extends BasePresenterForm<DetailView> {
    public ObservableField<String> tvNumber;
    public ObservableField<Product> mProduct;
    public ObservableField<BaseRecyclerView<Option>> adapterOption;
    public ObservableField<String> tvIndexImage;
    public ObservableField<String> tvPrice;
    private List<Option> optionList;
    private SharedPrefManager mSharedPrefManager;
    private User user;

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
        tvPrice = new ObservableField<>(Objects.requireNonNull(mProduct.get()).getPrice());
        user = GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class);
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

    public void showBottomSheet(String actionCode) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        LayoutBottomSheetBuyBinding binding = LayoutBottomSheetBuyBinding.inflate(mActivity.getLayoutInflater());
        bottomSheetDialog.setContentView(binding.getRoot());
        binding.setPresenter(this);
        if ("0".equalsIgnoreCase(actionCode)) {
            binding.btnAddToCart.setVisibility(View.GONE);
            binding.btnBuyNow.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddToCart.setVisibility(View.VISIBLE);
            binding.btnBuyNow.setVisibility(View.GONE);
        }
        binding.tvNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Objects.requireNonNull(mProduct.get()).setNumberBuy(tvNumber.get());
                    tvPrice.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(mProduct.get()).getPrice()) * Integer.parseInt(Objects.requireNonNull(tvNumber.get()))));
                } catch (Exception e) {
                    tvNumber.set("1");
                    Objects.requireNonNull(mProduct.get()).setNumberBuy("1");
                    tvPrice.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(mProduct.get()).getPrice())));
                }

            }
        });
        binding.btnClose.setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fakeData() {
        mProduct.set(mView.getProduct());
    }

    public void onClickAddCart() {
        List<Cart> mListCart;
        if (CommonActivity.isNullOrEmpty(Objects.requireNonNull(user).getListCart())) {
            mListCart = new ArrayList<>();

        } else {
            mListCart = user.getListCart();
        }
        Cart cart = new Cart();
        cart.setProduct(mProduct.get());
        cart.setChoose(false);
        mListCart.add(cart);
        user.setListCart(mListCart);
        mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
        Toast.makeText(mActivity, R.string.add_to_cart_success, Toast.LENGTH_SHORT).show();
    }

    public void onRemove() {
        if (Integer.parseInt(Objects.requireNonNull(tvNumber.get())) != 1) {
            tvNumber.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(tvNumber.get())) - 1));
            tvPrice.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(mProduct.get()).getPrice()) * Integer.parseInt(Objects.requireNonNull(tvNumber.get()))));
            Objects.requireNonNull(mProduct.get()).setNumberBuy(tvNumber.get());

        }
    }

    public void onAdd() {
        tvNumber.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(tvNumber.get())) + 1));
        tvPrice.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(mProduct.get()).getPrice()) * Integer.parseInt(Objects.requireNonNull(tvNumber.get()))));
        Objects.requireNonNull(mProduct.get()).setNumberBuy(tvNumber.get());
    }

    public void onBuyBow() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = sdf.format(new Date());
        List<Order> mListOrder;
        if (CommonActivity.isNullOrEmpty(Objects.requireNonNull(user).getListOrder())) {
            mListOrder = new ArrayList<>();

        } else {
            mListOrder = user.getListOrder();
        }
        Order order = new Order();
        order.setProduct(mProduct.get());
        order.setDateTime(formattedDate);
        order.setStatus(mActivity.getResources().getString(R.string.delivering));
        mListOrder.add(order);
        user.setListOrder(mListOrder);
        mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.order_success), Toast.LENGTH_SHORT).show();
    }

}
