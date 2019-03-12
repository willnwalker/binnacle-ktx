package xyz.willnwalker.binnacle.model

abstract class Wind {
    /// Current speed of the wind in current location in miles per hour.
    abstract var speed: kotlin.Any

    /// Current direction of the wind in degrees (where 0 = North, 90 = East).
    abstract var direction: kotlin.Any

    /// DateTime when these values were last updated.
    abstract var lastUpdate: kotlin.Any

    abstract fun setPositionStream(locationStream: kotlin.Any)
}