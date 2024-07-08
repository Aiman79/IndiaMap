package com.digitalhorizons.indiamapapp.home.interfaces;

import androidx.fragment.app.Fragment;

public interface ChangeFragmentListener {
//    void onFragmentChange(Fragment fragment, CategoryModel categoryModel);
    void onFragmentChange(int id, Fragment fragment, String tag, String backtack);
}
