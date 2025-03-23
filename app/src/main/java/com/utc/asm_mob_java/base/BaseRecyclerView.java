package com.utc.asm_mob_java.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.callback.OnListenerItemRecyclerView;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.utils.CommonActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerView<T> extends RecyclerView.Adapter<BaseRecyclerView<?>.BaseViewHolder<?>> {
    public static final int TYPE_NO_DATA = 0;
    public static final int TYPE_NORMAL = 1;

    private List<T> mList;
    private final Context mContext;
    @LayoutRes
    private final int layoutId;

    private String mType;

    private OnListenerItemRecyclerView<T> listener;

    private OnListenerRecyclerView<T> listenerRecyclerView;

    public void setListenerRecyclerView(OnListenerRecyclerView<T> listenerRecyclerView) {
        this.listenerRecyclerView = listenerRecyclerView;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setListener(OnListenerItemRecyclerView<T> listener) {
        this.listener = listener;
    }

    public BaseRecyclerView(Context mContext, List<T> lstData, @LayoutRes int layoutId) {
        this.mList = lstData;
        this.mContext = mContext;
        this.layoutId = layoutId;
        if (mList == null) mList = new ArrayList<>();
    }

    public BaseRecyclerView(Context mContext, List<T> lstData, @LayoutRes int layoutId, String type) {
        this.mList = lstData;
        this.mContext = mContext;
        this.layoutId = layoutId;
        mType = type;
        if (mList == null) mList = new ArrayList<>();
    }

    public BaseRecyclerView(Context mContext, @LayoutRes int layoutId) {

        this.mContext = mContext;
        this.layoutId = layoutId;
        mList = new ArrayList<>();
    }

    public void setType(String type) {
        this.mType = type;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.isEmpty()) {
            return TYPE_NO_DATA;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerView.BaseViewHolder holder, int position) {
        if (!mList.isEmpty()) holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (CommonActivity.isNullOrEmpty(mList)) {
            return 1;
        }
        return mList.size();
    }

    public class BaseViewHolder<M extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private final M mBinding;
        private T mItem;

        public BaseViewHolder(M itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bind(T mItem) {
            this.mItem = mItem;
            mBinding.setVariable(BR.item, mItem);
            mBinding.setVariable(BR.viewHolder, this);
            if (mBinding.getRoot().findViewById(R.id.edt_number) != null) {
                EditText editText = mBinding.getRoot().findViewById(R.id.edt_number);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (listenerRecyclerView != null)
                            listenerRecyclerView.afterTextChanged(mItem, getAdapterPosition(), editable);
                    }
                });
            }
        }

        public void onClickItem() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listener != null)
                listener.onClickItem(mList.get(getAdapterPosition()), getAdapterPosition());
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem(mList.get(getAdapterPosition()), getAdapterPosition());
        }

        public void onClickItem1() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem1(mList.get(getAdapterPosition()), getAdapterPosition());
        }

        public void onClickItem2() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem2(mItem, getAdapterPosition());
        }

        public void onClickButton() {
            if (listenerRecyclerView != null)
                if (getAdapterPosition() != RecyclerView.NO_POSITION && getAdapterPosition() < mList.size()) {
                    listenerRecyclerView.onClickButton(mList.get(getAdapterPosition()), getAdapterPosition());
                }
        }

        public String type() {
            return mType;
        }

        public void onClickItem4() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem4(mItem, getAdapterPosition());
        }
    }
}