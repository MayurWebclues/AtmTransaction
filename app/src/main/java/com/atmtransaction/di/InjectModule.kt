package com.atmtransaction.di

import android.app.Application
import androidx.room.Room
import com.atmtransaction.db.AtmRoomDB
import com.atmtransaction.db.dao.TransactionsDao
import com.atmtransaction.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainViewModel = module {
    viewModel {
        MainViewModel(get())
    }
}
val trasactDB = module {

    fun provideDataBase(application: Application): AtmRoomDB {
        return Room.databaseBuilder(application, AtmRoomDB::class.java, "ROOM_DB_ATM")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AtmRoomDB): TransactionsDao {
        return dataBase.getTransactionDao
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}

