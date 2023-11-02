package com.gkprojects.cmmsandroidapp.DataClasses

data class HomeRecyclerViewData(
    var topLeft: String,
    var topRight: String,
    var MainLeft :String,
    var MaintRight :String
)

data class OverviewMainData(
    val information :String? ,
    val customerName :String? ,
    val date :String?,
    val id :String? ,
    val customerID :Int?,
    val contractID :Int?,
    val userID :Int?,
    val assetID :Int?,
    val workOrderID : Int?,
    val technicalCaseID :Int ?,
    val setTable :String ?
)
