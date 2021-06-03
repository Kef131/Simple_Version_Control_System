package svcs

import kotlin.math.pow

fun main() {
    fun sum(a: Int, b: Int): Int = a + b
    val sumResult = sum(1,2) //result = a Integer of type Int
    println("SumResult value: $sumResult")
    val sumObject: (Int, Int) -> Int = ::sum  //result = a function of type (Int, Int) -> Int
//    println("Print sumObject: $sumObject")
    println("Print sumObject with params: ${sumObject(1,2)}")
    println("***************************************************************************************")
    println("***************************************************************************************")
    fun getRealGrade(x: Double) = x
    fun getGradeWithPenalty(x: Double) = x - 1

    // function getScoringFunction takes a boolean input (isCheater) and gives a return value of type (double) -> Double.
    // a type of this format has to be a function.
    fun getScoringFunction(isCheater: Boolean): (Double) -> Double {
        if (isCheater) {
            return ::getGradeWithPenalty
        }

        return ::getRealGrade
    }
    val wantedFunctionCheater = getScoringFunction(true)
    val wantedFunctionNotCheater = getScoringFunction(false)
//    println("Type of wantedFunction: $wantedFunction")
//    println("Type of wantedFunctionNotCheater: $wantedFunctionNotCheater")
    println("wantedFunctionCheater(10): ${wantedFunctionCheater(10.0)}")
    println("wantedFunctionNotCheater(10): ${wantedFunctionNotCheater(10.0)}")
    println("***************************************************************************************")
    println("***************************************************************************************")
    fun applyAndSum(a: Int, b: Int, transformation: (Int) -> Int): Int {
        return transformation(a) + transformation(b)
    }
    fun same(x: Int) = x
    fun square(x: Int) = x * x
    fun triple(x: Int) = 3 * x
    fun cube(x: Int) = x.toDouble().pow(3).toInt()
    println(applyAndSum(1, 2, ::same))  // returns 3 = 1 + 2
    println(applyAndSum(1, 2, ::square))  // returns 5 = 1 * 1 + 2 * 2
    println(applyAndSum(1, 2, ::triple))  // returns 9 = 3 * 1 + 3 * 2
    println(applyAndSum(1, 2, ::cube))  // returns 9 = 3 * 1 + 3 * 2
    println("***************************************************************************************")
    println("***************************************************************************************")
    fun isNotDot(c: Char): Boolean = c != '.'
    val originalText = "I don't know... what to say..."
    val textWithoutDots = originalText.filter(::isNotDot)
    println(originalText)
    println(textWithoutDots)
}