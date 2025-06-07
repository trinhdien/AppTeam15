package com.utc.asm_mob_java.base.slectimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.callback.OnListenerItemRecyclerView;
import com.utc.asm_mob_java.callback.OnListenerRecyclerView;
import com.utc.asm_mob_java.data.model.SelectImageModel;
import com.utc.asm_mob_java.databinding.ItemChooseImageBinding;
import com.utc.asm_mob_java.utils.CommonActivity;
import com.utc.asm_mob_java.utils.ImageUtils;

import java.util.List;

public class AdapterSelectImage extends RecyclerView.Adapter<AdapterSelectImage.ViewHolder> {
    private final List<SelectImageModel> mList;
    private final Context mContext;
    private OnListenerItemRecyclerView<SelectImageModel> listener;

    private OnListenerRecyclerView<SelectImageModel> listenerRecyclerView;

    public AdapterSelectImage(List<SelectImageModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChooseImageBinding mBinding = ItemChooseImageBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!mList.isEmpty()) holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChooseImageBinding mBinding;
        private SelectImageModel mItem;

        public ViewHolder(ItemChooseImageBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bind(SelectImageModel mItem) {
            this.mItem = mItem;
            mBinding.setViewHolder(this);
            if (mItem != null && !CommonActivity.isNullOrEmpty(mItem.getUrl())) {
                Bitmap bitmap = ImageUtils.convertBase64ToBitmap(mItem.getUrl());
                mBinding.icImage.setImageBitmap(bitmap);
            } else {
                mBinding.icImage.setImageResource(R.drawable.ic_upload_image);
            }
        }

        public void onClickItem() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listener != null)
                listener.onClickItem(mList.get(getBindingAdapterPosition()), getBindingAdapterPosition());
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem(mList.get(getBindingAdapterPosition()), getBindingAdapterPosition());
        }

        public void onClickItem1() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem1(mList.get(getBindingAdapterPosition()), getBindingAdapterPosition());
        }

        public void onClickItem2() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem2(mItem, getBindingAdapterPosition());
        }

        public void onClickButton() {
            if (listenerRecyclerView != null)
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION && getBindingAdapterPosition() < mList.size()) {
                    listenerRecyclerView.onClickButton(mList.get(getBindingAdapterPosition()), getBindingAdapterPosition());
                }
        }

        public void onClickItem4() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickItem4(mItem, getBindingAdapterPosition());
        }

        public void onClickEdit() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickEdit(mItem, getBindingAdapterPosition());
        }

        public void onClickDelete() {
            if (CommonActivity.isNullOrEmpty(mList)) return;
            if (listenerRecyclerView != null)
                listenerRecyclerView.onClickDelete(mItem, getBindingAdapterPosition());
        }
    }

    public OnListenerItemRecyclerView<SelectImageModel> getListener() {
        return listener;
    }

    public void setListener(OnListenerItemRecyclerView<SelectImageModel> listener) {
        this.listener = listener;
    }

    public OnListenerRecyclerView<SelectImageModel> getListenerRecyclerView() {
        return listenerRecyclerView;
    }

    public void setListenerRecyclerView(OnListenerRecyclerView<SelectImageModel> listenerRecyclerView) {
        this.listenerRecyclerView = listenerRecyclerView;
    }
}
