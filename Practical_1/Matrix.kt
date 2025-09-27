class Matrix(val data: Array<IntArray>) {
    val rows = data.size
    val cols = data[0].size

    init {
        require(data.all { it.size == cols }) { "All rows must have the same number of columns" }
        println("Matrix initialized with $rows rows and $cols columns")
    }

    // Addition
    operator fun plus(other: Matrix): Matrix {
        require(rows == other.rows && cols == other.cols) { "Matrix dimensions must match for addition" }
        val result = Array(rows) { i ->
            IntArray(cols) { j -> data[i][j] + other.data[i][j] }
        }
        return Matrix(result)
    }

    // Subtraction
    operator fun minus(other: Matrix): Matrix {
        require(rows == other.rows && cols == other.cols) { "Matrix dimensions must match for subtraction" }
        val result = Array(rows) { i ->
            IntArray(cols) { j -> data[i][j] - other.data[i][j] }
        }
        return Matrix(result)
    }

    // Multiplication
    operator fun times(other: Matrix): Matrix {
        require(cols == other.rows) { "Matrix A's columns must match Matrix B's rows for multiplication" }
        val result = Array(rows) { i ->
            IntArray(other.cols) { j ->
                (0 until cols).sumOf { k -> data[i][k] * other.data[k][j] }
            }
        }
        return Matrix(result)
    }

    override fun toString(): String
    {
        return data.joinToString("\n") { row -> row.joinToString(" ") }
    }
}

fun main() {
    val matrixA = Matrix(arrayOf( intArrayOf(3, -2, 5), intArrayOf(3, 0, 4)))

    val matrixB = Matrix(arrayOf( intArrayOf(2, 3, -1), intArrayOf(1, 2, 3)))

    val matrixC = Matrix(arrayOf( intArrayOf(2, 3), intArrayOf(1, 2), intArrayOf(3, 4)))

    println("************ Addition ***************")
    println("Matrix A:\n$matrixA")
    println("Matrix B:\n$matrixB")
    println("Matrix C:\n$matrixC")
    println("A + B:\n${matrixA + matrixB}")

    println("\n************ Subtraction ***************")
    println("A - B:\n${matrixA - matrixB}")

    println("\n************ Multiplication ***************")
    println("A * C:\n${matrixA * matrixC}")
}