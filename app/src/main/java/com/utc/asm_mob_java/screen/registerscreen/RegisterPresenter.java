package com.utc.asm_mob_java.screen.registerscreen;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.Cart;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.data.model.Order;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.dialog.DialogUtils;
import com.utc.asm_mob_java.screen.chooseaddress.ChooseAddressCallBack;
import com.utc.asm_mob_java.screen.chooseaddress.ChooseAddressFragment;
import com.utc.asm_mob_java.utils.Common;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;
import com.utc.asm_mob_java.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterPresenter extends BasePresenterForm<RegisterView> {
    public ObservableField<String> name;
    public ObservableField<String> username;
    public ObservableField<String> email;
    public ObservableField<String> pass;
    public ObservableField<String> phone;
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
        isShowPass = new ObservableField<>(false);
        isShowPassAgain = new ObservableField<>(false);
        birthday = new ObservableField<>();
        passAgain = new ObservableField<>();
        addressDetail = new ObservableField<>();
        user = new User();
        deliveryAddressCurrent = new ObservableField<>();
        mSharedPrefManager = new SharedPrefManager(mActivity);
        if (GsonUtils.String2ListObject(mSharedPrefManager.getListUser(), User[].class) == null) {
            mListUser = new ArrayList<>();
        } else {
            mListUser = new ArrayList<>(Objects.requireNonNull(GsonUtils.String2ListObject(mSharedPrefManager.getListUser(), User[].class)));
        }
        callBack = new ChooseAddressCallBack() {
            @Override
            public void onChooseAddress(DeliveryAddress deliveryAddress) {
                super.onChooseAddress(deliveryAddress);
                deliveryAddressCurrent.set(deliveryAddress);
            }
        };
    }

    public void onCancelClick() {
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    public void onConfirmClick() {
        if (validate()) {
            if (mListUser == null) {
                mListUser = new ArrayList<>();
            }
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
            Objects.requireNonNull(deliveryAddressCurrent.get()).setDefault(true);
            listAddress.add(deliveryAddressCurrent.get());
            user.setAddress(listAddress);
            user.setDateOfBirth(birthday.get());
            mListUser.add(user);
            mSharedPrefManager.saveListUser(GsonUtils.Object2String(mListUser));
            mView.showMessage(mActivity.getResources().getString(R.string.register_success));
            mView.onRegisterSuccess();
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
            mView.showMessage("Mật khẩu nhập lại không khớp");
            return false;
        }
        if (CommonActivity.isNullOrEmpty(phone.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.plesase_enter_number_phone));
            return false;
        }
        if (CommonActivity.isNullOrEmpty(deliveryAddressCurrent.get())) {
            mView.showMessage(mActivity.getResources().getString(R.string.please_choose_address));
            return false;
        }
        if (CommonActivity.isNullOrEmpty(birthday.get())) {
            mView.showMessage(mActivity.getString(R.string.please_choose_birth));
            return false;
        }
        if (!StringUtils.isValidEmail(email.get())) {
            mView.showMessage(mActivity.getString(R.string.email_not_true));
            return false;
        }
        if (StringUtils.isValidVietnamPhoneNumber(phone.get())) {
            mView.showMessage(mActivity.getString(R.string.number_phone_not_true));
            return false;
        }
        if (!StringUtils.isValidPassword(pass.get())) {
            DialogUtils.showErrDialog(mActivity,
                            "Mật khẩu không hợp lệ\n" +
                                    " Ít nhất 1 chữ cái (A-Z, a-z)\n" +
                                    "Ít nhất 1 số (0-9)\n" +
                                    "Ít nhất 1 ký tự đặc biệt (@, $, !, %, *, ?, &)\n" +
                                    "Độ dài từ 8 đến 20 ký tự")
                    .show(mActivity.getSupportFragmentManager(), "");
            return false;
        }
//        if (StringUtils.isValidUsername(username.get())) {
//            mView.showMessage(mActivity.getString(R.string.name_not_true));
//            return false;
//        }
        return true;
    }

    public void onClickAddress() {
        ChooseAddressFragment fragment = ChooseAddressFragment.newInstance(null);
        fragment.setCallBack(callBack);
        Common.replaceFragment(mActivity, R.id.main, fragment);
    }

    public void onChooseDate() {
        DialogUtils.showDatePickerDialog(mActivity, date -> birthday.set(date));
    }
}
