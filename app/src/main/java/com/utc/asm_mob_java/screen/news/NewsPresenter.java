package com.utc.asm_mob_java.screen.news;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.ObservableField;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.BaseRecyclerView;
import com.utc.asm_mob_java.base.baseactivity.BasePresenterForm;
import com.utc.asm_mob_java.data.model.NewsModel;
import com.utc.asm_mob_java.data.source.repository.RentRoomRepository;
import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;

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
    public CompositeDisposable compositeDisposable;
    public ObservableField<BaseRecyclerView<NewsModel>> mAdapter;
    private List<NewsModel> mListNews;
    public NewsPresenter(Context mContext, NewsView mView) {
        super(mContext, mView);
    }

    @Override
    protected void initData() {
        compositeDisposable = new CompositeDisposable();
        mListNews = new ArrayList<>();
        mAdapter = new ObservableField<>(new BaseRecyclerView<>(mActivity,mListNews, R.layout.item_news));
        getListNew();
    }
    private void getListNew(){
        mView.showLoading();
        RentRoomRepository repository = RentRoomRepository.newInstance();
        NewsRequest request = new NewsRequest();
        request.setUserId("10");
        Disposable disposable = repository.getNewsByUserid(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NewsResponse>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onNext(@NonNull NewsResponse newsResponse) {
                        handleNewsResponse(newsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        handleError(e);
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

            mListNews.addAll(newItems);

            if (mAdapter.get() != null) {
                Objects.requireNonNull(mAdapter.get()).notifyDataSetChanged();
            }
        } else {
            mView.showErr(response.getMessage());
        }
    }

    private void handleError(Throwable e) {
        mView.hideLoading();

        if (e instanceof SocketTimeoutException) {
            mView.showErr("Request timeout. Please try again");
        } else if (e instanceof ConnectException) {
            mView.showErr("Cannot connect to server");
        } else {
            mView.showErr("Error: " + e.getMessage());
        }
    }
}
