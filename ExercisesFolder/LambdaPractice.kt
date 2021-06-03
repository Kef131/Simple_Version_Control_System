package svcs

fun main() {
    val mul1 = fun(a: Int, b: Int): Int { return a * b}
    val mul2 = {a: Int, b: Int -> a * b}
    println(mul1(2,2))
    println(mul2(2,2))
    println("***************************************************************************************")
    println("***************************************************************************************")
    val originalText = "I don't know... what to say..."
    val textWithoutDots = originalText.filter({ c: Char -> c != '.'})
    val textWithoutDotsOutLambda = originalText.filter { c: Char -> c != '.' }
    val textWithoutDotsWithIt = originalText.filter { it != '.' }
    println(originalText)
    println(textWithoutDots)
    println(textWithoutDotsOutLambda)
    println(textWithoutDotsWithIt)
    println("***************************************************************************************")
    println("***************************************************************************************")
    val textWithSmallNumbers = "I have 0 small numbers, maybe 1 or 2 but never 6"
    val textWithoutSmallNumbers = textWithSmallNumbers.filter {
        val isNotDigit = !it.isDigit()
        val stringRepresentation = it.toString()

        isNotDigit || stringRepresentation.toInt() >= 5
    }
    val textWithoutSmallNumbersReturns = textWithSmallNumbers.filter {
        if (!it.isDigit()) {
            return@filter true
        }

        it.toString().toInt() >= 5
    }
    println(textWithSmallNumbers)
    println(textWithoutSmallNumbers)
    println(textWithoutSmallNumbersReturns)
    println("***************************************************************************************")
    println("***************************************************************************************")
    var count = 0

    val changeAndPrint = {
        ++count
        println(count)
    }

    println(count)    // 0
    changeAndPrint()  // 1
    count += 10
    changeAndPrint()  // 12
    println(count)    // 12
    println("***************************************************************************************")
    println("***************************************************************************************")
    //The placeArgument transforms the f function that takes two arguments to a function that takes a single argument.
    // We achieve it by creating a lambda that takes only one argument and calls the given function with
    // this argument and the given value. Here the lambda captures the value and the f.
    fun placeArgument(value: Int, f: (Int, Int) -> Int): (Int) -> Int {
        return { i -> f(value, i) }
    }
    fun sum(a: Int, b: Int): Int = a + b
    fun mul2(a: Int, b: Int): Int = a * b
    val increment = placeArgument(1, ::sum)
    val triple = placeArgument(3, ::mul2)
    println(increment(4))   // 5
    println(increment(40))  // 41
    println(triple(4))      // 12
    println(triple(40))     // 120
}