package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Long.second() = this * SECOND
fun Long.minute() = this * MINUTE
fun Long.hour() = this * HOUR
fun Long.day() = this * DAY


/**
 *
0с - 1с "только что"
1с - 45с "несколько секунд назад"
45с - 75с "минуту назад"
75с - 45мин "N минут назад"
45мин - 75мин "час назад"
75мин 22ч "N часов назад"
22ч - 26ч "день назад"
26ч - 360д "N дней назад"

>360д "более года назад"
 */
fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = this.time - date.time
    return when {
        abs(diff) in 0..1050 -> "только что"
        abs(diff) in 1000..45 * SECOND -> diff.getHumanTime("несколько секунд")
        abs(diff) in 46 * SECOND..75 * SECOND -> diff.getHumanTime("минуту")
        abs(diff) in 76 * SECOND..45 * MINUTE + 1 -> diff.getHumanTime(
            "${abs(diff) / MINUTE} ${(abs(diff) / MINUTE).pluralForms(
                "минуту",
                "минуты",
                "минут"
            )}"
        )
        abs(diff) in 45 * MINUTE..75 * MINUTE + 1 -> diff.getHumanTime("час")
        abs(diff) in 75 * MINUTE..23 * HOUR -> diff.getHumanTime(
            "${abs(diff) / HOUR} ${(abs(diff) / HOUR).pluralForms(
                "час",
                "часа",
                "часов"
            )}"
        )
        abs(diff) in 22 * HOUR..26 * HOUR + 1 -> diff.getHumanTime("день")
        abs(diff) - 1 in 26 * HOUR..360 * DAY + 1 -> diff.getHumanTime(
            "${abs(diff) / DAY} ${(abs(diff) / DAY).pluralForms(
                "день",
                "дня",
                "дней"
            )}"
        )
        abs(diff) >= 360 * DAY -> if (diff < 0) "более года назад" else "более чем через год"
        else -> ""
    }
}


fun Long.getHumanTime(stringTime: String): String {
    return if (this < 0) "$stringTime назад" else "через $stringTime"
}


fun Long.pluralForms(form1: String, form2: String, form3: String): String {
    val res = abs(this) % 100
    return when {
        res in 11..19 -> form3
        res % 10 in 2..4 -> form2
        res % 10 == 1.toLong() -> form1
        else -> form3
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}