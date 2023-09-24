package com.sana.habituniverse.data.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Habit(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var progressDays: Int = 0,
    var startDays: Date = Date(),
    var archiveDays: Date = Date(),
    var alarmHour: Int = 0,
    var alarmMinute: Int = 0,
    var alarmDays: RealmList<Int> = RealmList(),
    var isCompleted: Boolean = false
) : RealmObject()

open class HabitProgress(
    @PrimaryKey
    var id: String = "",
    var habitId: String = "",
    var day: Int = 0,
    var progressLevel: Int = 0,
    var checkState: Int = -1
) : RealmObject()
