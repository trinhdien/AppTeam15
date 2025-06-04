package com.utc.asm_mob_java.screen.news;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.NewsActivityBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;

public class NewsActivity extends BaseBindingActivity<NewsActivityBinding,NewsPresenter>  implements NewsView{
    @Override
    public void showMessage(String mess) {

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
        DialogUtils.showErrDialog(NewsActivity.this,err);
    }

    @Override
    protected int getIdLayout() {
        return R.layout.news_activity;
    }

    @Override
    protected void initData() {
        mPresenter = new NewsPresenter(this,this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter  != null){
            mPresenter.compositeDisposable.dispose();
        }
    }
}
