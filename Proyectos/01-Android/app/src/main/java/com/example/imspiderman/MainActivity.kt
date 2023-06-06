package com.example.imspiderman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun irActividad(
    clase: Class<*>
    ){
       val intent = Intent(this, clase)
       startActivity(intent)  //Método dentro de la clase
        //StarActivity() es igual a this.StarActivity()
    }

}