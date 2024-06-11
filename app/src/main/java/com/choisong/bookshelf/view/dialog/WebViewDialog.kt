package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentWebViewDialogBinding

class WebViewDialog(private val link: String) : DialogFragment() {
    private var _binding: FragmentWebViewDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "WebViewDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWebViewDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        loadWebView(link)
    }

    private fun init() = with(binding){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = dialog?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = Color.WHITE
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun loadWebView(url:String) = with(binding){
        webView.apply {
            webViewClient = WebViewClient()
            settings.domStorageEnabled = true
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_INSET
            settings.allowFileAccess = true
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true  //메타태그 허용 여부
            settings.useWideViewPort = true  //화면 사이즈 맞추기 허용 여부
            settings.setSupportZoom(true)   //화면 줌 허용 여부
            settings.builtInZoomControls = false    //화면 확대 축소 허용 여부

            settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            settings.setEnableSmoothTransition(true)
            settings.cacheMode = WebSettings.LOAD_NO_CACHE

            if(Build.VERSION.SDK_INT>= 19){
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }else {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }

            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    pgWebLoding.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    pgWebLoding.visibility = View.GONE
                }
            }

            loadUrl(url)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}