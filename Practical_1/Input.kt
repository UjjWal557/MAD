fun main() {
    print("Enter Enrollment No.: ")
    val enroll = readln()

    print("Enter Name: ")
    val name = readln()

    print("Enter Age: ")
    val age = readln().toInt()

    print("Enter Branch: ")
    val branch = readln()

    print("Enter Class: ")
    val cname = readln()

    print("Enter Batch: ")
    val batch = readln()

    print("Enter College Name: ")
    val college = readln()

    print("Enter University Name: ")
    val university = readln()

    println("""
    ***********************
    Student's Data:
    Enrollment No.: $enroll
    Name: $name
    Age: $age
    Branch: $branch
    Class: $cname
    Batch: $batch
    College Name: $college
    University Name: $university
""".trimIndent())
}