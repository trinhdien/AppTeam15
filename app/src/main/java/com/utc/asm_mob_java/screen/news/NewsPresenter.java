package com.utc.asm_mob_java.screen.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.NewsModel;
import com.utc.asm_mob_java.data.source.repository.RentRoomRepository;
import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;
import com.utc.asm_mob_java.screen.addnews.AddNewActivity;
import com.utc.asm_mob_java.screen.detail.DetailActivity;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.Constants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsPresenter extends BasePresenterForm<NewsView> {
    private ActivityResultLauncher<Intent> resultLauncherAddNew;
    public ObservableBoolean isNoData;
    public CompositeDisposable compositeDisposable;
    public ObservableField<BaseRecyclerView<NewsModel>> mAdapter;
    private List<NewsModel> mListNews;

    public NewsPresenter(Context mContext, NewsView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        resultLauncherAddNew = mActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode() == Activity.RESULT_OK ) {
                getListNew();
            }
        });
        isNoData = new ObservableBoolean(true);
        compositeDisposable = new CompositeDisposable();
        mListNews = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerView<>(mActivity, mListNews, R.layout.item_news));
        Objects.requireNonNull(mAdapter.get()).setRcvHaveImage(true);
        Objects.requireNonNull(mAdapter.get()).setListenerRecyclerView(new OnListenerRecyclerView<NewsModel>() {
            @Override
            public void onClickItem(NewsModel item, int position) {
                super.onClickItem(item, position);
                Intent intent = new Intent(mActivity, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.INT, item.getRoomId());
                intent.putExtra(Constants.BundleKey.ITEM, bundle);
                mActivity.startActivity(intent);
            }
        });
        getListNew();
    }

    private void getListNew() {
        mView.showLoading();
        RentRoomRepository repository = RentRoomRepository.newInstance();
        NewsRequest request = new NewsRequest();
        Disposable disposable = repository.getAllNews(request)
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
                        isNoData.set(CommonActivity.isNullOrEmpty(mListNews));
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });

        compositeDisposable.add(disposable);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void handleNewsResponse(NewsResponse response) {
        if ("200".equals(response.getStatus())) {
            List<NewsModel> newItems = response.getObject();

            if (newItems == null || newItems.isEmpty()) {
                return;
            }
            if (!CommonActivity.isNullOrEmpty(mListNews)) {
                mListNews.clear();
            }
            mListNews.addAll(newItems);

            if (mAdapter.get() != null) {
                Objects.requireNonNull(mAdapter.get()).notifyDataSetChanged();
            }
            isNoData.set(CommonActivity.isNullOrEmpty(mListNews));
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

    public void gotoAddNews() {
        Intent intent = new Intent(mActivity, AddNewActivity.class);
        resultLauncherAddNew.launch(intent);
    }
}
