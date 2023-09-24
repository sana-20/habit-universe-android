package com.sana.habituniverse.data.repository

import com.sana.habituniverse.data.entity.Habit
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Random
import java.util.UUID
import javax.inject.Inject

interface HabitRepository {
    fun getHabits(): Flow<RealmResults<Habit>>
    suspend fun getHabit(id: String): Habit?
    suspend fun deleteHabit(id: String)
    suspend fun upsertHabit(habit: Habit)
    suspend fun updateHabitCompleted(id: String, completed: Boolean)
    suspend fun createHabit(name: String, alarmHour: Int, alarmMinute: Int)
    suspend fun updateHabit(id: String, name: String, alarmHour: Int, alarmMinute: Int)
}

class HabitRepositoryImpl @Inject constructor() : HabitRepository {

    override fun getHabits(): Flow<RealmResults<Habit>> {
        val realm = Realm.getDefaultInstance()
        return realm.where(Habit::class.java).findAll().toFlow()
    }

    override suspend fun getHabit(id: String): Habit? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Habit::class.java).equalTo("id", id).findFirst()
    }

    override suspend fun deleteHabit(id: String) = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val habit = realm.where(Habit::class.java).equalTo("id", id).findFirst()
            habit?.deleteFromRealm()
        }
        realm.close()
    }

    override suspend fun upsertHabit(habit: Habit) = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(habit)
            it.insertOrUpdate(habit)
        }
        realm.close()
    }

    override suspend fun updateHabitCompleted(id: String, completed: Boolean) = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val habit = realm.where(Habit::class.java).equalTo("id", id).findFirst()
            habit?.isCompleted = completed
            if (habit != null) {
                it.copyToRealmOrUpdate(habit)
            }
        }
        realm.close()
    }

    override suspend fun createHabit(name: String, alarmHour: Int, alarmMinute: Int) = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.createObject(Habit::class.java, UUID.randomUUID().toString()).apply {
                this.name = name
                this.alarmHour = alarmHour
                this.alarmMinute = alarmMinute
            }
        }
        realm.close()
    }

    override suspend fun updateHabit(id: String, name: String, alarmHour: Int, alarmMinute: Int) = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val habit = realm.where(Habit::class.java).equalTo("id", id).findFirst()
            habit?.name = name
            habit?.alarmHour = alarmHour
            habit?.alarmMinute = alarmMinute
            if (habit != null) {
                it.insertOrUpdate(habit)
            }
        }
        realm.close()
    }

}