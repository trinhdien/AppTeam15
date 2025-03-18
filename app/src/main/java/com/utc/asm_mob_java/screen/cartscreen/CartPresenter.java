package com.utc.asm_mob_java.screen.cartscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.callback.OnListenerItemRecyclerView;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.Cart;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.screen.detailscreen.DetailActivity;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartPresenter extends BasePresenterForm<CartView> {
    public ObservableBoolean isChooseAll;
    public ObservableField<BaseRecyclerView<Cart>> mAdapterCart;
    public ObservableField<String> tvPrice;
    private List<Cart> mListCart;
    private SharedPrefManager mSharedPrefManager;
    private User user;

    public CartPresenter(Context mContext, CartView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        isChooseAll = new ObservableBoolean(false);
        mListCart = new ArrayList<>();
        mSharedPrefManager = new SharedPrefManager(mActivity);
        user = GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class);
        mListCart = Objects.requireNonNull(user).getListCart();
        mAdapterCart = new ObservableField<>(new BaseRecyclerView<>(mActivity, mListCart, R.layout.item_cart));
        tvPrice = new ObservableField<>("0");
        Objects.requireNonNull(mAdapterCart.get()).setListenerRecyclerView(new OnListenerRecyclerView<Cart>() {
            @Override
            public void onClickButton(Cart item, int position) {
                super.onClickButton(item, position);
                item.setChoose(!item.isChoose());
                Objects.requireNonNull(mAdapterCart.get()).notifyItemChanged(position, item);
                updatePrice();
            }

            @Override
            public void onClickItem1(Cart item, int position) {
                super.onClickItem1(item, position);
                if (Integer.parseInt(Objects.requireNonNull(item.getProduct().getNumberBuyTemp())) != 1) {
                    item.getProduct().setNumberBuyTemp((String.valueOf(Integer.parseInt(Objects.requireNonNull(item.getProduct().getNumberBuyTemp())) - 1)));
                    item.getProduct().setPriceTemp(String.valueOf(Integer.parseInt(item.getProduct().getPrice()) * Integer.parseInt(item.getProduct().getNumberBuyTemp())));
                    item.getProduct().setNumberBuy(item.getProduct().getNumberBuyTemp());
                    Objects.requireNonNull(mAdapterCart.get()).notifyItemChanged(position, item);
                    updatePrice();
                }
            }

            @Override
            public void onClickItem2(Cart item, int position) {
                super.onClickItem2(item, position);
                item.getProduct().setNumberBuyTemp((String.valueOf(Integer.parseInt(Objects.requireNonNull(item.getProduct().getNumberBuyTemp())) + 1)));
                item.getProduct().setPriceTemp(String.valueOf(Integer.parseInt(item.getProduct().getPrice()) * Integer.parseInt(item.getProduct().getNumberBuyTemp())));
                item.getProduct().setNumberBuy(item.getProduct().getNumberBuyTemp());
                Objects.requireNonNull(mAdapterCart.get()).notifyItemChanged(position, item);
                updatePrice();
            }

            @Override
            public void afterTextChanged(Cart item, int position, Editable editable) {
                super.afterTextChanged(item, position, editable);
                try {
                    item.getProduct().setNumberBuy(item.getProduct().getNumberBuyTemp());
                    item.getProduct().setPriceTemp(String.valueOf(Integer.parseInt(item.getProduct().getPrice()) * Integer.parseInt(item.getProduct().getNumberBuyTemp())));
                } catch (Exception e) {
                    item.getProduct().setNumberBuy("1");
                    item.getProduct().setPriceTemp(String.valueOf(Integer.parseInt(item.getProduct().getPrice())));
                }
                Objects.requireNonNull(mAdapterCart.get()).notifyItemChanged(position, item);
                updatePrice();
            }

            @Override
            public void onClickItem4(Cart item, int position) {
                super.onClickItem4(item, position);
                goToDetail(item.getProduct());
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onChooseAll() {
        isChooseAll.set(!isChooseAll.get());
        for (Cart cart : mListCart) {
            cart.setChoose(isChooseAll.get());
            Objects.requireNonNull(mAdapterCart.get()).notifyDataSetChanged();
        }
        updatePrice();
    }

    private void updatePrice() {
        int price = 0;
        for (Cart cart : mListCart) {
            if (cart.isChoose()) {
                price += Integer.parseInt(cart.getProduct().getPriceTemp());
            }
        }
        tvPrice.set(String.valueOf(price));
    }

    private void goToDetail(Product item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.ITEM, GsonUtils.Object2String(item));
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(Constants.BundleKey.ITEM, bundle);
        mActivity.startActivity(intent);
    }
}
