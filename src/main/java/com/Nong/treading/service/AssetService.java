package com.Nong.treading.service;

import com.Nong.treading.modal.Asset;
import com.Nong.treading.modal.Coin;
import com.Nong.treading.modal.User;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity) throws Exception;

}
