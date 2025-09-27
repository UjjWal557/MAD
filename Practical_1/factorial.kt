fun main() {
    print("Enter a number: ")
    val n = readln().toInt()
    val res = factorial(n)
    println("Factorial of $n is: $res")
}

fun factorial(n: Int): Long {
    return if (n <= 1) 1
    else n * factorial(n - 1)
}
