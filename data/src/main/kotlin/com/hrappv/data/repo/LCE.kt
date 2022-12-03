package utils

sealed class LCE <out T>{
    object LOADING : LCE<Nothing>()
    object NOACTION : LCE<Nothing>()
    data class CONTENT<T>(val data : T) : LCE<T>()
    data class ERROR (val error : String) : LCE<Nothing>()
    data class LOADINGS_STATUS (val error : String) : LCE<Nothing>()
}