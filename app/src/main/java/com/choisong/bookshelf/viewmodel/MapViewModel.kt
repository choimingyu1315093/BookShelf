package com.choisong.bookshelf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.choisong.bookshelf.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val serviceRepository: ServiceRepository): ViewModel(){

    val iWishBookHaveUserList = serviceRepository.getIWishBookHaveUserList().asLiveData()
}