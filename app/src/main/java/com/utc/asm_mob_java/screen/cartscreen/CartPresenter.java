package com.utc.asm_mob_java.screen.cartscreen;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartPresenter extends BasePresenterForm<CartView> {
    public ObservableField<BaseRecyclerView<Cart>> mAdapterCart;
    private List<Cart> mListCart;
    public CartPresenter(Context mContext, CartView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        mListCart = new ArrayList<>();
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mListCart.add(new Cart());
        mAdapterCart = new ObservableField<>(new BaseRecyclerView<>(mActivity,mListCart, R.layout.item_cart
        ));
    }
}
