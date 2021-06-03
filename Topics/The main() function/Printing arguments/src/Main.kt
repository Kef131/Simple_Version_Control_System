fun main(args: Array<String>) {
    if (args.size == 3) {
        for ((index, arg) in args.withIndex()) {
            println("Argument ${index + 1}: $arg. It has ${arg.length} characters")
        }
    } else println("Invalid number of program arguments")
}
