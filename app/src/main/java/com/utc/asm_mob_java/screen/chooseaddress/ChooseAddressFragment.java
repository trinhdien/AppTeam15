package com.utc.asm_mob_java.screen.chooseaddress;

import android.os.Bundle;
import android.widget.Toast;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.databinding.DialogAddressBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;

public class ChooseAddressFragment extends BaseBindingFragment<DialogAddressBinding, ChooseAddressPresenter> implements ChooseAddressView {
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
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onConfirmClick(DeliveryAddress address) {
        if(callBack != null){
            callBack.onChooseAddress(address);
        }
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    public void setCallBack(ChooseAddressCallBack callBack) {
        this.callBack = callBack;
    }
}
