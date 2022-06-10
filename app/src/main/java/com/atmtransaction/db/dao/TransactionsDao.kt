package com.atmtransaction.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.atmtransaction.db.model.AddTransactionsModel
import com.atmtransaction.db.model.BaseTransactionsModel

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: AddTransactionsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBaseTransaction(transaction: BaseTransactionsModel)

    @Query("SELECT * FROM add_transaction")
    fun getAllTransactionData(): LiveData<List<AddTransactionsModel>>

    @Query("SELECT * FROM base_transaction")
    fun getBaseTransactionData(): LiveData<BaseTransactionsModel>

}