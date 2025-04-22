package com.utc.asm_mob_java.screen.morescreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.MainActivity;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.dialog.BaseListener;
import com.utc.asm_mob_java.dialog.DialogUtils;
import com.utc.asm_mob_java.dialog.dialogeditprofile.DialogEditProfile;
import com.utc.asm_mob_java.dialog.dialogeditprofile.EditProfileListener;
import com.utc.asm_mob_java.screen.chooseaddress.ChooseAddressCallBack;
import com.utc.asm_mob_java.screen.chooseaddress.ChooseAddressFragment;
import com.utc.asm_mob_java.utils.Common;
import com.utc.asm_mob_java.utils.Config;
import com.utc.asm_mob_java.utils.Constants;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.SharedPrefManager;
import com.utc.asm_mob_java.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MorePresenter extends BasePresenterForm<MoreView> {
    public ObservableField<User> mUser;
    public ObservableField<BaseRecyclerView<DeliveryAddress>> mAdapterAddress;
    private List<DeliveryAddress> mListAddress;
    private SharedPrefManager mSharedPrefManager;
    private ChooseAddressCallBack callBack;

    public MorePresenter(Context mContext, MoreView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        mUser = new ObservableField<>();
        mSharedPrefManager = new SharedPrefManager(mActivity);
        mListAddress = new ArrayList<>();
        mAdapterAddress = new ObservableField<>(new BaseRecyclerView<>(mActivity, mListAddress, R.layout.item_delivery_address));
        fakeData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fakeData() {
        Objects.requireNonNull(mAdapterAddress.get()).notifyDataSetChanged();
        mUser.set(GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class));
        mView.setImageBitMap(StringUtils.convertBase64ToBitmap(Objects.requireNonNull(mUser.get()).getAvt()));
        mListAddress.addAll(Objects.requireNonNull(mUser.get()).getAddress());
        Objects.requireNonNull(mAdapterAddress.get()).notifyDataSetChanged();
        Objects.requireNonNull(mAdapterAddress.get()).setListenerRecyclerView(new OnListenerRecyclerView<DeliveryAddress>() {
            @Override
            public void onClickItem(DeliveryAddress item, int position) {
                super.onClickItem(item, position);
                for (DeliveryAddress address : mListAddress) {
                    address.setDefault(false);
                }
                item.setDefault(true);
                Objects.requireNonNull(mAdapterAddress.get()).notifyDataSetChanged();
            }

            @Override
            public void onClickEdit(DeliveryAddress item, int position) {
                super.onClickEdit(item, position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BundleKey.ITEM, GsonUtils.Object2String(item));
                bundle.putInt(Constants.BundleKey.INT, position);
                bundle.putString(Constants.BundleKey.ACTION, Config.ACTION_EDIT_ADDRESS);
                ChooseAddressFragment fragment = ChooseAddressFragment.newInstance(bundle);
                fragment.setCallBack(callBack);
                Common.replaceFragment(mActivity, R.id.main, fragment);
            }

            @Override
            public void onClickDelete(DeliveryAddress item, int position) {
                super.onClickDelete(item, position);
                DialogUtils.showConfirmDialog(new BaseListener() {
                                                  @Override
                                                  public void onConfirm() {
                                                      super.onConfirm();
                                                      mListAddress.remove(item);
                                                      Objects.requireNonNull(mUser.get()).setAddress(mListAddress);
                                                      mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(mUser.get()));
                                                      Objects.requireNonNull(mAdapterAddress.get()).notifyItemChanged(position);
                                                  }
                                              }, mActivity, mActivity.getResources().getString(R.string.notification),
                                mActivity.getResources().getString(R.string.confirm_delete_address))
                        .show(mActivity.getSupportFragmentManager(), "");
            }
        });
        callBack = new ChooseAddressCallBack() {
            @Override
            public void onChooseAddress(DeliveryAddress deliveryAddress) {
                super.onChooseAddress(deliveryAddress);
                mListAddress.add(deliveryAddress);
                Objects.requireNonNull(mUser.get()).setAddress(mListAddress);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(mUser.get()));
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.add_address_success), Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(mAdapterAddress.get()).notifyDataSetChanged();
            }

            @Override
            public void onEditAddress(DeliveryAddress deliveryAddress, int position) {
                super.onEditAddress(deliveryAddress, position);
                mListAddress.set(position, deliveryAddress);
                Objects.requireNonNull(mUser.get()).setAddress(mListAddress);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(mUser.get()));
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.add_address_success), Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(mAdapterAddress.get()).notifyDataSetChanged();
            }
        };
    }

    public void showCustomMenu(View anchorView) {
        @SuppressLint("InflateParams")
        View popupView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_menu_setting, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);

        // Tắt Popup khi chạm ngoài
        popupWindow.setOutsideTouchable(true);
        // Lấy vị trí của nút nhấn
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        // Lấy vị trí của nút nhấn
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], // Giữ nguyên vị trí ngang
                location[1] + anchorView.getHeight()); // Đặt dưới nút bấm

        // Xử lý sự kiện click trong menu
        popupView.findViewById(R.id.menu_edit_profile).setOnClickListener(v -> {
            showDialogEditProfile();
            popupWindow.dismiss();
        });

        popupView.findViewById(R.id.menu_logout).setOnClickListener(v -> {
            // Xử lý đăng xuất
            mSharedPrefManager.saveUserLogin(null);
            Intent intent = new Intent(mActivity, MainActivity.class);
            mActivity.startActivity(intent);
            mActivity.finish();
            popupWindow.dismiss();
        });
    }

    public void showDialogEditProfile() {
        EditProfileListener listener = new EditProfileListener() {

            @Override
            public void onConfirm(String name, String email, String numberPhone, String birthday) {
                Objects.requireNonNull(mUser.get()).setName(name);
                Objects.requireNonNull(mUser.get()).setEmail(email);
                Objects.requireNonNull(mUser.get()).setPhone(numberPhone);
                Objects.requireNonNull(mUser.get()).setDateOfBirth(birthday);
                mSharedPrefManager.saveUserLogin(GsonUtils.Object2String(mUser.get()));
                mUser.set(GsonUtils.String2Object(mSharedPrefManager.getUserLogin(), User.class));
            }

            @Override
            public void onCancel() {

            }
        };
        DialogEditProfile dialog = new DialogEditProfile(listener,
                Objects.requireNonNull(mUser.get()).getName(),
                Objects.requireNonNull(mUser.get()).getEmail(),
                Objects.requireNonNull(mUser.get()).getPhone(),
                Objects.requireNonNull(mUser.get()).getDateOfBirth());
        dialog.show(mActivity.getSupportFragmentManager(), "");
    }

    public void onClickAddress() {
        ChooseAddressFragment fragment = ChooseAddressFragment.newInstance(null);
        fragment.setCallBack(callBack);
        Common.replaceFragment(mActivity, R.id.main, fragment);
    }
}
