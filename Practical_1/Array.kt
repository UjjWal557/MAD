fun main()
{
    println("Array-1 using arrayOf() method:")
    val ar1 =arrayOf(1,2,3,4,5)
    println(ar1.contentToString())

    println("Array-2 using Array<>?() method:")
    val ar2=Array<Int>(5){0}
    println(ar2.contentToString())

    println("Array-3 using Array<>?() and lambda function:")
    val ar3=Array<Int>(6){it}
    println(ar3.contentToString())

    println("Array-4 using IntArray() method:")
    val ar4=IntArray(5){it}  //{it} generates an array of size 5, from value 0...
    println(ar4.contentToString())

    println("Array-5 using intArrayOf():")
    val ar5=intArrayOf(1,2,3,4,5)
    println(ar5.contentToString())

    println("2D Array-6 using arrayOf() and intArray() method:")
    val ar6=arrayOf(intArrayOf(1,2,3),intArrayOf(4,5,6),intArrayOf(7,8,9))
    println(ar6.contentDeepToString())

    println("Array-7 using user input:")
    val ar7=IntArray(5)
    for(i in 0 until ar7.size )
    {
        print("a[$i]=")
        ar7[i]=readln().toInt()
    }
    println("Created Array:")
    println(ar7.joinToString(","))

    println("Sorting with Built-in function:")
    val arr=ar7.sorted()
    println(arr.joinToString(","))

    println("Sorting without Built-in function:")
    for( i in 0 until ar7.size-1 )
    {
        for( j in 0 until ar7.size - i - 1)
        {
            if (ar7[i] > ar7[i + 1]) {
                val temp = ar7[i]
                ar7[i] = ar7[i + 1]
                ar7[i + 1] = temp
            }
        }
    }
    println(ar7.joinToString(","))
}