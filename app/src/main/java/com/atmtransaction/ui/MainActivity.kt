package com.atmtransaction.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.atmtransaction.R
import com.atmtransaction.databinding.ActivityMainBinding
import com.atmtransaction.db.model.AddTransactionsModel
import com.atmtransaction.utility.phoneWatcher
import com.atmtransaction.utility.toast
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by inject()
    private var transactionList: MutableList<AddTransactionsModel> = ArrayList()
    private lateinit var binding: ActivityMainBinding
    private val context = this@MainActivity
    private var check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()

    }

    private fun initUI() {
        addTextWatcher()
        addListener()

    }

    private fun addListener() {
        binding.buttonWithdraw.setOnClickListener {
            if (!check) {
                if (binding.editText.text.toString().isBlank() && binding.editText.text.toString().isEmpty()) return@setOnClickListener
                if (binding.editText.text.toString().toInt().mod(4) == 0) {
                    context.toast(getString(R.string.done))
                    viewModel.withdrawCash(binding.editText.text.toString().toInt())
                    binding.editText.setText("")
                } else {
                    context.toast(getString(R.string.str_error_multiple))
                }

            }
        }

        viewModel.getAllUser().observe(this, Observer {
            if (it.isNullOrEmpty().not()) {

                Log.e("print", it.toString())
                transactionList.addAll(it)
                setDataToLastTransection()
            } else {
                Log.e("print", "it.toString()")
            }
        })

        setDataToFixAmountLayout()
    }

    private fun setDataToFixAmountLayout() {
        binding.fixLayout.textBalanceFix.text = viewModel.textBalanceFix.toString()
        binding.fixLayout.textAmountFix100.text = viewModel.textAmountFix100.toString()
        binding.fixLayout.textAmountFix200.text = viewModel.textAmountFix200.toString()
        binding.fixLayout.textAmountFix500.text = viewModel.textAmountFix500.toString()
        binding.fixLayout.textAmountFix2000.text = viewModel.textAmountFix2000.toString()
    }

    private fun setDataToLastTransection() {
        var lastIndex = transactionList.size-1
        Log.e("print", "it.toString()" + transactionList[0].withdraw_amount.toString())
        binding.lastTransactionLayout.textLastWithdraw.text = "Rs ${transactionList[lastIndex].withdraw_amount.toString()}"
        binding.lastTransactionLayout.textLast100.text = transactionList[lastIndex].rupee100.toString()
        binding.lastTransactionLayout.textLast200.text = transactionList[lastIndex].rupee200.toString()
        binding.lastTransactionLayout.textLast500.text = transactionList[lastIndex].rupee500.toString()
        binding.lastTransactionLayout.textLast2000.text = transactionList[lastIndex].rupee2000.toString()

    }

    private fun addTextWatcher() {
        binding.editText.phoneWatcher {
            setUiAsValidation(it)
        }
    }

    private fun setUiAsValidation(it: Boolean): Boolean {
        check = it
        if (check) {
            context.toast(getString(R.string.str_error_valid_amount))
        }
        return check
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}