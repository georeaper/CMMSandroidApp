package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import com.gkprojects.cmmsandroidapp.PdfFileMaker
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.Adapter.RvAdapterFindCustomers
import com.gkprojects.cmmsandroidapp.DataClasses.CustomCheckListWithEquipmentData
import com.gkprojects.cmmsandroidapp.DataClasses.CustomDisplayDatFieldReportEquipments
import com.gkprojects.cmmsandroidapp.DataClasses.CustomWorkOrderPDFDATA
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports
import com.gkprojects.cmmsandroidapp.DataClasses.ReportState
import com.gkprojects.cmmsandroidapp.DataClasses.Users
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.Models.FieldReportCheckListVM
import com.gkprojects.cmmsandroidapp.Models.FieldReportEquipmentVM
import com.gkprojects.cmmsandroidapp.Models.SharedViewModel
import com.gkprojects.cmmsandroidapp.Models.UsersVM
import com.gkprojects.cmmsandroidapp.Models.WorkOrdersVM
import com.gkprojects.cmmsandroidapp.R
import com.gkprojects.cmmsandroidapp.SignatureView
import com.gkprojects.cmmsandroidapp.databinding.FragmentCustomerInfoBinding
import io.github.mddanishansari.html_to_pdf.HtmlToPdfConvertor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID



class CustomerInfoFragment : Fragment() {
    private lateinit var customerViewModel: CustomerVM
    private lateinit var workOrderViewModel: WorkOrdersVM
    private lateinit var usersViewModel : UsersVM

//

    private lateinit var binding : FragmentCustomerInfoBinding
    private var customers =ArrayList<Customer>()
    val customerSearch = ArrayList<CustomerSelect>()
    private var dialog: Dialog? = null
    private var rvAdapter: RvAdapterFindCustomers? = null
    private lateinit var filterText : SearchView
    private var customerId : String?=null
    private var contractId : String?=null
    private var caseId : String?=null
    private var userId : String?=null
    private var userDetails : Users?= null
    private var reportId : String?=null
    private var reportNumber : String?=null
    private var remoteDBiD : Int?=null
    private var reportCostValue : Double?=null
    private var lastModified : String?=null
    private var version : String?=null
    private var dateCreated : String?=null
    private var clientSignature :ByteArray? = null
    private val reportStates = listOf(
        ReportState("Open", true),
        ReportState("Close", false)
    )
    private var printEquipmentList =ArrayList<CustomCheckListWithEquipmentData>()
    private var printToolsList = ArrayList<FieldReportToolsCustomData>()
    private var printInventoryList = ArrayList<FieldReportInventoryCustomData>()

