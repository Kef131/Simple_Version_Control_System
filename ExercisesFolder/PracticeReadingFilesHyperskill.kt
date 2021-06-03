package svcs

import java.io.File

fun main() {
    //Getting working path
    println(java.io.File( "." ).getCanonicalPath())
    ////////////////LONGEST WORD
    val file = File("Simple Version Control System/task/src/svcs/words_sequence.txt")
    var maxLength = 0
    file.forEachLine { if (it.length > maxLength) maxLength = it.length }
    println(maxLength)

    //////////////SIZE OF TEXT
    val file2 = File("Simple Version Control System/task/src/svcs/text.txt")
    var wordsCounter = 0
    file2.forEachLine { wordsCounter += it.split(" ").count() }
    println(wordsCounter)

    //////////////COUNT NUMBERS
    val file3 = File("Simple Version Control System/task/src/svcs/words_with_numbers.txt")
    var numbersCounter = 0
    val textLines = file3.readLines()
    for (line in textLines) if (line.contains("\\d".toRegex())) numbersCounter++
    println(numbersCounter)
}