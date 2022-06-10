package com.atmtransaction.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_transaction")
data class AddTransactionsModel(
    @PrimaryKey(autoGenerate = true)
    var transactionID: Int? = null,
    @ColumnInfo(name = "withdraw_amount")
    var withdraw_amount: Int = 0,
    @ColumnInfo(name = "total_balance")
    var total_balance: Int = 0,
    @ColumnInfo(name = "rupee100")
    var rupee100: Int = 0,
    @ColumnInfo(name = "rupee200")
    var rupee200: Int = 0,
    @ColumnInfo(name = "rupee500")
    var rupee500: Int = 0,
    @ColumnInfo(name = "rupee2000")
    var rupee2000: Int = 0,



)
