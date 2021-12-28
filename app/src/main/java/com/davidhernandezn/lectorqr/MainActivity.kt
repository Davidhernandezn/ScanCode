package com.davidhernandezn.lectorqr

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davidhernandezn.lectorqr.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEscanear.setOnClickListener { initEscanear()}


    }

    private fun initEscanear() {
       val integrator = IntentIntegrator(this)//.initiateScan()
       integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)//en lugar de elegir solo qr agregamos que pueda leer todosd los codigos
        integrator.setPrompt("¡Escanea Cualquier Codigo!")//añade un mensaje en la parte inferior
        integrator.setBeepEnabled(true)//genera un sonido al escanear algún codigo
        integrator.setTorchEnabled(true)//activa el flash al escanear
        integrator.initiateScan()//inicia el escaneo de codigos
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){
            if (result.contents == null){
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Buscando... ${result.contents}", Toast.LENGTH_SHORT).show()
                //var url = {result.contents}
               var mywebview = findViewById<WebView>(R.id.webview)
                mywebview.webChromeClient = object : WebChromeClient(){

                }

                mywebview.webViewClient = object : WebViewClient() {

                }
                var settings = mywebview.settings
                settings.javaScriptEnabled = true
                mywebview.loadUrl("${result.contents}")
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        var mywebview = findViewById<WebView>(R.id.webview)
        if(mywebview.canGoBack()){
            mywebview.goBack()
        }else{
            super.onBackPressed()
        }

    }
}
