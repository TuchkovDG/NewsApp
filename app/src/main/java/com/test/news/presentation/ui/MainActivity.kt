package com.test.news.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.test.news.R
import com.test.news.databinding.ActivityMainBinding
import com.test.news.presentation.ui.base.BaseBindingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseBindingActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            (supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment).navController
        )
    }

    override fun onStart() {
        super.onStart()
        binding.navMainFragment.findNavController().addOnDestinationChangedListener(this)
    }

    override fun onStop() {
        binding.navMainFragment.findNavController().removeOnDestinationChangedListener(this)
        super.onStop()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.title.set(destination.label.toString())
    }
}