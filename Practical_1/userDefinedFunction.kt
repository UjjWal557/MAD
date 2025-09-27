fun main() {
    print("Enter first number: ")
    val num1 = readln().toDouble()
    print("Enter second number: ")
    val num2 = readln().toDouble()

    operation(num1, num2)
}
fun operation(a: Double, b: Double) {
    println("Addition: ${a + b}")
    println("Subtraction: ${a - b}")
    println("Multiplication: ${a * b}")
    println("Division: ${ if (b != 0.0) a / b else "Cannot divide by zero"}")
}
