fun main()
{
    print("Enter the size of array:")
    val n: Int = readln().toInt()
    val ar=IntArray(n)
    var max:Int=0
    for( i in 0 until ar.size)
    {
        print("ar[$i]}=")
        ar[i]=readln().toInt()
        if(ar[i]>max)
        {
            max=ar[i]
        }
    }
    println("Maximum element=$max")
}