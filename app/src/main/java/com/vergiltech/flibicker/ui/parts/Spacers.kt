package com.vergiltech.flibicker.ui.parts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Spacers {
    @Composable
    fun SmallVerticalSpacer() {
        Spacer(modifier = Modifier.height(5.dp))
    }

    @Composable
    fun MediumVerticalSpacer() {
        Spacer(modifier = Modifier.height(15.dp))
    }

    @Composable
    fun LargeVerticalSpacer() {
        Spacer(modifier = Modifier.height(25.dp))
    }

    @Composable
    fun SmallHorizontalSpacer() {
        Spacer(modifier = Modifier.width(5.dp))
    }

    @Composable
    fun MediumHorizontalSpacer() {
        Spacer(modifier = Modifier.width(15.dp))
    }

    @Composable
    fun LargeHorizontalSpacer() {
        Spacer(modifier = Modifier.width(25.dp))
    }
}