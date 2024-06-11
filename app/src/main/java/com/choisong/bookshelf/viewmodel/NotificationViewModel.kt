package com.choisong.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.choisong.bookshelf.model.AlarmListData
import com.choisong.bookshelf.model.AlarmListDataResult
import com.choisong.bookshelf.network.DeleteAlarmApi
import com.choisong.bookshelf.network.DeleteAllAlarmApi
import com.choisong.bookshelf.network.GetAlarmCountApi
import com.choisong.bookshelf.network.GetAlarmListApi
import com.choisong.bookshelf.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.apache.commons.lang3.mutable.Mutable
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val getAlarmCountApi: GetAlarmCountApi,
    private val getAlarmListApi: GetAlarmListApi,
    private val deleteAlarmApi: DeleteAlarmApi,
    private val deleteAllAlarmApi: DeleteAllAlarmApi): ViewModel() {

//    val alarmListResult = serviceRepository.getAlarmList().asLiveData()

    private var _alarmCountResult = MutableLiveData<Int>()
    val alarmCountResult: LiveData<Int>
    get() = _alarmCountResult

    private var _alarmListResult = MutableLiveData<AlarmListData>()
    val alarmListResult: LiveData<AlarmListData>
    get() = _alarmListResult

    private var _deleteAlarmResult = MutableLiveData<Boolean>()
    val deleteAlarmResult: LiveData<Boolean>
    get() = _deleteAlarmResult

    private var _deleteAllAlarmResult = MutableLiveData<Boolean>()
    val deleteAllAlarmResult: LiveData<Boolean>
        get() = _deleteAllAlarmResult

    fun alarmCount(accessToken: String){
        viewModelScope.launch {
            getAlarmCountApi.getAlarmCount("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "getAlarmCount: Success $result")
                        _alarmCountResult.postValue(result!!.data)
                    }
                }else {
                    Log.d("TAG", "getAlarmCount: Failed")
                }
            }
        }
    }

    fun alarmList(accessToken: String){
        viewModelScope.launch {
            getAlarmListApi.alarmList("Bearer $accessToken").let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "alarmList: Success $result")
                        _alarmListResult.postValue(result)
                    }
                }else {
                    Log.d("TAG", "alarmList: Failed")
                }
            }
        }
    }

    fun deleteAlarm(accessToken: String, alarmIdx: Int){
        viewModelScope.launch {
            deleteAlarmApi.deleteAlarm("Bearer $accessToken", alarmIdx).let { response ->
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "deleteAlarm: Success $result")
                        _deleteAlarmResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "deleteAlarm: Failed")
                }
            }
        }
    }

    fun deleteAllAlarm(accessToken: String){
        viewModelScope.launch {
            deleteAllAlarmApi.deleteAllAlarm("Bearer $accessToken").let { response ->
                Log.d("TAG", "deleteAllAlarm: $response")
                if(response.isSuccessful){
                    response.body().let { result ->
                        Log.d("TAG", "deleteAllAlarm: Success $result")
                        _deleteAllAlarmResult.postValue(result!!.result)
                    }
                }else {
                    Log.d("TAG", "deleteAllAlarm: Failed")
                }
            }
        }
    }
}