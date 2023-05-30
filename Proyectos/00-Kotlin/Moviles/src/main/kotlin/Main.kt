import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")
    // Tipos de variables
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";


    //  val > var

    // Duck Typing
    var ejemploVariable = " Adrian Eguez "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //  Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos
    //Tipos de arrelogs

    //ESTATICO - No se puede editar
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3,4)
    println(arregloEstatico)

    //Arreglo Dinamicos - Se pueden editar
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11);
    arregloDinamico.add(12);
    println(arregloDinamico);

    //For each -> Unit
    //Iterar en un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}");
        }
    arregloDinamico.forEach{ print(it) } //it en inglés significa eso, significa un elemento.

    arregloEstatico
        .forEachIndexed{ indice: Int, valorActual: Int ->
        println("Valor: ${valorActual}, Indice: ${indice}");
    }
    println(respuestaForEach)

    //MAP -> Muta el arreglo (Cambia al arreglo)
    // 1) Enviemos el nuevo valor de la iteración
    // 2) Nos devuelve un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() +100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map{it +15}
    println("RespuestaMapDos: ${respuestaMapDos}")


    /*
    Filter -> Filtrar el Arreglo
    1) Devolver una expresión True or False
    2) Nuevo arreglo filtrado
    */
    val respuestaFilter: List <Int> = arregloDinamico
        .filter{valorActual: Int ->
            val mayoresACinco: Boolean = valorActual>5 //Condición
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <=5 }
    println(respuestaFilter)
    println(respuestaFilterDos)


    /*
    OR AND
    OR -> ANY (Alguno cumple?)
    AND -> ALL (Todos Cumplen?)
    */
    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual:Int->
            return@any (valorActual>5)
        }
    println("La respuesta de Any es: ${respuestaAny}") //true

    val respuestaAll: Boolean = arregloDinamico
        .all{valorActual:Int ->
            return@all (valorActual>5)
        }
    println("La respuesta de ALL es: ${respuestaAll}") // False

    // Reduce -> valor acumulado
    // Valor Acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    // [1, 2, 3, 4, 5] -> Sumeme todos los valores del arreglo
    // valorIteración1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteración 1
    // valorIteración2 = valorIteración1 + 2 = 1 + 2 = 3 -> Iteración 2
    // valorIteración3 = valorIteración2 + 3 = 3 + 3 = 6 -> Iteración 3
    // valorIteración4 = valorIteración3 + 4 = 6 + 4 = 10 -> Iteración 4
    // valorIteración5 = valorIteración4 + 5 = 10 + 5 = 15 -> Iteración 5

    val respuestaReduce: Int = arregloDinamico
            // acumulado siempre empieza en 0
        .reduce{ acumulado: Int, valorActual: Int ->
            return@reduce (acumulado+valorActual) //Lógica negocio
        }
    println(respuestaReduce) //78
}

// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    println("Nombre : ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double{
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa ) + bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){ // Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor PRIMARIO
    // Ejemplo:
    // uno: Int, (Parametro (sin modificador de acceso))
    // private var uno: Int, // Propiedad Publica Clase numeros.uno
    // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,
    protected val numeroUno: Int, // Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, // Propiedad de la clase protected numeros.numeroDos
){
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}


class Suma( // Constructor Primario Suma
    uno: Int, // Parametro
    dos: Int // Parametro
) : Numeros(uno, dos) { // <- Constructor del Padre
    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor(//  Segundo constructor
        uno: Int?, // parametros
        dos: Int // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor(//  tercer constructor
        uno: Int, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    ) // Si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor(//  cuarto constructor
        uno: Int?, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )

    public fun sumar(): Int { // public por defecto, o usar private o protected
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object { // Atributos y Metodos "Compartidos"
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}








