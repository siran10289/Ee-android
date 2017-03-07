package com.eeyuva.utils.listeners;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.ResponseList;

import java.util.List;

public interface DialogListener {
    void onDialogClosedByModuleClick(ResponseList moduleObject);
    void onDialogClosedByCatagoryClick(CatagoryList.Catagory catagoryObject);

}