    private var isReadPermissionGranted =false
    private var isWritePermissionGranted =false
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
        isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
    }


    private val openFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                val htmlContent = readFileContent(it)
                createPdfFromHtml(htmlContent, "output.pdf")
                //saveFileToInternalStorage(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerViewModel= ViewModelProvider(this)[CustomerVM::class.java]
        getCustomers()
        workOrderViewModel=ViewModelProvider(this)[WorkOrdersVM::class.java]
        usersViewModel =ViewModelProvider(this)[UsersVM::class.java]
        val sharedViewModel: SharedViewModel by activityViewModels()
        sharedViewModel.user.observe(viewLifecycleOwner, Observer {
            userId=it.UserID

            usersViewModel.getSingleUser(requireContext(),userId!!).observe(viewLifecycleOwner) {

                userDetails = it as Users
            }
        })




        //getUserDetails()


        val signatureView = SignatureView(requireContext())
        val args =this.arguments
        if( args!=null){
            reportId = args.getString("reportId")
            val sharedViewModel: SharedViewModel by activityViewModels()

            sharedViewModel.reportId.value = reportId

            Log.d("reportId","$reportId")
            workOrderViewModel.getWorkOrderByID(requireContext(),reportId!!).observe(viewLifecycleOwner,
                Observer {
                    setUpData(it as FieldReports)
                })
            getDataForEquipmentList()
            getDataForToolsList()
            getDataForSparePartsList()
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, reportStates.map { it.stateName })
        binding.customerInfoAutocompleteReportType.setAdapter(adapter)
        binding.customerInfoAutocompleteReportType.setText(reportStates[0].stateName, false)
        binding.customerInfoAutocompleteReportType.setOnKeyListener { _, _, _ -> true }

        val signBtn =binding.customerInfoImageButtonSignature
        signBtn.setOnClickListener {
            if (clientSignature!=null) {
                val bitmap = BitmapFactory.decodeByteArray(clientSignature, 0, clientSignature!!.size)
                // Display the bitmap in an ImageView
                val imageView = ImageView(requireContext())
                imageView.setImageBitmap(bitmap)
                AlertDialog.Builder(requireContext())
                    .setTitle("Your signature")
                    .setView(imageView)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Draw your signature")
                    .setView(signatureView)
                    .setPositiveButton("Save") { _, _ ->
                        val bitmap = signatureView.getSignatureBitmap()
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray = stream.toByteArray()
                        // Save byteArray to your database
                        clientSignature = byteArray
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setNeutralButton("Clear") { _, _ ->
                        signatureView.clear()
                    }
                    .create()
                dialog.setOnDismissListener {
                    (signatureView.parent as ViewGroup).removeView(signatureView)
                }
                dialog.show()
            }
        }




        val customerTv =binding.customerInfoTextViewCustomerName
        customerTv.setOnClickListener {
        setDialog()

        }
        val imageBtnSaveReport=binding.customerInfoImageButtonSaveReport
        imageBtnSaveReport.setOnClickListener {
            if (reportId ==null){
                Log.d("testWorkOrder","$userDetails")
                insertDb()
            }else
                updateDb()
        }
        val imageBtnPrintReport =binding.customerInfoImageButtonPrintReport
        imageBtnPrintReport.setOnClickListener {

            requestPermission()
            printPDFData()
            getDataForEquipmentList()
            getDataForToolsList()
            getDataForSparePartsList()
            //   openFilePicker()



        }



    }

