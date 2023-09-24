package com.sana.habituniverse.presentation.post

data class HabitPostState(
    val item: HabitPostItem
)

data class HabitPostItem(
    val id: String = "",
    val name: String = "",
    val alarmHour: Int = 21,
    val alarmMinute: Int = 0
)

sealed class HabitPostSideEffect {
    object GoToHome : HabitPostSideEffect()
}

sealed class HabitPostEvent {
    data class SaveHabit(val postItem: HabitPostItem) : HabitPostEvent()
    object BackClick : HabitPostEvent()
    data class NameChange(val newValue: String) : HabitPostEvent()
    data class TimeChange(val newHour: Int, val newMinute: Int) : HabitPostEvent()
}