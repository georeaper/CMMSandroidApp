package com.gkprojects.cmmsandroidapp

import android.content.Context
import android.util.Log
import com.gkprojects.cmmsandroidapp.DataClasses.CustomCheckListWithEquipmentData
import io.github.mddanishansari.html_to_pdf.HtmlToPdfConvertor
import java.io.File
import com.gkprojects.cmmsandroidapp.DataClasses.CustomWorkOrderPDFDATA
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportInventoryCustomData
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportInventoryVM
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportToolsCustomData
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportToolsVM
import com.gkprojects.cmmsandroidapp.Models.FieldReportCheckListVM
import com.gkprojects.cmmsandroidapp.Models.FieldReportEquipmentVM

class PdfFileMaker(private val context: Context, private val customWorkOrderPdfData: CustomWorkOrderPDFDATA) {
   // ,private val customToolsList :ArrayList<FieldReportToolsCustomData>
    var printEquipmentList = ArrayList<CustomCheckListWithEquipmentData>()
    var printToolsList = ArrayList<FieldReportToolsCustomData>()
    var printInventoryList =ArrayList<FieldReportInventoryCustomData>()
    fun printTest() {
//        Log.d("testingPrint","$printEquipmentList")
//        Log.d("testingPrint2","$printToolsList")
//        Log.d("testingPrint3","$printInventoryList")
        val uniqueEquipmentData: List<CustomCheckListWithEquipmentData> = printEquipmentList.distinctBy { it.EquipmentId }
        Log.d("testingPrint3","$uniqueEquipmentData")

        //bellow is the files in the internal storage that act as a template and their filepath

        val filename = "myFileMainAndTools.html" // replace with your file's name
        val filenameItems = "myFileEquipmentAndSpareParts.html"
        val filenameCheckForm ="myFileCheckForms.html"
        val file = context.getFileStreamPath(filename)
        val fileEquipment =context.getFileStreamPath(filenameItems)
        val fileCheckform =context.getFileStreamPath(filenameCheckForm)

        val data = customWorkOrderPdfData


        val toolIterator = printToolsList.iterator()
        val equipmentIterator=uniqueEquipmentData.iterator()
//        val checkformIterator=checkformList.iterator()
        val sparePartsIterator=printInventoryList.iterator()


       val stringBuilder = StringBuilder()

        file?.bufferedReader()?.useLines {
            lines ->
            lines.forEach { line ->
                //Log.d("pdfLine", line +" "+)
                var modifiedLine = line
                modifiedLine = modifiedLine.replace("#CUSTOMER_NAME#", data.customerName!!)
                modifiedLine = modifiedLine.replace("#START_DATE#", data.startDate!!)
                modifiedLine = modifiedLine.replace("#DEPARTMENT_WORKORDER#", data.departmentWorkOrder!!)
                modifiedLine = modifiedLine.replace("#END_DATE#", data.endDate!!)
                modifiedLine = modifiedLine.replace("#REPORT_NUMBER#", data.reportNumber!!)
                modifiedLine = modifiedLine.replace("#USERS_NAME#", data.usersName!!)
                modifiedLine = modifiedLine.replace("#SIGNE_NAME#", data.signeName!!)
                modifiedLine = modifiedLine.replace("#REPORT_TITLE#", data.reportTitle!!)
                modifiedLine = modifiedLine.replace("#DETAILED_REPORT#", data.detailedReport!!)

                while (toolIterator.hasNext() && "#ToolName#" in modifiedLine && "#ToolSerialNumber#" in modifiedLine && "#ToolCalDate#" in modifiedLine) {
                    val tool = toolIterator.next()
                    modifiedLine = modifiedLine.replaceFirst("#ToolName#", tool.toolsTitle)
                    modifiedLine = modifiedLine.replaceFirst("#ToolSerialNumber#", tool.toolsSerialNumber)
                    modifiedLine = modifiedLine.replaceFirst("#ToolCalDate#", tool.toolsCalDate)
                }

                stringBuilder.append(modifiedLine)
                stringBuilder.append("\n")



            }

        }

       fileEquipment?.bufferedReader()?.useLines {
           lines ->
           lines.forEach {line ->
               var modifiedLine = line
               while (equipmentIterator.hasNext() && "#EquipmentManufacturer#" in modifiedLine
                   && "#EquipmentModel#" in modifiedLine && "#EquipmentSerialNumber#" in modifiedLine && "#EquipmentCategory#" in modifiedLine) {
                   val equipment = equipmentIterator.next()
                   modifiedLine = modifiedLine.replaceFirst("#EquipmentManufacturer#", equipment.EquipmentManufacturer!!)
                   modifiedLine = modifiedLine.replaceFirst("#EquipmentModel#", equipment.EquipmentModel!!)
                   modifiedLine = modifiedLine.replaceFirst("#EquipmentSerialNumber#", equipment.EquipmentSerialNumber!!)
                   modifiedLine = modifiedLine.replaceFirst("#EquipmentCategory#", equipment.EquipmentCategory!!)
               }
               stringBuilder.append(modifiedLine)
               stringBuilder.append("\n")
           }
       }
       val temp = processFile(fileCheckform,printEquipmentList)
        stringBuilder.append(temp)
        stringBuilder.append("\n")


       val replaceString = replaceCustomVariables(stringBuilder.toString())

       val filenamePDF = "my_filetemp.pdf" // replace with your file's name
       val filePDF = context.getFileStreamPath(filenamePDF)
       pdfCreateFile(replaceString,filePDF)
    }

    private fun pdfCreateFile(htmlString :String ,pdfLocation : File){
        val htmlToPdfConvertor = HtmlToPdfConvertor(context)
        htmlToPdfConvertor.convert(
            pdfLocation = pdfLocation, // the file location where pdf should be saved
            htmlString = htmlString, // the HTML string to be converted
            onPdfGenerationFailed = { exception -> // something went wrong, handle the exception (this param is optional)
                exception.printStackTrace()
                Log.d("pdfTracker","$exception")
            },
            onPdfGenerated = { pdfFile -> // pdf was generated, do whatever you want with it
//                openPdf(pdfFile)
            })
    }
}
fun replaceCustomVariables(input: String): String {
    val regex = "#[^#]*#".toRegex()
    return regex.replace(input, "")
}
fun processFile(file: File?, items: List<CustomCheckListWithEquipmentData>): StringBuilder {
    val stringBuilder = StringBuilder()

    val itemsGroupedByEquipmentId = items.groupBy { it.EquipmentId }

    itemsGroupedByEquipmentId.forEach { (equipmentId, items) ->
        val iterator = items.iterator()

        file?.bufferedReader()?.useLines { lines ->
            lines.forEach { line ->
                var modifiedLine = line
                while (iterator.hasNext() && "#CheckFormQ#" in modifiedLine
                    && "#CheckFormL#" in modifiedLine && "#CheckFormM#" in modifiedLine && "#CheckFormR#" in modifiedLine) {
                    Log.d("testingDebug","$line")
                    val item = iterator.next()
                    modifiedLine = modifiedLine.replaceFirst("#CheckFormQ#", item.FieldCheckListDescription ?: "")
                    modifiedLine = modifiedLine.replaceFirst("#CheckFormL#", item.FieldCheckListLimit ?: "")
                    modifiedLine = modifiedLine.replaceFirst("#CheckFormM#", item.FieldCheckListMeasure ?: "")
                    modifiedLine = modifiedLine.replaceFirst("#CheckFormR#", item.FieldCheckListResult ?: "")
                }
                stringBuilder.append(modifiedLine)
                stringBuilder.append("\n")
            }
        }
    }

    return stringBuilder
}

