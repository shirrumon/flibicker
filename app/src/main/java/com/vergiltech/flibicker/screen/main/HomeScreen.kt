package com.vergiltech.flibicker.screen.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.vergiltech.flibicker.screen.main.partials.Search
import com.vergiltech.flibicker.screen.main.vm.HomeViewModel

@Composable
fun HomeScreen (
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Search(homeViewModel = homeViewModel)
}