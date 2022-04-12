package com.sharifiniax.parscalendar.di

import android.content.Context
import androidx.room.Room
import com.sharifiniax.parscalendar.data.TaskDao
import com.sharifiniax.parscalendar.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class TodoModule {

    @Provides
    @ViewModelScoped
    fun getDatabase(@ApplicationContext context: Context): TodoDatabase {

        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "TodoDatabase"
        ).build()

    }

    @Provides
    @ViewModelScoped
    fun getTaskDao( db : TodoDatabase ): TaskDao {
        return db.TaskDao()
    }




}