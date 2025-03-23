package com.utc.asm_mob_java.screen.chooseaddress;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.basefragment.BaseBindingFragment;
import com.utc.asm_mob_java.databinding.DialogAddressBinding;
import com.utc.asm_mob_java.dialog.dialogconfirm.ConfirmDialog;

public class ChooseAddressFragment extends BaseBindingFragment<DialogAddressBinding, ChooseAddressPresenter> implements ChooseAddressView {
    public static ChooseAddressFragment newInstance() {
        return new ChooseAddressFragment();
    }

    @Override
    public void showMessage() {

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
    public void showErr() {
        ConfirmDialog confirmDialog = new ConfirmDialog(null, mActivity.getResources()
                .getString(R.string.error),
                mActivity.getResources().getString(R.string.error_get_province),
                true, "", mActivity.getResources().getString(R.string.ok));
        confirmDialog.show(requireActivity().getSupportFragmentManager(), "");
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
}
