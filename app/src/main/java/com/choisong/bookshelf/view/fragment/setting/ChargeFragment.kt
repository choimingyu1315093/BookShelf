package com.choisong.bookshelf.view.fragment.setting

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.billingclient.api.*
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentChargeBinding
import com.choisong.bookshelf.model.PaymentItem
import com.choisong.bookshelf.model.PaymentModel
import com.choisong.bookshelf.model.TicketBuyModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.fragment.detail.DetailFragmentArgs
import com.choisong.bookshelf.viewmodel.ChargeViewModel
import com.google.common.collect.ImmutableList
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChargeFragment : Fragment(), PurchasesUpdatedListener {
    private var _binding: FragmentChargeBinding? = null
    private val binding get() = _binding!!
    private val chargeViewModel: ChargeViewModel by viewModels()

    companion object {
        const val TAG = "ChargeFragment"
    }

    private val ticket: ChargeFragmentArgs by navArgs()

    lateinit var accessToken: String

    private lateinit var billingClient: BillingClient
    private var productDetailsList: MutableList<ProductDetails> = mutableListOf()
    private lateinit var consumeListener : ConsumeResponseListener
    val tempList = listOf("3_bookshelf_buy_3", "5_bookshelf_buy_5", "7_bookshelf_buy_7", "10_bookshelf_buy_10")
    private lateinit var paymentItem: PaymentItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChargeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")

        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                findNavController().popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        billingClient = context?.let {
            BillingClient.newBuilder(it)
                .setListener(this@ChargeFragment)
                .enablePendingPurchases()
                .build()
        }!!

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                //연결이 종료될 시 재시도 요망
                Log.d(TAG, "onBillingServiceDisconnected: 연결 실패")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 연결 성공
                    Log.d(TAG, "onBillingServiceDisconnected: 연결 성공")
                    CoroutineScope(Dispatchers.Main).launch {
                        var testMutableList = mutableListOf<QueryProductDetailsParams.Product>()

                        for(element in tempList) {
                            testMutableList.add(
                                QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId(element)
                                    .setProductType(BillingClient.ProductType.INAPP)
                                    .build()
                            )
                        }

                        var testList = listOf(testMutableList)
                        var testImmutableList = ImmutableList.of(testList)

                        //5.0 마이그레이션
                        val tempParam = QueryProductDetailsParams.newBuilder()
                            .setProductList(
                                testMutableList
                            ).build()

                        billingClient.queryProductDetailsAsync(tempParam) { billingResult, mutableList ->
                            Log.d(TAG, "onBillingSetupFinished: mutableList.size ${mutableList.size}")
                            Log.d(TAG, "onBillingSetupFinished: billingResult $billingResult")
                            for(i in 0 until mutableList.size){
                                productDetailsList.add(mutableList[i])
                            }

                            Log.d(TAG, "onBillingSetupFinished: productDetailsList.size ${productDetailsList.size}")
                        }
                    }
                }
            }
        })

        consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
            Log.d(TAG, "initBillingClient: ${billingResult.responseCode}")
            Log.d(TAG, "initBillingClient: purchaseToken $purchaseToken")
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "initBillingClient: 소모 성공")
            } else {
                Log.d(TAG, "initBillingClient: 소모 실패")
            }
        }

        tvTicket.text = "낙엽 ${ticket.ticket}장"

        btnPurchase.setOnClickListener { purchaseItem(1) }
        btnPurchase2.setOnClickListener { purchaseItem(3) }
        btnPurchase3.setOnClickListener { purchaseItem(0) }
        btnPurchase4.setOnClickListener { purchaseItem(2) }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun purchaseItem(itemIndex: Int) {
        var gson = Gson()
        paymentItem = gson.fromJson(productDetailsList[itemIndex].toString().replace("ProductDetails",""), PaymentItem::class.java)

        var flowProductDetailParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetailsList[itemIndex])
            .build()

        var flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(flowProductDetailParams))
            .build()

        val responseCode = billingClient.launchBillingFlow(requireActivity(), flowParams).responseCode
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        Log.d(TAG, "onPurchasesUpdated: purchases ${purchases?.size}")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                Log.d(TAG, "onPurchasesUpdated: 구매 성공")
                Log.d(TAG, "onPurchasesUpdated: purchase $purchase")

                // 거래 성공 코드
                val consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                billingClient.consumeAsync(consumeParams, consumeListener)

                Log.d(TAG, "onPurchasesUpdated: product ${purchase.products[0]}")
                when(purchase.products[0]){
                    "3_bookshelf_buy_3" -> payment(3)
                    "5_bookshelf_buy_5" -> payment(5)
                    "7_bookshelf_buy_7" -> payment(7)
                    "10_bookshelf_buy_10" -> payment(10)
                }

            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // 유저 취소 errorcode
        }
    }

    private fun payment(ticket_count: Int) {
        val ticketBuyModel = TicketBuyModel(ticket_count)
        chargeViewModel.buyTicket(accessToken, ticketBuyModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }
}