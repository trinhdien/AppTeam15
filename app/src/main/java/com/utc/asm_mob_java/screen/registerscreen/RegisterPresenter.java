package com.utc.asm_mob_java.screen.registerscreen;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.Cart;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.data.model.Order;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.screen.chooseaddress.ChooseAddressCallBack;
import com.utc.asm_mob_java.screen.chooseaddress.ChooseAddressFragment;
import com.utc.asm_mob_java.utils.Common;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterPresenter extends BasePresenterForm<RegisterView> {
    public ObservableField<String> name;
    public ObservableField<String> username;
    public ObservableField<String> email;
    public ObservableField<String> pass;
    public ObservableField<String> phone;
    public ObservableField<String> address;
    public ObservableField<Boolean> isShowPass;
    public ObservableField<Boolean> isShowPassAgain;
    public ObservableField<String> passAgain;
    public ObservableField<String> addressDetail;
    public ObservableField<String> birthday;
    public ObservableField<DeliveryAddress> deliveryAddressCurrent;
    private SharedPrefManager mSharedPrefManager;
    private List<User> mListUser;
    private User user;
    private ChooseAddressCallBack callBack;

    public RegisterPresenter(Context mContext, RegisterView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        name = new ObservableField<>();
        username = new ObservableField<>();
        email = new ObservableField<>();
        pass = new ObservableField<>();
        phone = new ObservableField<>();
        address = new ObservableField<>();
        isShowPass = new ObservableField<>(false);
        isShowPassAgain = new ObservableField<>(false);
        birthday = new ObservableField<>();
        passAgain = new ObservableField<>();
        addressDetail = new ObservableField<>();
        mListUser = new ArrayList<>();
        user = new User();
        deliveryAddressCurrent = new ObservableField<>();
        mSharedPrefManager = new SharedPrefManager(mActivity);
        mListUser = GsonUtils.String2ListObject(mSharedPrefManager.getListUser(), User[].class);
        callBack = deliveryAddress -> deliveryAddressCurrent.set(deliveryAddress);
    }

    public void onCancelClick() {
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    public void onConfirmClick() {
        if (validate()) {
            List<DeliveryAddress> listAddress = new ArrayList<>();
            List<Cart> listCart = new ArrayList<>();
            List<Order> listOrder = new ArrayList<>();
            user.setAvt(Constants.IMAGE_DEFAULT);
            user.setName(name.get());
            user.setUsername(username.get());
            user.setEmail(email.get());
            user.setPassword(pass.get());
            user.setPhone(phone.get());
            user.setListCart(listCart);
            user.setListOrder(listOrder);
            listAddress.add(deliveryAddressCurrent.get());
            user.setAddress(listAddress);
            user.setDateOfBirth(birthday.get());
            mListUser.add(user);
            mSharedPrefManager.saveListUser(GsonUtils.Object2String(mListUser));
            mView.showMessage(mActivity.getResources().getString(R.string.register_success));
            onCancelClick();
        }
    }

    private boolean validate() {
        if (CommonActivity.isNullOrEmpty(name.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.please_enter_name));
            return false;
        }
        if (CommonActivity.isNullOrEmpty(username.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.please_enter_username));
            return false;
        }
        if (CommonActivity.isNullOrEmpty(email.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.please_enter_email));
        }
        if (CommonActivity.isNullOrEmpty(pass.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.please_enter_pass));
            return false;
        }
        if (!Objects.equals(pass.get(), passAgain.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.dont_match_pass_or_username));
        }
        if (CommonActivity.isNullOrEmpty(phone.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.plesase_enter_number_phone));
        }
        if (CommonActivity.isNullOrEmpty(address.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.please_choose_address));
        }
        return true;
    }

    public void onClickAddress() {
        ChooseAddressFragment fragment = ChooseAddressFragment.newInstance(null);
        fragment.setCallBack(callBack);
        Common.replaceFragment(mActivity, R.id.main, fragment);
    }
}
