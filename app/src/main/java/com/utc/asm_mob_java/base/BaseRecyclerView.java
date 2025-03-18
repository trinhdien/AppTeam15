package com.utc.asm_mob_java.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

    private boolean showNoData;

    private boolean isFormView;

    private boolean changeBackground;

    public void setChangeBackground(boolean changeBackground) {
        this.changeBackground = changeBackground;
    }

    public void setShowNoData(boolean showNoData) {
        this.showNoData = showNoData;
    }

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

    public List<T> getmList() {
        return mList == null ? new ArrayList<>() : mList;
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

    public BaseRecyclerView(Context mContext, List<T> lstData, @LayoutRes int layoutId, boolean isFormView) {
        this.mList = lstData;
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.isFormView = isFormView;
        if (mList == null) mList = new ArrayList<>();
    }

    public BaseRecyclerView(Context mContext, @LayoutRes int layoutId) {

        this.mContext = mContext;
        this.layoutId = layoutId;
        mList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListData(List<T> lstData) {
        this.mList = lstData;
        notifyDataSetChanged();
    }

    public void setType(String type) {
        this.mType = type;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                viewType == TYPE_NO_DATA ? R.layout.item_no_data_list :
                        layoutId, parent, false);
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
        if (changeBackground) {
            if (position % 2 == 0) {
                holder.mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.white, mContext.getTheme()));
            } else {
                holder.mBinding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.grey31, mContext.getTheme()));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList.isEmpty() && showNoData) {
            return 1;
        }
        return mList.size();
    }

    public class BaseViewHolder<M extends ViewDataBinding> extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
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
//            mBinding.setVariable(BR.onChangeSelect, this);
//            mBinding.setVariable(BR.endItem, mList.size() - 1 == getAdapterPosition());
//
//            mBinding.setVariable(BR.index, getAdapterPosition());
//            mBinding.setVariable(BR.formView, isFormView);
        }

        public void onClickItem() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listener != null)
                listener.onClickItem(mList.get(getAdapterPosition()), getAdapterPosition());
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem(mList.get(getAdapterPosition()), getAdapterPosition());
        }

        public void onClickItemCheckSerial(View v, boolean b) {
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItemCheckSerial(mItem, getAdapterPosition(), v, b);
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

        public void onClickItem3(View v, boolean b) {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem3(mItem, getAdapterPosition(), v, b);
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

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
            if (isCheck) {
                if (listenerRecyclerView != null)
                    listenerRecyclerView.onCheckItem(mList.get(getAdapterPosition()), getAdapterPosition());
            } else {
                if (listenerRecyclerView != null) {
                    listenerRecyclerView.onUncheckItem(mList.get(getAdapterPosition()), getAdapterPosition());
                }
            }
        }

        public void onFocusChangeText(EditText editText, boolean hasFocus) {
            if (listenerRecyclerView != null)
                listenerRecyclerView.onFocusChange(mItem, editText, hasFocus);
        }

        public View.OnFocusChangeListener onFocusChangeListener() {
            return (view, hasFocus) -> {
                if (listenerRecyclerView != null) {
                    if (view != null) {
                        onFocusChangeText((EditText) view, hasFocus);
                    }
                }

            };
        }
        public void onClickItem4() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem4(mItem, getAdapterPosition());
        }
    }

    public int getCountList() {
        return mList != null ? mList.size() : 0;
    }

    public int getCount() {
        return mList != null ? mList.size() : 0;
    }


}