package com.atmtransaction.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atmtransaction.db.dao.TransactionsDao
import com.atmtransaction.db.model.AddTransactionsModel
import com.atmtransaction.db.model.BaseTransactionsModel

@Database(entities = [AddTransactionsModel::class,BaseTransactionsModel::class], version = 1, exportSchema = false)
abstract class AtmRoomDB : RoomDatabase() {

    abstract val getTransactionDao: TransactionsDao


    /**
     *  Creates a way to ensure that the database accessed in different locations is the same
     *  instance. Also known as a Singleton pattern. Further explained in the History Repository.
     */
    /*companion object {
        private var INSTANCE: AtmRoomDB? = null

        fun getDatabase(context: Context) = INSTANCE ?: kotlin.run {
            Room.databaseBuilder(
                context.applicationContext,
                AtmRoomDB::class.java,
                "ROOM_DB_NAME"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }*/
}