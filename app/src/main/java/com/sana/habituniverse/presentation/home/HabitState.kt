package com.sana.habituniverse.presentation.home

sealed class HabitHomeState {
    object Loading : HabitHomeState()
    data class Loaded(val habits: List<HabitHomeItem>) : HabitHomeState()
}

data class HabitHomeItem(
    val id: String,
    val name: String,
    val progressDays: Int,
    val isCompleted: Boolean
)

sealed class HabitHomeSideEffect {
    data class GoToDetail(val id: String) : HabitHomeSideEffect()
    data class GoToPost(val id: String) : HabitHomeSideEffect()
}

sealed class HabitHomeEvent {
    data class ArchiveHabit(val id: String) : HabitHomeEvent()
    data class DeleteHabit(val id: String) : HabitHomeEvent()
    data class EditHabit(val id: String) : HabitHomeEvent()
    data class ClickHabit(val id: String) : HabitHomeEvent()
}

enum class ProgressState(val value: Int) {
    IN_PROGRESS(0),
    COMPLETED(1)
}