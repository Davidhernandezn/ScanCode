package com.davidhernandezn.lectorqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.davidhernandezn.lectorqr.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
                Toast.makeText(this, "Valor escaneado: ${result.contents}", Toast.LENGTH_SHORT).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}