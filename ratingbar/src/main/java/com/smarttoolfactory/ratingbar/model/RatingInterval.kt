package com.smarttoolfactory.ratingbar.model

import kotlin.math.ceil

enum class RatingInterval {
    Full, Half, Unconstrained
}

internal fun Float.getRatingForInterval(
    ratingInterval: RatingInterval,
    allowZero: Boolean
): Float {

    val delta = if (allowZero && this < 0.1f) 0f else 0.001f


    val result = when (ratingInterval) {
        RatingInterval.Full -> {
            ceil(this.coerceAtLeast(delta).toDouble()).toFloat()
        }

        RatingInterval.Half -> {

            val carry = this - this.toInt()

            if (carry == 0f || carry == 0.5f) {
                this
            } else
                if (carry < 0.5) {
                this.toInt().toFloat() + 0.5f
            } else {
                ceil(this.coerceAtLeast(delta).toDouble()).toFloat()
            }
        }

        else -> this
    }

    return result
}
