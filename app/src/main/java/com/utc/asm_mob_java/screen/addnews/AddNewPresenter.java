package com.utc.asm_mob_java.screen.addnews;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.KeyValue;
import com.utc.asm_mob_java.dialog.dialogfilter.DialogFilter;
import com.utc.asm_mob_java.dialog.dialogfilter.DialogFilterListener;

import java.util.ArrayList;
import java.util.List;

public class AddNewPresenter extends BasePresenterForm<AddNewView> {
    public ObservableField<KeyValue> currentStatusInterior;
    public ObservableField<String> address;
    public ObservableField<String> area;
    public ObservableField<String> deposit;
    public ObservableField<String> title;
    public ObservableField<String> description;
    public ObservableField<String> price;
    public ObservableField<Boolean> individual = new ObservableField<>(true);

    private List<KeyValue> lstStatusInterior;

    public AddNewPresenter(Context mContext, AddNewView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        lstStatusInterior = new ArrayList<>();
        lstStatusInterior.add(new KeyValue("0","Nội thất cao cấp"));
        lstStatusInterior.add(new KeyValue("1","Nội thất đầy đủ"));
        lstStatusInterior.add(new KeyValue("2","Nhà trống"));
        currentStatusInterior = new ObservableField<>(lstStatusInterior.get(0));
        address = new ObservableField<>();
        area = new ObservableField<>();
        deposit = new ObservableField<>();
        title = new ObservableField<>();
        description = new ObservableField<>();
        price = new ObservableField<>();
    }

    public void onChooseStatusInterior() {
        DialogFilterListener<KeyValue> listener = (item, position) -> currentStatusInterior.set(item);
        DialogFilter<KeyValue> dialogFilter = new DialogFilter<>(listener, "Chọn tình trạng nội thất", lstStatusInterior);
        dialogFilter.show(mActivity.getSupportFragmentManager(), "");
    }
    public void onClickRdo(){
        individual.set(Boolean.FALSE.equals(individual.get()));
    }
    public void onClickChooseImage(){
        mView.onChooseImage();
    }

}
