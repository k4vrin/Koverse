package dev.kavrin.koverse.android.di

import dev.kavrin.koverse.android.presentation.screens.chat.ChatViewModel
import dev.kavrin.koverse.android.presentation.screens.username.UsernameViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::UsernameViewModel)
    viewModelOf(::ChatViewModel)
}