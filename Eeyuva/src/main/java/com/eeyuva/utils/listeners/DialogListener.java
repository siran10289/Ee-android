package com.eeyuva.utils.listeners;
import com.eeyuva.screens.home.ResponseList;

import java.util.List;

public interface DialogListener {
    void onDialogClosedByOkClick(ResponseList moduleObject);
    void onDialogClosedByCancelClick();
}