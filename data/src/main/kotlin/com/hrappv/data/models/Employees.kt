package com.hrappv.data.models

data class Employees(
   val emp_id: Long,
   val fname: String,
   val department_name: String,
   val totaldays: Long,
   val bith_day: String,
   val salary: Float,
   val vacanition: Long,
   val vbalance: Long,
   val bdl_balance: Long?
)
