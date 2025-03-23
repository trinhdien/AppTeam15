package com.utc.asm_mob_java.dialog.dialogfilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.databinding.ItemFilterBinding;
import com.utc.asm_mob_java.utils.CommonActivity;

import java.util.List;

public class DialogFilterAdapter<T> extends RecyclerView.Adapter<DialogFilterAdapter<T>.DialogFilterViewHolder> {
    private final List<T> mList;
    private final Context mContext;
    private DialogFilterListener<T> listener;

    public DialogFilterAdapter(List<T> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DialogFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_filter, parent, false);
        return new DialogFilterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogFilterAdapter.DialogFilterViewHolder holder, int position) {
        if (!CommonActivity.isNullOrEmpty(mList)) {
            holder.bind(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class DialogFilterViewHolder extends RecyclerView.ViewHolder {
        private T mItem;
        private final ItemFilterBinding mBinding;

        public DialogFilterViewHolder(@NonNull ItemFilterBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(T mItem) {
            this.mItem = mItem;
//            mBinding.setVariable(BR.item, mItem);
            mBinding.tvContent.setText(mItem.toString());
            mBinding.lnItem.setOnClickListener(view -> onChoose());
            mBinding.executePendingBindings();
        }

        public void onChoose() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listener != null)
                listener.onChoose(mItem, getAdapterPosition());
        }
    }

    public void setListener(DialogFilterListener<T> listener) {
        this.listener = listener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<T> newList) {
        mList.clear();
        mList.addAll(newList);
        notifyDataSetChanged();
    }

}
