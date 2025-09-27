class Car(
    val type: String,
    val model: Int,
    val owner: String,
    val originalPrice: Double,
    val miles: Int
) {
    init {
        println("Object is created, init is called")
    }
    constructor(type: String, model: Int) : this(type, model, "Unknown", 0.0, 0)

    fun getCarInfo(): String {
        return "$type, $model"
    }

    fun getCurrentPrice(): Double {
        return (originalPrice - (miles*10))
    }

    fun displayCarInfo() {
        println("Car Information: ${getCarInfo()}")
        println("Car Owner: $owner")
        println("Miles Driven: $miles")
        println("Original Car Price: $originalPrice")
        println("Current Car Price: ${getCurrentPrice()}\n")
    }
}

fun main() {
    val car1 = Car("BMW", 2018, "Aman", 500000.0, 135)
    val car2 = Car("Toyota", 2017, "KJS", 450000.0, 92)
    val car3 = Car("Maruti", 2020)

    val carList = listOf(car1, car2, car3)

    for (car in carList) {
        car.displayCarInfo()
    }
}

