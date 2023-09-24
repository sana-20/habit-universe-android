package com.sana.habituniverse.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sana.habituniverse.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HabitPostViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ContainerHost<HabitPostState, HabitPostSideEffect>, ViewModel() {

    override val container: Container<HabitPostState, HabitPostSideEffect> =
        container(HabitPostState(HabitPostItem()))

    fun load(id: String?) = intent {
        viewModelScope.launch {
            if (id == null) return@launch
            val habit = habitRepository.getHabit(id)
            reduce {
                HabitPostState(
                    HabitPostItem(
                        id = id,
                        name = habit?.name ?: "",
                        alarmHour = habit?.alarmHour ?: 21,
                        alarmMinute = habit?.alarmMinute ?: 0
                    )
                )
            }
        }
    }

    fun onEvent(event: HabitPostEvent) = intent {
        viewModelScope.launch {
            when (event) {
                is HabitPostEvent.SaveHabit -> {
                    val item = event.postItem
                    if (item.id.isEmpty()) {
                        habitRepository.createHabit(
                            name = item.name,
                            alarmHour = item.alarmHour,
                            alarmMinute = item.alarmMinute
                        )

                    } else {
                        habitRepository.updateHabit(
                            id = item.id,
                            name = item.name,
                            alarmHour = item.alarmHour,
                            alarmMinute = item.alarmMinute
                        )
                    }
                    postSideEffect(HabitPostSideEffect.GoToHome)
                }

                HabitPostEvent.BackClick -> {
                    postSideEffect(HabitPostSideEffect.GoToHome)
                }

                is HabitPostEvent.NameChange -> {
                    reduce {
                        HabitPostState(item = state.item.copy(name = event.newValue))
                    }
                }

                is HabitPostEvent.TimeChange -> {
                    reduce {
                        HabitPostState(
                            item = state.item.copy(
                                alarmHour = event.newHour,
                                alarmMinute = event.newMinute
                            )
                        )
                    }
                }
            }
        }
    }
}