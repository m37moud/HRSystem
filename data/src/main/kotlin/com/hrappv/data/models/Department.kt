package com.hrappv.data.models

data class Department(
    var depart_id: Long? = null,
    var department: String = "",
    var commetion_rate: Long?= null,
    var depart_type: String?= null,
    var commetion_type: String?= null,
    var commetion_month: String?= null
)