//    private fun getUserDetails() {
//       usersViewModel.getFirstUser(requireContext()).observe(viewLifecycleOwner, Observer {
//           Log.d("getFirstUser","$it")
//           if(it==null){
//               usersViewModel.insertUser(requireContext(),Users("",null,"DemoUser","Demo","demo@email,com","+306987012345",null,"Demo","TCdemo",0,0,null,null,null))
//               usersViewModel.getFirstUser(requireContext()).observe(viewLifecycleOwner, Observer {
//                   userDetails=it as Users
//                   Log.d("getFirstUser2","$userDetails")
//               })
//           }else{
//               userDetails=it as Users
//           }
//       })
//
//        //the userBelow is a demoUser
//        //userDetails=Users(userId,null,"DemoUser","Demo","demo@email,com","+306987012345",null,"Demo",0,null,null,null)
//    }

    private fun setUpData(fieldReports: FieldReports) {
        caseId=fieldReports.CaseID
        customerId=fieldReports.CustomerID
        val sharedViewModel2: SharedViewModel by activityViewModels()
        sharedViewModel2.customerId.value=customerId
        userId=fieldReports.UserID
        contractId=fieldReports.ContractID
        clientSignature=fieldReports.ClientSignature
        remoteDBiD=fieldReports.RemoteID
        reportCostValue=fieldReports.Value
        version=fieldReports.Version
        dateCreated=fieldReports.DateCreated
        lastModified=fieldReports.LastModified
        reportNumber=fieldReports.ReportNumber
        binding.customerInfoEditTextReportNumber.setText(reportNumber)
        binding.customerInfoEditTextMainReport.setText(fieldReports.Description)
        binding.customerInfoEditTextMainSubject.setText(fieldReports.Title)
        binding.customerInfoEditTextDateCreated.setText(fieldReports.StartDate)
        binding.customerInfoEditTextDateClosed.setText(fieldReports.EndDate)
        binding.customerInfoEditTextDepartment.setText(fieldReports.Department)
        binding.customerInfoEditTextSigneeName.setText(fieldReports.ClientName)

        customerViewModel.getAllCustomerData(requireContext()).observe(viewLifecycleOwner,
            Observer {
                customers=it as ArrayList<Customer>
                val customerName= customers.find { it.CustomerID== customerId   }
                binding.customerInfoTextViewCustomerName.text=customerName!!.Name
            })
    }

    private fun updateDb() {
        val subject=binding.customerInfoEditTextMainSubject.text.toString()
        val report=binding.customerInfoEditTextMainReport.text.toString()
        val startDate=binding.customerInfoEditTextDateCreated.text.toString()
        val closedDate=binding.customerInfoEditTextDateCreated.text.toString()
        val department =binding.customerInfoEditTextDepartment.text.toString()
        val clientName =binding.customerInfoEditTextSigneeName.text.toString()
        val selectedStateName = binding.customerInfoAutocompleteReportType.text.toString()
        val selectedReportState = reportStates.find { it.stateName == selectedStateName }
        val selectedState = selectedReportState?.state
        lastModified = getCurrentDate()
        val updateFieldReport =FieldReports(
            reportId!!,
            remoteDBiD,
            reportNumber,
            report,
            startDate,
            closedDate,
            subject,
            department,
            clientName,
            selectedState,
            clientSignature,
            reportCostValue,
            lastModified,
            dateCreated,
            version,
            customerId,
            contractId,
            userId,
            caseId
        )
        Log.d("InsertWorkOrder","$updateFieldReport")
        lifecycleScope.launch { withContext(Dispatchers.Main){
            workOrderViewModel.update(requireContext(),updateFieldReport)
        } }
    }

    private fun insertDb() {
        val subject=binding.customerInfoEditTextMainSubject.text.toString()
        val report=binding.customerInfoEditTextMainReport.text.toString()
        val startDate=binding.customerInfoEditTextDateCreated.text.toString()
        val closedDate=binding.customerInfoEditTextDateCreated.text.toString()
        val department =binding.customerInfoEditTextDepartment.text.toString()
        val clientName =binding.customerInfoEditTextSigneeName.text.toString()
        val selectedStateName = binding.customerInfoAutocompleteReportType.text.toString()
        val selectedReportState = reportStates.find { it.stateName == selectedStateName }
        val selectedState = selectedReportState?.state
        var tempReportNumber :Int? =userDetails?.LastReportNumber
        Log.d("last1","$tempReportNumber")
        userId=userDetails?.UserID

        if (tempReportNumber!=null){
            tempReportNumber = tempReportNumber + 1
            Log.d("last2","$tempReportNumber")
        }
        Log.d("last3","$tempReportNumber")
        val formattedNumber = formatNumber(tempReportNumber, 8)
        reportNumber= userDetails!!.ReportPrefix + formattedNumber
        dateCreated = getCurrentDate()
        val insertFieldReport =FieldReports(UUID.randomUUID().toString(),null,reportNumber,report,startDate,closedDate,subject,department,clientName,selectedState,clientSignature,reportCostValue,lastModified,dateCreated,version,customerId,contractId,userId,caseId)
        Log.d("InsertWorkOrder","$insertFieldReport")
            lifecycleScope.launch { withContext(Dispatchers.Main){

            workOrderViewModel.insert(requireContext(),insertFieldReport)
            usersViewModel.increaseLastReportNumber(requireContext(),tempReportNumber!!,userId!!)
             } }


    }
    private fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.now().format(formatter)
    }
    private fun formatNumber(number: Int?, digits: Int): String {
        return String.format("%0${digits}d", number)
    }
    private fun getCustomers(){
        if (customerViewModel!=null){
            customerViewModel.getAllCustomerData(requireContext()).observe(viewLifecycleOwner,
                Observer {
                    customers=it as ArrayList<Customer>
                    updateAdapter(customers)
                    Log.d("CustomerInfoVM","$customers")
                })
        }

    }
    private fun setDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.dialog_searchable_spinner)
        dialog?.window?.setLayout(650,800)
        dialog?.window?.setBackgroundDrawableResource(com.google.android.material.R.drawable.m3_tabs_transparent_background)
        dialog=builder.create()
        dialog?.show()
        val recycleView: RecyclerView = dialog!!.findViewById(R.id.rv_searchable_TextView)
        this.filterText  = dialog!!.findViewById(R.id.searchView_rv_customers)
        recycleView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = rvAdapter
        }
        filterText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    filterList(p0.lowercase(Locale.ROOT),customerSearch)
                }
                return true
            }
        })
        rvAdapter!!.setOnClickListener(object : RvAdapterFindCustomers.OnClickListener{
            override fun onClick(position: Int, model: CustomerSelect) {
                var strtemp: String = model.CustomerName!!
                customerId = model.CustomerID
                binding.customerInfoTextViewCustomerName.text=strtemp
                dialog!!.dismiss()
            }
        })
    }
    private fun filterList(query: String,searchCustomer : ArrayList<CustomerSelect>) {
        val filteredList= java.util.ArrayList<CustomerSelect>()
        for (i in searchCustomer){
            if (i.CustomerName!!.lowercase(Locale.ROOT).contains(query))
                filteredList.add(i)
            Log.d("datafilterDialog", filteredList.toString())
        }
        if (filteredList.isEmpty() ){
            Toast.makeText(context,"Empty List", Toast.LENGTH_SHORT).show()

        }else{

            rvAdapter?.filterList(filteredList)
        }

    }
    private fun updateAdapter(customers: ArrayList<Customer>){
        val customerSearch = ArrayList<CustomerSelect>()
        Log.d("customers","$customers")
        for (i in customers.indices){
            val temp =CustomerSelect(customers[i].CustomerID!!,customers[i].Name!!)
            customerSearch.add(temp)
        }
        rvAdapter= RvAdapterFindCustomers(requireContext(),customerSearch)
        rvAdapter!!.filterList(customerSearch)


    }


    private fun requestPermission(){

        isReadPermissionGranted= ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED


        isWritePermissionGranted= ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED

        val permissionRequest :MutableList<String> = ArrayList()

        if(!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(!isWritePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(permissionRequest.isNotEmpty()){

            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }



    private fun saveFileToInternalStorage(uri: Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = requireContext().openFileOutput("my_file.html", Context.MODE_PRIVATE)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream?.close()
    }

    fun readFileFromInternalStorage(filename: String): String {
        return try {
            val fileInputStream = requireContext().openFileInput(filename)
            fileInputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    fun createPdfFromHtml(htmlString: String, filename: String) {
        val webView = WebView(requireContext())
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Log.d("WebViewTEST", "Error while loading HTML content: ${error.description}")
            }
        }
        webView.loadDataWithBaseURL(null, htmlString, "text/HTML", "UTF-8", null)

        val printManager = requireContext().getSystemService(Context.PRINT_SERVICE) as PrintManager
        webView.createPrintDocumentAdapter(filename).also { printAdapter ->
            printManager.print(filename, printAdapter, PrintAttributes.Builder().build())
        }
    }
    fun readFileContent(uri: Uri): String {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.bufferedReader().use { it?.readText() } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getDataForEquipmentList() {
        //reportID
        //A list that contains Equipment Info and FieldEquipmentInfo to Feed CheckForm

        workOrderViewModel.getDataEquipmentListAndCheckListByReportID(requireContext(),reportId!!).observe(
            viewLifecycleOwner,
            Observer {
                printEquipmentList =it as ArrayList<CustomCheckListWithEquipmentData>
                Log.d("testingViewModel","$printEquipmentList")
            }
        )

    }

    fun getDataForSparePartsList(){
        //reportID
        workOrderViewModel.printDataInventoryListByReportID(requireContext(),reportId!!).observe(
            viewLifecycleOwner, Observer {
                printInventoryList =it as ArrayList<FieldReportInventoryCustomData>
                Log.d("testing2","$printInventoryList")
            }
        )

    }
    fun getDataForToolsList(){
        //reportID
        workOrderViewModel.printDataToolsListByReportID(requireContext(),reportId!!).observe(
            viewLifecycleOwner,
            Observer {
                printToolsList =it as ArrayList<FieldReportToolsCustomData>
                Log.d("testing3","$printToolsList")
            }
        )

    }
    fun printPDFData(){

        workOrderViewModel.printWorkOrder(requireContext(),reportId!!).observe(
            viewLifecycleOwner,
            Observer {
                val tempData :CustomWorkOrderPDFDATA =it as CustomWorkOrderPDFDATA
                Log.d("testing4","$tempData")
                val pdfMake = PdfFileMaker(requireContext(),tempData)
                pdfMake.printEquipmentList=printEquipmentList
                pdfMake.printInventoryList=printInventoryList
                pdfMake.printToolsList=printToolsList
                pdfMake.printTest()
                Log.d("testing5","$tempData")
            }
        )



    }

}