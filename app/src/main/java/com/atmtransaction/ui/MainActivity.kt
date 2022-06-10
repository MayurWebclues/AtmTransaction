package com.atmtransaction.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.atmtransaction.R
import com.atmtransaction.databinding.MainlayoutBinding
import com.atmtransaction.db.model.AddTransactionsModel
import com.atmtransaction.utility.Utility
import com.atmtransaction.utility.toast
import com.atmtransaction.utility.validate
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by inject()
    private var transactionList: MutableList<AddTransactionsModel> = ArrayList()
    private lateinit var binding: MainlayoutBinding
    private val context = this@MainActivity
    private var check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainlayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()

    }

    private fun initUI() {
        addTextWatcher()
        addListener()
        setDataToFixAmountLayout()
        showHide()
    }

    private fun addListener() {
        binding.buttonWithdraw.setOnClickListener {
            Utility.hideKeyboard(context)
            if (!check) {
                if (binding.editText.text.toString().isBlank() && binding.editText.text.toString().isEmpty()) return@setOnClickListener
                if (viewModel.textBalanceFix < binding.editText.text.toString().toInt()) {
                    context.toast(getString(R.string.str_not_enough_balanace))
                    return@setOnClickListener
                }
                if (binding.editText.text.toString().toInt().mod(4) == 0) {
                    viewModel.withdrawCash(binding.editText.text.toString().toInt())
                    binding.editText.setText("")
                } else {
                    context.toast(getString(R.string.str_error_multiple))
                }
            }
        }

        viewModel.getAllUser().observe(this, Observer {
            if (it.isNullOrEmpty().not()) {
                transactionList.clear()
                transactionList.addAll(it)
                val adapter = TransactionAdapter(transactionList)
                binding.rvRecyclerView.layoutManager = LinearLayoutManager(context)
                binding.rvRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                setDataToLastTransection()
                setDataToFixAmountLayout()
                showHide()
            } else {
                Log.e("print", "it.toString()")
            }
        })

        viewModel.getBaseTransection().observe(this) {
            if (it != null) {
                viewModel.textAmountFix100 = it.countRupee100
                viewModel.textAmountFix200 = it.countRupee200
                viewModel.textAmountFix500 = it.countRupee500
                viewModel.textAmountFix2000 = it.countRupee2000
                viewModel.textBalanceFix = it.total_balance
                setDataToFixAmountLayout()
            }
        }
    }

    private fun showHide() {
        if (transactionList.size > 0) {
            binding.lastlistLayout!!.visibility = View.VISIBLE
            binding.llAlllist!!.visibility = View.VISIBLE
        } else {
            binding.lastlistLayout!!.visibility = View.GONE
            binding.llAlllist!!.visibility = View.GONE
        }
    }

    private fun setDataToFixAmountLayout() {
        binding.fixLayout.textBalanceFix.text = viewModel.textBalanceFix.toString()
        binding.fixLayout.textAmountFix100.text = viewModel.textAmountFix100.toString()
        binding.fixLayout.textAmountFix200.text = viewModel.textAmountFix200.toString()
        binding.fixLayout.textAmountFix500.text = viewModel.textAmountFix500.toString()
        binding.fixLayout.textAmountFix2000.text = viewModel.textAmountFix2000.toString()
    }

    private fun setDataToLastTransection() {
        var lastIndex = transactionList.size - 1
        binding.lastTransactionLayout.textLastWithdraw.text = "Rs.${transactionList[lastIndex].withdraw_amount.toString()}"
        binding.lastTransactionLayout.textLast100.text = transactionList[lastIndex].rupee100.toString()
        binding.lastTransactionLayout.textLast200.text = transactionList[lastIndex].rupee200.toString()
        binding.lastTransactionLayout.textLast500.text = transactionList[lastIndex].rupee500.toString()
        binding.lastTransactionLayout.textLast2000.text = transactionList[lastIndex].rupee2000.toString()

    }

    private fun addTextWatcher() {
        binding.editText.validate {
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
}