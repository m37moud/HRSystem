package com.hrappv.data.models


data class DayDetails(
    var day: String = "",
    var wardia: String = "",
    var typeOfDay: String = "",
    var partTime: Double = 0.0,
    var earlyAccess: String = "",
    var earlyAccessNote: String = "",
    var notes: String = ""
){
    fun getMembersDayDetails() = listOf(
        day,
        wardia,
        typeOfDay,
        partTime,
        earlyAccess,
        earlyAccessNote,
        notes

    )

//    fun getResult(day :String) : DayDetails{
//        if ( This.day == day){
//
//        }
//
//    }
}

