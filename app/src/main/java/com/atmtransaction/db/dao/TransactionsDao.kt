package com.atmtransaction.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.atmtransaction.db.model.AddTransactionsModel

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: AddTransactionsModel)

    @Query("SELECT * FROM add_transaction")
    fun getAllTransactionData(): LiveData<List<AddTransactionsModel>>

}