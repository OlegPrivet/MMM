package com.app.mmm.view.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.app.mmm.core.viewmodel.BaseViewModel
import com.app.mmm.core.workmanager.PushWorker
import com.app.mmm.datastore.PrefsDataStore
import com.app.mmm.view.settings.state.PeriodDuration
import com.app.mmm.view.settings.state.SettingsViewAction
import com.app.mmm.view.settings.state.SettingsViewEvent
import com.app.mmm.view.settings.state.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@HiltViewModel
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class SettingsViewModel @Inject constructor(
    private val prefs: PrefsDataStore,
    private val workManager: WorkManager,
) : BaseViewModel<SettingsViewState, SettingsViewAction, SettingsViewEvent>(initialState = INIT_STATE) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.pushEnableFlow.collect {
                viewState = viewState.copy(pushEnabled = it)
            }
        }
    }

    override fun obtainEvent(viewEvent: SettingsViewEvent) {
        when (viewEvent) {
            is SettingsViewEvent.PushChanging -> obtainPush(viewEvent.isEnable)
            is SettingsViewEvent.PeriodChanging -> obtainPeriod(viewEvent.period)
        }
    }

    private fun obtainPush(enable: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.pushEnabled(enable)
            if (enable) createWorkManager() else cancelWorker()
        }
    }

    private fun cancelWorker() {
        workManager.cancelUniqueWork("PUSH")
        Timber.w(workManager.getWorkInfosByTag("PUSH").toString())
    }

    private fun createWorkManager() {
        val interval = viewState.pushPeriod.duration.toLong(DurationUnit.HOURS)
        val flexInterval =
            viewState.pushPeriod.duration.toLong(DurationUnit.HOURS) - 1L.minutes.toLong(DurationUnit.MINUTES)
        val pushWorker = PeriodicWorkRequestBuilder<PushWorker>(
            repeatInterval = interval,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
            flexTimeInterval = flexInterval,
            flexTimeIntervalUnit = TimeUnit.HOURS,
        )
            .addTag("PUSH")
            .build()
        workManager.enqueueUniquePeriodicWork("PUSH", ExistingPeriodicWorkPolicy.REPLACE, pushWorker)
    }

    private fun obtainPeriod(period: PeriodDuration) {
        viewState = viewState.copy(pushPeriod = period)
        createWorkManager()
    }

    private companion object {
        val INIT_STATE = SettingsViewState()
    }
}
