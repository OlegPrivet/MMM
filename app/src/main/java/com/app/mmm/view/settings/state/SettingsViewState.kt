package com.app.mmm.view.settings.state


data class SettingsViewState(
    val pushEnabled: Boolean = false,
    val pushPeriod: PeriodDuration = PeriodDuration.ONE_HOURS,
)
