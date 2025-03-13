package com.utc.asm_mob_java.screen.morescreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.MainActivity;
import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.data.model.User;
import com.utc.asm_mob_java.utils.Common;
import com.utc.asm_mob_java.utils.GsonUtils;
import com.utc.asm_mob_java.utils.Logger;
import com.utc.asm_mob_java.utils.SharedPrefManager;
import com.utc.asm_mob_java.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MorePresenter extends BasePresenterForm<MoreView> {
    public ObservableField<User> mUser;
    public ObservableField<BaseRecyclerView<DeliveryAddress>> mAdapterAddress;
    private List<DeliveryAddress> mListAddress;
    private SharedPrefManager mSharedPrefManager;

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
            // Xử lý chỉnh sửa thông tin
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
}
