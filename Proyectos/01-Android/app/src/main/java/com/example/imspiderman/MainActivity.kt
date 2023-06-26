package com.example.imspiderman

<<<<<<< HEAD
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
=======
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
>>>>>>> main

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
<<<<<<< HEAD
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener{
            irActividad(AACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener {
            irActividad(BListView::class.java)
        }

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
    /*

    fun irActividad(
    clase: Class<*>
    ){
       val intent = Intent(this, clase)
       startActivity(intent)  //Método dentro de la clase
        //StarActivity() es igual a this.StarActivity()
    }*/

=======
    }
>>>>>>> main
}