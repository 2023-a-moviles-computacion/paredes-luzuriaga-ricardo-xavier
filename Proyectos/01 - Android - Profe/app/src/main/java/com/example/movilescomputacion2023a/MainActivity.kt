package com.example.movilescomputacion2023a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener {
                irActividad(AACicloVida::class.java)
            }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener {
                irActividad(BListView::class.java)
            }


        val callbackIntentPickUri = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === RESULT_OK)
                if (result.data != null) {
                    if (result.data!!.data != null) {
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(uri, null, null, null, null, null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        "Telefono: ${telefono}"
                    }
                }
        }
        // Implícito
        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }
        //Explícito
        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }
    }
    val callbackContenidoCIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    //Lógica de Negocio
                    val data = result.data
                    "${data?.getStringExtra("NombreModificado")}"
                }
            }
        }

        fun abrirActividadConParametros(
            clase: Class<*>
        ) {
            val intentExplicito = Intent(this, clase)
            //Enviar parámetros (Solamente variables primitivas)
            intentExplicito.putExtra("nombre", "Ricardo")
            intentExplicito.putExtra("apellido", "Paredes")
            intentExplicito.putExtra("edad", 21)
            val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
            boton.setOnClickListener { devolverRespuesta() }
            //Este call back no funcionó xd
            //Este call back ya funcionó xd
            callbackContenidoCIntentExplicito.launch(intentExplicito)
        }
        fun devolverRespuesta(){
            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado", "Nahomy")
            intentDevolverParametros.putExtra("edadModificado", 20)
            setResult(
                RESULT_OK,
                intentDevolverParametros
            )
            finish()
        }

        fun irActividad(
            clase: Class<*>
        ) {
            val intent = Intent(this, clase)
            startActivity(intent)
        }
}
