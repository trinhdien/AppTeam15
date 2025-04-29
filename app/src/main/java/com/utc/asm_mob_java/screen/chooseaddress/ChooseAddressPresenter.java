package com.utc.asm_mob_java.screen.chooseaddress;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.DeliveryAddress;
import com.utc.asm_mob_java.data.model.District;
import com.utc.asm_mob_java.data.model.Province;
import com.utc.asm_mob_java.data.model.Ward;
import com.utc.asm_mob_java.data.source.repository.AddressRepository;
import com.utc.asm_mob_java.data.source.response.GetAddressResponse;
import com.utc.asm_mob_java.dialog.dialogfilter.DialogFilter;
import com.utc.asm_mob_java.dialog.dialogfilter.DialogFilterListener;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Config;
import com.utc.asm_mob_java.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChooseAddressPresenter extends BasePresenterForm<ChooseAddressView> {
    public ObservableField<Province> currentProvince;
    public ObservableField<District> currentDistrict;
    public ObservableField<Ward> currentWard;
    public ObservableField<String> address;
    public ObservableField<String> addressDetail;
    public ObservableField<String> numberPhone;
    public ObservableField<String> name;
    private DeliveryAddress deliveryAddress;
    private List<Province> mListProvince;
    private List<District> mListDistrict;
    private List<Ward> mListWard;
    private List<District> mListDistrictTemp;
    private List<Ward> mListWardTemp;
    private AddressRepository repository;
    private String action;
    private int position;
    private boolean isEditAddress;


    public ChooseAddressPresenter(Context mContext, ChooseAddressView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        deliveryAddress = new DeliveryAddress();
        repository = AddressRepository.newInstance();
        mListProvince = new ArrayList<>();
        mListDistrict = new ArrayList<>();
        mListWard = new ArrayList<>();
        currentProvince = new ObservableField<>();
        currentDistrict = new ObservableField<>();
        currentWard = new ObservableField<>();
        address = new ObservableField<>();
        addressDetail = new ObservableField<>();
        numberPhone = new ObservableField<>();
        name = new ObservableField<>();
        mListDistrictTemp = new ArrayList<>();
        mListWardTemp = new ArrayList<>();
        action = mView.getAction();
        position = mView.getPosition();
        DeliveryAddress oldDeliveryAddress = mView.onGetDeliveryAddress();
        if (action.contains(Config.ACTION_EDIT_ADDRESS)) {
            currentProvince.set(oldDeliveryAddress.getProvince());
            currentDistrict.set(oldDeliveryAddress.getDistrict());
            currentWard.set(oldDeliveryAddress.getWard());
            address.set(oldDeliveryAddress.getAddress());
            name.set(oldDeliveryAddress.getName());
            numberPhone.set(oldDeliveryAddress.getPhoneNumber());
            addressDetail.set(oldDeliveryAddress.getAddressDetail());
            isEditAddress = true;
        }
        getProvince();
    }

    private void getProvince() {
        mView.showLoading();
        Observable.zip(repository.getProvinces(), repository.getDistricts(), repository.getWards()
                        , GetAddressResponse::new).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetAddressResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull GetAddressResponse getAddressResponse) {
                        mListProvince.addAll(getAddressResponse.getProvinces());
                        mListDistrict.addAll(getAddressResponse.getDistricts());
                        mListWard.addAll(getAddressResponse.getWards());
                        mListDistrictTemp.addAll(getAddressResponse.getDistricts());
                        mListWardTemp.addAll(getAddressResponse.getWards());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mView.showErr(mActivity.getResources().getString(R.string.error_get_province));
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    public void onClickProvince() {
        DialogFilterListener<Province> dialogFilterListener = (item, position) -> onChooseProvince(item);
        DialogFilter<Province> dialogFilter = new DialogFilter<>(dialogFilterListener, mContext.getResources().getString(R.string.choose_province), mListProvince);
        dialogFilter.show(mActivity.getSupportFragmentManager(), "");
    }

    public void onClickDistrict() {
        if (currentProvince.get() == null) {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.please_choose_province), Toast.LENGTH_SHORT).show();
            return;
        }
        DialogFilterListener<District> dialogFilterListener = (item, position) -> onChooseDistrict(item);
        DialogFilter<District> dialogFilter = new DialogFilter<>(dialogFilterListener, mContext.getResources().getString(R.string.choose_district), mListDistrict);
        dialogFilter.show(mActivity.getSupportFragmentManager(), "");
    }

    public void onClickWard() {
        if (currentDistrict.get() == null) {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.please_choose_district), Toast.LENGTH_SHORT).show();
            return;
        }
        DialogFilterListener<Ward> dialogFilterListener = (item, position) -> onChooseWard(item);
        DialogFilter<Ward> dialogFilter = new DialogFilter<>(dialogFilterListener, mContext.getResources().getString(R.string.choose_ward), mListWard);
        dialogFilter.show(mActivity.getSupportFragmentManager(), "");
    }

    private void onChooseProvince(Province item) {
        currentProvince.set(item);
        currentWard.set(null);
        currentDistrict.set(null);
        address.set("");
        if (currentProvince.get() != null) {
            mListDistrict.clear();
            for (District district : mListDistrictTemp) {
                if (district.getProvince_code() == Objects.requireNonNull(currentProvince.get()).getCode()) {
                    mListDistrict.add(district);
                }
            }
        }
    }

    private void onChooseDistrict(District item) {
        currentDistrict.set(item);
        currentWard.set(null);
        address.set("");
        if (currentDistrict.get() != null) {
            mListWard.clear();
            for (Ward ward : mListWardTemp) {
                if (ward.getDistrict_code() == Objects.requireNonNull(currentDistrict.get()).getCode()) {
                    mListWard.add(ward);
                }
            }
        }
    }

    private void onChooseWard(Ward item) {
        currentWard.set(item);
        address.set(Objects.requireNonNull(currentProvince.get()).getName() + ", " + Objects.requireNonNull(currentDistrict.get()).getName() + ", " + Objects.requireNonNull(currentWard.get()).getName());
    }

    public void onCancelClick() {
        mActivity.getOnBackPressedDispatcher().onBackPressed();
    }

    public void onConfirmClick() {
        if (validateConfirm()) {
            deliveryAddress.setProvince(currentProvince.get());
            deliveryAddress.setDistrict(currentDistrict.get());
            deliveryAddress.setWard(currentWard.get());
            deliveryAddress.setAddressDetail(addressDetail.get());
            deliveryAddress.setAddress(address.get());
            deliveryAddress.setPhoneNumber(numberPhone.get());
            deliveryAddress.setName(name.get());
            deliveryAddress.setDefault(false);
            if (isEditAddress) {
                mView.onEdit(deliveryAddress, position);
            }
            mView.onConfirmClick(deliveryAddress);
        }
    }

    private boolean validateConfirm() {
        if (CommonActivity.isNullOrEmpty(name.get())) {
            mView.showMessage(String.format(mActivity.getResources().getString(R.string.please_enter), mActivity.getResources().getString(R.string.name_get_person_order)));
            return false;
        }
        if (CommonActivity.isNullOrEmpty(numberPhone.get())) {
            mView.showMessage(String.format(mActivity.getResources().getString(R.string.please_enter), mActivity.getResources().getString(R.string.number_phone)));
            return false;
        }
        if (currentProvince.get() == null) {
            mView.showMessage(String.format(mActivity.getResources().getString(R.string.please), mActivity.getResources().getString(R.string.choose_province)));
            return false;
        }
        if (currentDistrict.get() == null) {
            mView.showMessage(String.format(mActivity.getResources().getString(R.string.please), mActivity.getResources().getString(R.string.choose_district)));
            return false;
        }
        if (currentWard.get() == null) {
            mView.showMessage(String.format(mActivity.getResources().getString(R.string.please), mActivity.getResources().getString(R.string.choose_ward)));
            return false;

        }
        if (!StringUtils.isValidVietnamPhoneNumber(numberPhone.get())) {
            mView.showMessage(mActivity.getString(R.string.number_phone_not_true));
            return false;
        }
        if (!StringUtils.isValidUsername(name.get())) {
            mView.showMessage(mActivity.getString(R.string.name_not_true));
            return false;
        }
        return true;
    }
}
