package com.hrappv.data.local.datastore

import com.hrappv.Day_register
import com.hrappv.GetAllDaysRegister
import com.hrappv.GetAllEmployees
import com.hrappv.data.models.Employees
import com.hrappv.data.models.RegisterAttends
import kotlinx.coroutines.flow.Flow

interface DayRegisterDataSource {

    fun getAllDaysRegister(): Flow<List<GetAllDaysRegister>>

   suspend fun checkRegisterDay (emp: String, depart: String, oDate: String) : Day_register?

     fun insertRegisterDay(dayRegister : RegisterAttends): Boolean

    suspend fun updateTheDay (day : RegisterAttends) : Boolean

     fun insertMultiDaysRegister(daysRegister: List<RegisterAttends>): Boolean

}