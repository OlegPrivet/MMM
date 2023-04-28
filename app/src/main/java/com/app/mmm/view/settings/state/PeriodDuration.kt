package com.app.mmm.view.settings.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.app.mmm.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@Stable
enum class PeriodDuration(@StringRes val title: Int, val duration: Duration) {
    ONE_HOURS(R.string.one_hour, 1L.hours),
    TWO_HOURS(R.string.two_hour, 2L.hours),
    THREE_HOURS(R.string.three_hour, 3L.hours),
    FOUR_HOURS(R.string.four_hour, 4L.hours),
    FIVE_HOURS(R.string.five_hour, 5L.hours),
    SIX_HOURS(R.string.six_hour, 6L.hours),
}
