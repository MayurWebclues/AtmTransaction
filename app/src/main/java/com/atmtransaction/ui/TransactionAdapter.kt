package com.atmtransaction.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atmtransaction.databinding.TransectionListBinding
import com.atmtransaction.db.model.AddTransactionsModel

class TransactionAdapter(private val dataList: List<AddTransactionsModel>) : RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TransectionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var model = dataList[position]
        with(holder) {
            bind(model)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(private val binding: TransectionListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(get: AddTransactionsModel) {
            binding.textAmountWithdraw.text=get.withdraw_amount.toString()
            binding.textAmount100.text=get.rupee100.toString()
            binding.textAmount200.text=get.rupee200.toString()
            binding.textAmount500.text=get.rupee500.toString()
            binding.textAmount2000.text=get.rupee2000.toString()
        }
    }
}