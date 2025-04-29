package com.utc.asm_mob_java.screen.detailscreen;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
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
import com.utc.asm_mob_java.dialog.BaseListener;
import com.utc.asm_mob_java.dialog.DialogUtils;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.DateUtils;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

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
    private BottomSheetDialog bottomSheetDialog;

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
        bottomSheetDialog = new BottomSheetDialog(mActivity);
        fakeData();
    }

    public void loadLocalImages(SliderLayout sliderLayout) {
        int[] drawableImages = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_3, R.drawable.poster_4, R.drawable.poster_5, R.drawable.poster_6, R.drawable.poster_7, R.drawable.poster_8};
        for (int image : drawableImages) {
            DefaultSliderView sliderView = new DefaultSliderView(mActivity);
            sliderView.image(image);
            sliderView.setProgressBarVisible(true);
            sliderView.setRequestOption(new RequestOptions().centerCrop());
            sliderLayout.stopAutoCycle();
            sliderLayout.addSlider(sliderView);
        }
        tvIndexImage.set("1/" + drawableImages.length);
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndexImage.set(position + 1 + "/" + drawableImages.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCancel() {
        super.onCancel();
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    public void showBottomSheet(String actionCode) {
        LayoutBottomSheetBuyBinding binding = LayoutBottomSheetBuyBinding.inflate(mActivity.getLayoutInflater());
        bottomSheetDialog.setContentView(binding.getRoot());
        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });
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
        BaseListener confirmListener = new BaseListener() {
            @Override
            public void onConfirm() {
                List<Cart> mListCart;
                if (CommonActivity.isNullOrEmpty(Objects.requireNonNull(user).getListCart())) {
                    mListCart = new ArrayList<>();

                } else {
                    mListCart = user.getListCart();
                }
                Cart cart = new Cart();
                cart.setProduct(mProduct.get());
                cart.setChoose(false);
                cart.setDateTime(DateUtils.convertDateToStringPattern(new Date(), DateUtils.DATE_PATTERN_1));
                mListCart.add(cart);
                user.setListCart(mListCart);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.add_to_cart_success), Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.cancel_add_to_cart_success), Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        };
        DialogUtils.showConfirmDialog(confirmListener, mActivity, null,mActivity.getResources().getString(R.string.confirm_add_to_cart)).show(mActivity.getSupportFragmentManager(), "");
    }

    public void onRemove() {
        if (Integer.parseInt(Objects.requireNonNull(tvNumber.get())) != 1) {
            tvNumber.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(tvNumber.get())) - 1));
            tvPrice.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(mProduct.get()).getPrice()) * Integer.parseInt(Objects.requireNonNull(tvNumber.get()))));
            Objects.requireNonNull(mProduct.get()).setNumberBuy(tvNumber.get());
            Objects.requireNonNull(mProduct.get()).setNumberBuyTemp(tvNumber.get());

        }
    }

    public void onAdd() {
        tvNumber.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(tvNumber.get())) + 1));
        tvPrice.set(String.valueOf(Integer.parseInt(Objects.requireNonNull(mProduct.get()).getPrice()) * Integer.parseInt(Objects.requireNonNull(tvNumber.get()))));
        Objects.requireNonNull(mProduct.get()).setNumberBuy(tvNumber.get());
        Objects.requireNonNull(mProduct.get()).setNumberBuyTemp(tvNumber.get());
    }

    public void onBuyBow() {
        BaseListener confirmListener = new BaseListener() {
            @Override
            public void onConfirm() {
                @SuppressLint("SimpleDateFormat")
                List<Order> mListOrder;
                if (CommonActivity.isNullOrEmpty(Objects.requireNonNull(user).getListOrder())) {
                    mListOrder = new ArrayList<>();

                } else {
                    mListOrder = user.getListOrder();
                }
                Order order = new Order();
                order.setProduct(mProduct.get());
                order.setDateTime(DateUtils.convertDateToStringPattern(new Date(),DateUtils.DATE_PATTERN_1));
                order.setStatus(mActivity.getResources().getString(R.string.delivering));
                mListOrder.add(order);
                user.setListOrder(mListOrder);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.order_success), Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.cancel_order_success), Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        };
        DialogUtils.showConfirmDialog(confirmListener, mActivity, null,mActivity.getResources().getString(R.string.confirm_buy_product)).show(mActivity.getSupportFragmentManager(),"");
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view.clearFocus();
    }
}
