package com.atmtransaction.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atmtransaction.db.dao.TransactionsDao
import com.atmtransaction.db.model.AddTransactionsModel
import com.atmtransaction.db.model.BaseTransactionsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainViewModel(val transactDao: TransactionsDao) : ViewModel() {

    var userAmout: Int = 0
    fun getAllUser(): LiveData<List<AddTransactionsModel>> = transactDao.getAllTransactionData()
    fun getBaseTransection(): LiveData<BaseTransactionsModel> = transactDao.getBaseTransactionData()

    var textBalanceFix = 50000
    var textAmountFix100 = 75
    var textAmountFix200 = 50
    var textAmountFix500 = 25
    var textAmountFix2000 = 10

    fun addTransaction(addTransact: AddTransactionsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            transactDao.insertBaseTransaction(
                BaseTransactionsModel(
                    textBalanceFix, textAmountFix100,
                    textAmountFix200,
                    textAmountFix500,
                    textAmountFix2000
                )
            )
            transactDao.insertTransaction(addTransact)
        }
    }


    private val noteMultiple = intArrayOf(2000, 500, 200, 100)
    private var noofNotes = intArrayOf(10, 25, 50, 75)
    private var count = intArrayOf(0, 0, 0, 0)
    private var totalNotes = 0

    init {
        calculateTotal()
    }

    open fun calculateTotal() {
        for (i in noteMultiple.indices) {
            totalNotes = totalNotes + noteMultiple[i] * noofNotes[i]
        }
    }

    @Synchronized
    open fun withdrawCash(amount: Int) {
        userAmout = amount
        var amount = amount
        if (amount <= totalNotes) {
            for (i in noteMultiple.indices) {
                if (noteMultiple[i] <= amount) {
                    val noteCount = amount / noteMultiple[i]
                    if (noofNotes[i] > 0) {
                        count[i] = if (noteCount >= noofNotes[i]) noofNotes[i] else noteCount
                        noofNotes[i] = if (noteCount >= noofNotes[i]) 0 else noofNotes[i] - noteCount
                        totalNotes = totalNotes - count[i] * noteMultiple[i]
                        amount = amount - count[i] * noteMultiple[i]
                    }
                }
            }
            displayNotes()
        } else {
            println("Unable to dispense cash at this moment for this big amount")
        }
    }

    private fun displayNotes() {
        val model = AddTransactionsModel()
        for (i in count.indices) {
            if (count[i] != 0) {
                println(noteMultiple[i].toString() + " * " + count[i] + " = " + noteMultiple[i] * count[i])
                when (noteMultiple!![i]) {
                    100 -> {
                        model.rupee100 = count[i]
                        textAmountFix100 -= count[i]
                    }
                    200 -> {
                        model.rupee200 = count[i]
                        textAmountFix200 -= count[i]
                    }
                    500 -> {
                        model.rupee500 = count[i]
                        textAmountFix500 -= count[i]
                    }
                    2000 -> {
                        model.rupee2000 = count[i]
                        textAmountFix2000 -= count[i]
                    }
                }
            }
        }

        model.withdraw_amount = userAmout
        textBalanceFix -= userAmout
        addTransaction(model)
    }




}