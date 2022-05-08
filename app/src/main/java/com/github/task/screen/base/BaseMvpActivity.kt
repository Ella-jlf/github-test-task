package com.github.task.screen.base

import androidx.annotation.LayoutRes


abstract class BaseMvpActivity(@LayoutRes layout: Int) : MvpActivity(layout), BaseMvpView