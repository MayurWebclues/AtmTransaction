package com.atmtransaction.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_transaction")
data class BaseTransactionsModel(
    @PrimaryKey
    @ColumnInfo(name = "total_balance")
    var total_balance: Int = 0,
    @ColumnInfo(name = "countRupee100")
    var countRupee100: Int = 0,
    @ColumnInfo(name = "countRupee200")
    var countRupee200: Int = 0,
    @ColumnInfo(name = "countRupee500")
    var countRupee500: Int = 0,
    @ColumnInfo(name = "countRupee2000")
    var countRupee2000: Int = 0,



)
