package com.hrappv.ui.feature.home_screen

import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {

}