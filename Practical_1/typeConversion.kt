fun main() {
    val a: Int = 42
    val b: Double = a.toDouble()
    println("Integer to Double: $b")
    println("Type of a="+a::class.simpleName+"\nType of b="+b::class.simpleName)

    val s1: String = "123"
    val s2: Int = s1.toInt()
    println("String to Integer: $s2")
    println("Type of s1="+s1::class.qualifiedName+"\nType of s2="+s2::class.qualifiedName)

    val str: String = "3.14159"
    val db: Double = str.toDouble()
    println("String to Double: $db")
    println("Type of str="+str::class.qualifiedName+"\nType of db="+db::class.simpleName)
}
