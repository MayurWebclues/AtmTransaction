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



    private val currDenom = intArrayOf(2000, 500, 200, 100)


    private var currNo = intArrayOf(10, 25, 50, 75)


    private var count = intArrayOf(0, 0, 0, 0)
    private var totalCorpus = 0

    init {
        calcTotalCorpus()
    }

    open fun calcTotalCorpus() {
        for (i in currDenom.indices) {
            totalCorpus = totalCorpus + currDenom[i] * currNo[i]
        }
    }

    @Synchronized
    open fun withdrawCash(amount: Int) {
        userAmout = amount
        var amount = amount
        if (amount <= totalCorpus) {
            for (i in currDenom.indices) {
                if (currDenom[i] <= amount) { //If the amount is less than the currDenom[i] then that particular denomination cannot be dispensed
                    val noteCount = amount / currDenom[i]
                    if (currNo[i] > 0) { //To check whether the ATM Vault is left with the currency denomination under iteration
                        //If the Note Count is greater than the number of notes in ATM vault for that particular denomination then utilize all of them
                        count[i] = if (noteCount >= currNo[i]) currNo[i] else noteCount
                        currNo[i] = if (noteCount >= currNo[i]) 0 else currNo[i] - noteCount
                        //Deduct the total corpus left in the ATM Vault with the cash being dispensed in this iteration
                        totalCorpus = totalCorpus - count[i] * currDenom[i]
                        //Calculate the amount that need to be addressed in the next iterations
                        amount = amount - count[i] * currDenom[i]
                    }
                }
            }
            displayNotes()
            displayLeftNotes()
        } else {
            println("Unable to dispense cash at this moment for this big amount")
        }
    }

    private fun displayNotes() {
        val model = AddTransactionsModel()
        for (i in count.indices) {
            if (count[i] != 0) {
                println(currDenom[i].toString() + " * " + count[i] + " = " + currDenom[i] * count[i])
                when (currDenom!![i]) {
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
        textBalanceFix-=userAmout
        addTransaction(model)
    }

    private fun displayLeftNotes() {
        for (i in currDenom.indices) {
            println("Notes of " + currDenom[i] + " left are " + currNo[i])

        }

    }


}