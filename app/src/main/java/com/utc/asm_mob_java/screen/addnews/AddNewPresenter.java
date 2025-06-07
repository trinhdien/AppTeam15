package com.utc.asm_mob_java.screen.addnews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.base.slectimage.AdapterSelectImage;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.KeyValue;
import com.utc.asm_mob_java.data.model.NewsModel;
import com.utc.asm_mob_java.data.model.SelectImageModel;
import com.utc.asm_mob_java.data.source.repository.RentRoomRepository;
import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;
import com.utc.asm_mob_java.dialog.dialogfilter.DialogFilter;
import com.utc.asm_mob_java.dialog.dialogfilter.DialogFilterListener;
import com.utc.asm_mob_java.utils.CommonActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNewPresenter extends BasePresenterForm<AddNewView> {
    public CompositeDisposable compositeDisposable;
    public SelectImageModel currentSelectImage;
    public int positionSelectImage;
    public ObservableField<KeyValue> currentStatusInterior;
    public ObservableField<String> address;
    public ObservableField<String> area;
    public ObservableField<String> deposit;
    public ObservableField<String> title;
    public ObservableField<String> description;
    public ObservableField<String> price;
    public ObservableField<AdapterSelectImage> mAdapter;
    public ObservableField<Boolean> individual = new ObservableField<>(true);
    private List<SelectImageModel> mListImageSelect;

    private List<KeyValue> lstStatusInterior;
    private RentRoomRepository mRepository;

    public AddNewPresenter(Context mContext, AddNewView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        compositeDisposable = new CompositeDisposable();
        lstStatusInterior = new ArrayList<>();
        lstStatusInterior.add(new KeyValue("Noi that cao cap", "Nội thất cao cấp"));
        lstStatusInterior.add(new KeyValue("Noi that day du", "Nội thất đầy đủ"));
        lstStatusInterior.add(new KeyValue("Nha trong", "Nhà trống"));
        currentStatusInterior = new ObservableField<>();
        address = new ObservableField<>();
        area = new ObservableField<>();
        deposit = new ObservableField<>();
        title = new ObservableField<>();
        description = new ObservableField<>();
        price = new ObservableField<>();
        mListImageSelect = new ArrayList<>();
        mListImageSelect.add(new SelectImageModel());
        mListImageSelect.add(new SelectImageModel());
        mListImageSelect.add(new SelectImageModel());
        mAdapter = new ObservableField<>(new AdapterSelectImage(mListImageSelect, mActivity));
        Objects.requireNonNull(mAdapter.get()).setListenerRecyclerView(new OnListenerRecyclerView<SelectImageModel>() {
            @Override
            public void onClickItem(SelectImageModel item, int position) {
                super.onClickItem(item, position);
                currentSelectImage = item;
                positionSelectImage = position;
                mView.onChooseImage();
            }
        });
        mRepository = RentRoomRepository.newInstance();
    }

    public void onChooseStatusInterior() {
        DialogFilterListener<KeyValue> listener = (item, position) -> currentStatusInterior.set(item);
        DialogFilter<KeyValue> dialogFilter = new DialogFilter<>(listener, "Chọn tình trạng nội thất", lstStatusInterior);
        dialogFilter.show(mActivity.getSupportFragmentManager(), "");
    }

    public void onClickRdo() {
        individual.set(Boolean.FALSE.equals(individual.get()));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onClickChooseImage() {
        if (mListImageSelect.size() < 12) {
            mListImageSelect.add(new SelectImageModel());
            mListImageSelect.add(new SelectImageModel());
            mListImageSelect.add(new SelectImageModel());
            Objects.requireNonNull(mAdapter.get()).notifyDataSetChanged();
        } else {
            mView.showErr("Bạn chỉ được chọn tối đa 12 ảnh");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifySelectImage(String imageBase64) {
        if (currentSelectImage != null) {
            currentSelectImage.setUrl(imageBase64);
            mListImageSelect.set(positionSelectImage, currentSelectImage);
            Objects.requireNonNull(mAdapter.get()).notifyItemChanged(positionSelectImage, currentSelectImage);
        }
    }

    public void onClickPost() {
        if (validate()) {
            mView.showLoading();
            NewsRequest newsRequest = new NewsRequest();
            newsRequest.setAddress(address.get());
            newsRequest.setArea(Integer.parseInt(Objects.requireNonNull(area.get())));
            newsRequest.setDeposit(Integer.parseInt(Objects.requireNonNull(deposit.get())));
            newsRequest.setDescription(description.get());
            newsRequest.setFurnitureStatus(currentStatusInterior.get() == null ? null : Objects.requireNonNull(currentStatusInterior.get()).getKey());
            newsRequest.setStatus("Dang hien thi");
            newsRequest.setTitle(title.get());
            newsRequest.setPrice(Integer.parseInt(Objects.requireNonNull(price.get())));
            newsRequest.setCustomerId(10);
            newsRequest.setCustomerType(Boolean.TRUE.equals(individual.get()) ? "Ca nhan" : "Moi gioi");
            List<String> images = new ArrayList<>();
            for (SelectImageModel item : mListImageSelect) {
                if (!CommonActivity.isNullOrEmpty(item.getUrl())) {
                    images.add(item.getUrl());
                }
            }
            newsRequest.setImages(images);
            Disposable disposable = mRepository.addNews(newsRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<NewsResponse>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onNext(@NonNull NewsResponse newsResponse) {
                            mView.hideLoading();
                            handleNewsResponse(newsResponse);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mView.hideLoading();
                            handleError(e);
                        }

                        @Override
                        public void onComplete() {
                            mView.hideLoading();
                        }
                    });

            compositeDisposable.add(disposable);
        }
    }

    private boolean validate() {
        int indexCheck = 0;
        if (address.get() == null || Objects.requireNonNull(address.get()).isEmpty()) {
            mView.showErr("Vui lòng nhập địa chỉ");
            return false;
        }
        if (area.get() == null || Objects.requireNonNull(area.get()).isEmpty()) {
            mView.showErr("Vui lòng nhập diện tích");
            return false;
        }
        if (title.get() == null || Objects.requireNonNull(title.get()).isEmpty()) {
            mView.showErr("Vui lòng nhập tiêu đề");
            return false;
        }
        if (description.get() == null || Objects.requireNonNull(description.get()).isEmpty()) {
            mView.showErr("Vui lòng nhập mô tả");
            return false;
        }
        if (price.get() == null || Objects.requireNonNull(price.get()).isEmpty()) {
            mView.showErr("Vui lòng nhập giá thuê");
            return false;
        }
        for (SelectImageModel item : mListImageSelect) {
            if (!CommonActivity.isNullOrEmpty(item.getUrl())) {
                indexCheck++;
            }
            if (indexCheck == 3) {
                break;
            }

        }
        if (indexCheck < 3) {
            mView.showErr("Vui lòng chọn ít nhất 3 hình ảnh");
            return false;
        }
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void handleNewsResponse(NewsResponse response) {
        if ("200".equals(response.getStatus())) {
            Toast.makeText(mActivity, "Thêm bản tin thành công", Toast.LENGTH_SHORT).show();
            mActivity.setResult(Activity.RESULT_OK);
            mActivity.finish();
        } else {
            mView.showErr(response.getMessage());
        }
    }

    private void handleError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            mView.showErr("Request timeout. Please try again");
        } else if (e instanceof ConnectException) {
            mView.showErr("Cannot connect to server");
        } else {
            mView.showErr("Error: " + e.getMessage());
        }
    }
}
