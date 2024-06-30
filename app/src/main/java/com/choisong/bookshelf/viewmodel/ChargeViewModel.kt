package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choisong.bookshelf.model.TicketBuyModel
import com.choisong.bookshelf.model.TicketLogDataResult
import com.choisong.bookshelf.network.GetTicketLogApi
import com.choisong.bookshelf.network.PatchBuyTicketsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeViewModel @Inject constructor(private val getTicketLogApi: GetTicketLogApi, private val buyTicketApi: PatchBuyTicketsApi): ViewModel(){

    private var _ticketLogList = MutableLiveData<ArrayList<TicketLogDataResult>>()
    val tickerLogoList: LiveData<ArrayList<TicketLogDataResult>>
    get() = _ticketLogList

    fun getTicketLog(accessToken: String){
        viewModelScope.launch {
            getTicketLogApi.ticketLogList("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "ticketLogList: Success $result")
                        _ticketLogList.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "ticketLogList: Failed")
                }
            }
        }
    }

    fun buyTicket(accessToken: String, ticketBuyModel: TicketBuyModel){
        viewModelScope.launch {
            buyTicketApi.buyTickets("Bearer $accessToken", ticketBuyModel).let { response ->
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