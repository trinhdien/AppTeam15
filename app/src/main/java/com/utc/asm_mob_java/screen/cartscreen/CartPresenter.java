package com.utc.asm_mob_java.screen.cartscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.Cart;
import com.utc.asm_mob_java.data.model.Order;
import com.utc.asm_mob_java.data.model.Product;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.dialog.BaseListener;
import com.utc.asm_mob_java.dialog.DialogUtils;
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

public class CartPresenter extends BasePresenterForm<CartView> {
    public ObservableBoolean isChooseAll;
    public ObservableBoolean isEmpty;
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
        isEmpty = new ObservableBoolean(false);
        isChooseAll = new ObservableBoolean(false);
        mListCart = new ArrayList<>();
        mSharedPrefManager = new SharedPrefManager(mActivity);
        user = GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class);
        mListCart = Objects.requireNonNull(user).getListCart();

        if (CommonActivity.isNullOrEmpty(mListCart)) {
            isEmpty.set(true);
        }else {
            mListCart.sort(sortByLatestTimestamp());
        }
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

            @Override
            public void onClickItem(Cart item, int position) {
                super.onClickItem(item, position);
                BaseListener confirmListener = new BaseListener() {
                    @Override
                    public void onConfirm() {
                        super.onConfirm();
                        mListCart.remove(item);
                        user.setListCart(mListCart);
                        mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
                        Objects.requireNonNull(mAdapterCart.get()).notifyItemChanged(position);
                        if (CommonActivity.isNullOrEmpty(mListCart)) {
                            isEmpty.set(true);
                        }
                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.delete_product_in_cart), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                };
                DialogUtils.showConfirmDialog(confirmListener, mActivity, null, mActivity.getResources().getString(R.string.confirm_del_product_in_cart)).show(mActivity.getSupportFragmentManager(), "");
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
        boolean isChooseAll = true;
        for (Cart cart : mListCart) {
            if (cart.isChoose()) {
                price += Integer.parseInt(cart.getProduct().getPriceTemp());
            } else {
                isChooseAll = false;
            }
        }
        this.isChooseAll.set(isChooseAll);
        tvPrice.set(String.valueOf(price));
    }

    private void goToDetail(Product item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.ITEM, GsonUtils.Object2String(item));
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(Constants.BundleKey.ITEM, bundle);
        mActivity.startActivity(intent);
    }

    public void onBuy() {
        List<Cart> lstCartBuy = new ArrayList<>();
        for (Cart cart : mListCart) {
            if (cart.isChoose()) {
                lstCartBuy.add(cart);
            }
        }
        BaseListener confirmListener = new BaseListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onConfirm() {
                @SuppressLint("SimpleDateFormat")
                List<Order> mListOrder;
                if (CommonActivity.isNullOrEmpty(Objects.requireNonNull(user).getListOrder())) {
                    mListOrder = new ArrayList<>();

                } else {
                    mListOrder = user.getListOrder();
                }
                for (Cart cart : lstCartBuy) {
                    Order order = new Order();
                    order.setProduct(cart.getProduct());
                    order.setDateTime(DateUtils.convertDateToStringPattern(new Date(), DateUtils.DATE_PATTERN_1));
                    order.setStatus(mActivity.getResources().getString(R.string.delivering));
                    mListOrder.add(order);
                    mListCart.remove(cart);
                }
                user.setListOrder(mListOrder);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(user));
                Objects.requireNonNull(mAdapterCart.get()).notifyDataSetChanged();
                if (CommonActivity.isNullOrEmpty(mListCart)) {
                    isEmpty.set(true);
                    isChooseAll.set(false);
                }
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.order_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.cancel_order_success), Toast.LENGTH_SHORT).show();
            }
        };
        DialogUtils.showConfirmDialog(confirmListener, mActivity, null, mActivity.getResources().getString(R.string.confirm_buy_product)).show(mActivity.getSupportFragmentManager(), "");
    }

    private static Comparator<Cart> sortByLatestTimestamp() {
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
