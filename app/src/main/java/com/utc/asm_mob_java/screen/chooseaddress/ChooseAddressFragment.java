package com.utc.asm_mob_java.screen.chooseaddress;

import android.os.Bundle;
import android.widget.Toast;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.databinding.DialogAddressBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;

public class ChooseAddressFragment extends BaseBindingFragment<DialogAddressBinding, ChooseAddressPresenter> implements ChooseAddressView {
    private Bundle bundle;
    private ChooseAddressCallBack callBack;

    public static ChooseAddressFragment newInstance(Bundle bundle) {
        ChooseAddressFragment fragment = new ChooseAddressFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showMessage(String mess) {
        Toast.makeText(mActivity, mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showErr(String err) {
        DialogUtils.showErrDialog(mActivity, err).show(mActivity.getSupportFragmentManager(), "");
    }

    @Override
    protected int getIdLayoutRes() {
        return R.layout.dialog_address;
    }

    @Override
    protected void initData() {
        mPresenter = new ChooseAddressPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        bundle = getArguments();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onConfirmClick(DeliveryAddress address) {
        if (callBack != null) {
            callBack.onChooseAddress(address);
        }
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public DeliveryAddress onGetDeliveryAddress() {
        if (bundle != null && bundle.containsKey(Constants.BundleKey.ITEM)) {
            return GsonUtils.String2Object(bundle.getString(Constants.BundleKey.ITEM), DeliveryAddress.class);
        }
        return null;
    }

    @Override
    public String getAction() {
        if (bundle != null && bundle.containsKey(Constants.BundleKey.ACTION)) {
            return bundle.getString(Constants.BundleKey.ACTION);
        }
        return "";
    }

    @Override
    public int getPosition() {
        if (bundle != null && bundle.containsKey(Constants.BundleKey.INT)) {
            return bundle.getInt(Constants.BundleKey.INT);
        }
        return 0;
    }

    @Override
    public void onEdit(DeliveryAddress address, int position) {
        callBack.onEditAddress(address, position);
    }

    public void setCallBack(ChooseAddressCallBack callBack) {
        this.callBack = callBack;
    }
}
