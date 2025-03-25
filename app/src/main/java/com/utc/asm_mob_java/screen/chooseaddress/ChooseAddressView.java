package com.utc.asm_mob_java.screen.chooseaddress;

import com.utc.asm_mob_java.base.BaseView;
import com.utc.asm_mob_java.data.model.DeliveryAddress;

public interface ChooseAddressView extends BaseView<ChooseAddressPresenter> {
    void onConfirmClick(DeliveryAddress address);
}
