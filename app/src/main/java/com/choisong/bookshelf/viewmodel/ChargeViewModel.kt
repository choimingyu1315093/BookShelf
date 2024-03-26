package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.TicketBuyModel
import com.choisong.bookshelf.network.PatchTicketBuyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeViewModel @Inject constructor(private val ticketBuyApi: PatchTicketBuyApi): ViewModel(){

    fun buyTicket(accessToken: String, ticketBuyModel: TicketBuyModel){
        viewModelScope.launch {
            ticketBuyApi.buyTicket("Bearer $accessToken", ticketBuyModel).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "buyTicket: Success $result")
                    }
                }else {
                    Log.d("TAG", "buyTicket: Failed")
                }
            }
        }
    }
}