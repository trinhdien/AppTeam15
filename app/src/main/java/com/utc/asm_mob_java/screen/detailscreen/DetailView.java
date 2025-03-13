package com.utc.asm_mob_java.screen.detailscreen;

import com.utc.asm_mob_java.base.BaseView;
import com.utc.asm_mob_java.data.model.Product;

public interface DetailView extends BaseView<DetailPresenter> {
    Product getProduct();
}
