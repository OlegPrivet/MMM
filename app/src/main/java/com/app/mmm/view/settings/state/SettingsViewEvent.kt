package com.app.mmm.view.settings.state

sealed class SettingsViewEvent {
    data class PushChanging(val isEnable: Boolean) : SettingsViewEvent()
    data class PeriodChanging(val period: PeriodDuration) : SettingsViewEvent()
}
