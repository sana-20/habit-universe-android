package com.sana.habituniverse.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sana.habituniverse.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HabitHomeViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ContainerHost<HabitHomeState, HabitHomeSideEffect>, ViewModel() {

    override val container = container<HabitHomeState, HabitHomeSideEffect>(HabitHomeState.Loading)

    init {
        getHabits()
    }

    fun onEvent(event: HabitHomeEvent) = intent {
        viewModelScope.launch {
            when (event) {
                is HabitHomeEvent.ArchiveHabit -> {
                    habitRepository.updateHabitCompleted(event.id, true)
                }

                is HabitHomeEvent.DeleteHabit -> {
                    habitRepository.deleteHabit(event.id)
                }

                is HabitHomeEvent.EditHabit -> {
                    postSideEffect(HabitHomeSideEffect.GoToPost(event.id))
                }

                is HabitHomeEvent.ClickHabit -> {
                    postSideEffect(HabitHomeSideEffect.GoToDetail(event.id))
                }
            }
        }
    }

    private fun getHabits() = intent {
        viewModelScope.launch {
            habitRepository.getHabits()
                .collect {
                    val habits = it.map { habit ->
                        HabitHomeItem(
                            id = habit.id,
                            name = habit.name,
                            progressDays = habit.progressDays,
                            isCompleted = habit.isCompleted
                        )
                    }
                    reduce {
                        HabitHomeState.Loaded(habits)
                    }
                }
        }
    }
}