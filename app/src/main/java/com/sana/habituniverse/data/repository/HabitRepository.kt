package com.sana.habituniverse.data.repository

import com.sana.habituniverse.common.utils.log
import com.sana.habituniverse.data.entity.Habit
import io.realm.Realm
import javax.inject.Inject

interface HabitRepository {
    fun getHabits()
    fun deleteHabit(id: Int)
    fun upsertHabit(habit: Habit)
}

class HabitRepositoryImpl @Inject constructor(
    private val realm: Realm
) : HabitRepository {

    override fun getHabits() {
        val habits = realm.where(Habit::class.java).findAll()
        log("==Habits: ${habits.size}==")
        habits.forEach {
            log("$it")
        }
    }

    override fun deleteHabit(id: Int) {
        realm.executeTransaction {
            val habit = realm.where(Habit::class.java).equalTo("id", id).findFirst()
            habit?.deleteFromRealm()
        }
        getHabits()
    }

    override fun upsertHabit(habit: Habit) {
        realm.executeTransaction {
            it.insertOrUpdate(habit)
        }
        getHabits()
    }
}