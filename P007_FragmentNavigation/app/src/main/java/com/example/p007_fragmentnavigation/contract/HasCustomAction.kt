package com.example.p007_fragmentnavigation.contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 *  Реализуем этот интерфейс, если во фрагменте нужно показать дополнительный action в toolbar,
 *  когда фрагмент отображается для пользователя
 */

interface HasCustomAction {

    /**
     * @return a custom action specification, see [CustomAction] class for details
     */
    fun getCustomAction() : CustomAction
}

class CustomAction(
    @DrawableRes val iconRes: Int,
    @StringRes val textRes: Int,
    val onCustomAction: Runnable
)
