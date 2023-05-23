import java.util.*



fun main(args: Array<String>) {
    println("Hello World!");

    //Tipos de variables
    //Variables Inmutables (No se reasignan con el =)
    val inmutable: String ="Ricardo";
    //inmutable ="Ricardo";

    //Variables mutables (Re asignar)
    var mutable: String ="Xavier";
    mutable="Ricardo";

    // val>var, dependiendo el var o el val es mejor
    //No es necesario poner punto y coma
    //Duck Typing
    val ejemploVariable = "Ricardo Paredes"
    val sueldo: Double = 1.2
    val edadEjemplo: Int = 12
    ejemploVariable.trim() //El método trim quita los espacios
    //ejemploVariable=edadEjemplo;

    //Variables primitiva
    val nombreProfesor: String="Adrian Eguez"
    val sueldo1:Double=1.2
    val estadoCivil: Char ='C'
    val mayorEdad: Boolean = true

    //Clases Java
    val fechaNacimiento: Date = Date()

    //No hay Switch pero existe uno similar llamado When
    val estadoCivilWhen = "C"
    when(estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }

    //If de una sola línea
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"


    //Llamar funciones
    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    // Named Parameters o Parámetros nombrados
    //No necesitan estar ordenados
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial=20.00, sueldo = 10.00, tasa = 14.00)


}

//Funciones
    // void -> Unit
    fun imprimirNombre (nombre : String): Unit{
        println("Nommbre: ${nombre}")
    }

    fun calcularSueldo(
        sueldo:Double, //Requerido
        tasa: Double =12.00, // Opcional (defecto)
        bonoEspecial: Double?=null, // Opción null -> nullable
        // El signo de ? significa que una variable puede ser nula en algún momento sin importar el tipo de dato que sea.
    ): Double{
        if(bonoEspecial==null){
            return sueldo * (100/tasa)
        }else{
            return sueldo * (100/tasa) + bonoEspecial
        }
    }


//Clases
abstract  class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno=uno
        this.numeroDos=dos
        println("Inicializandooooooo xd")
    }

}
abstract  class Numeros( //Constructor Primario
    //Ejemplo:
    //uno: Int, (parametro (sin modificador de acceso))
    // public var uno: Int, //Propiedad pública clase números uno
    // var uno: Int, //Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,

    protected val numeroUno: Int, //Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, //Propiedad de la clase protected numeros.numeroDos
){
    //var cedula: string ="" (public por defecto)
    // private valorCalculado: Int=0 (private)
    init{
        this.numeroUno; //El this es opcional
        this.numeroDos;
        numeroUno //Sin el this
        numeroDos
        println ("Inicializando")
    }
}

