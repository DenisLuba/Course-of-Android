package com.example.p007_fragmentnavigation.fragments

import androidx.fragment.app.Fragment
import com.example.p007_fragmentnavigation.contract.CustomAction
import com.example.p007_fragmentnavigation.contract.HasCustomAction
import com.example.p007_fragmentnavigation.contract.HasCustomTitle

class OptionsFragment : Fragment(), HasCustomTitle, HasCustomAction {
    override fun getCustomAction(): CustomAction {
        TODO("Not yet implemented")
    }

    override fun getTitleRes(): Int {
        TODO("Not yet implemented")
    }


}