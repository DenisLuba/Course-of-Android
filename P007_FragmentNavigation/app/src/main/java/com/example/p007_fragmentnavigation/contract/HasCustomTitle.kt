package com.example.p007_fragmentnavigation.contract

import androidx.annotation.StringRes


/**
 *  Этот интерфейс переопределяет дефолтный toolbar title
 */

interface HasCustomTitle {

    /**
     * @return the string resource which should be displayed instead of default title
     */
    @StringRes
    fun getTitleRes() : Int
}