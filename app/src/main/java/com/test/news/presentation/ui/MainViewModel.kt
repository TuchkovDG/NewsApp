package com.test.news.presentation.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val title = ObservableField<String>()
}