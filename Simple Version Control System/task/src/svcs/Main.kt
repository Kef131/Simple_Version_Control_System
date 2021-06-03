package svcs

import java.io.File
import java.lang.Exception
import java.security.MessageDigest

val mainPath: String = "${File(".").canonicalPath}\\vcs"
val commitPath: String = "${File(".").canonicalPath}\\vcs\\commits"

fun createVCSFolder() {
    val dir = File("vcs")
    if (!dir.exists()) dir.mkdir()
}

fun helpPage(): String {
    return """These are SVCS commands:
config     Get and set a username.
add        Add a file to the index.
log        Show commit logs.
commit     Save changes.
checkout   Restore a file."""
}

fun openFile(filename: String, defaultNonFile: String, configFile: Boolean = false): String {
    val file = File("$mainPath\\$filename")
    return if (!file.exists() || file.length() == 0L) {
        defaultNonFile
    } else {
        if (!configFile) file.readText() else "The username is ${file.readText()}."
    }
}

fun configCommand(args: Array<String>) {
    val configFile = "config.txt"
    if (args.size == 1) {
        println(openFile(configFile, "Please, tell me who you are.",true))
    } else {
        writeConfig(configFile, args[1])
    }
}

fun writeConfig(filename: String, userNameText: String) {
    val file = File("$mainPath\\$filename")
    try {
        file.writeText(userNameText)
        println("The username is $userNameText.")
    } catch (e: Exception) {
        println(e.toString())
    }
}

fun addCommand(args: Array<String>) {
    val indexFile = "index.txt"
    if (args.size == 1) {
        println(openFile(indexFile, "Add a file to the index."))
    } else {
        writeAdd(indexFile, args[1])
    }
}

val writeAdd = { indexFile: String, filename: String ->
    val addFile = File("$mainPath\\$indexFile")
    val filenamePath = File("$filename")
    if (!addFile.exists() && filenamePath.exists()) {
        val text = "Tracked files:\n$filename\n"
        addFile.writeText(text)
        println("The File '$filename' is tracked.")
    } else if (filenamePath.exists()) {
        addFile.appendText("$filename\n")
        println("The File '$filename' is tracked.")
    } else {
        println("Can't found '$filename'.")
    }
}


fun commitsExists(commitHash: String): Boolean {
    val logFile = File("$mainPath\\log.txt")
    return if (logFile.exists()) {
        logFile.readText().contains(commitHash)
    } else false
}

fun commitCommand(args: Array<String>) {
    if (args.size == 1) {
        println("Message was not passed.")
    } else {
        // Get files to check if can commit
        val filenamesToCommit = File("$mainPath\\index.txt").readLines()
        // Get the files to commit so we can create hash value, then create the folder and save the files there there
        if (filenamesToCommit.isNotEmpty()) {
            // Sum the bytes of all the files and then hash it
            var finalByteArrayOfFiles = ByteArray(0)
            for (i in 1 until filenamesToCommit.size)
                finalByteArrayOfFiles += File("${filenamesToCommit[i]}").readBytes()
            val commitHash = byteArrayToSHA256(finalByteArrayOfFiles)

            // Check if hash value was already created
            if (commitsExists(commitHash)) {
                println("Nothing to commit.\n")
                return
            }

            // Once get hash, create the directory named as hash value
            var dirCommit = File(commitPath)
            if (!dirCommit.exists()) dirCommit.mkdir()
            var dirHashCommit = File("$commitPath\\$commitHash")
            if (!dirHashCommit.exists()) dirHashCommit.mkdir()

            // Get a copy of those files tracked inside the hash folder
            for (i in 1 until filenamesToCommit.size)
                File("${filenamesToCommit[i]}").copyTo(File("$dirHashCommit\\${filenamesToCommit[i]}"), true)

            // Once done, log the info in log.txt
            var author =
                if (File("$mainPath\\config.txt").exists()) File(mainPath.plus("\\config.txt")).readText()
                else "No Author"

            val logFile = File("$mainPath\\log.txt")
            logFile.appendText("commit $commitHash\nAuthor: $author\n${args[1]}\n\n")
            println("Changes are committed.")
        }
    }
}

// Extension function of ByteArray - Not in current pre requisites
fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

fun byteArrayToSHA256(bytes: ByteArray): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(bytes)
    return bytes.toHex()
}

var logCommand = {
    val logFile = File("$mainPath\\log.txt")
    if (!logFile.exists() || logFile.length() == 0L) {
        println("No commits yet.")
    } else {
        val logContentLines = logFile.readLines()
        var index = logContentLines.size - 1
        repeat(logContentLines.size / 4) {
            for (i in index - 3..index) {
                if (i != 3) println(logContentLines[i])
            }
            index -= 4
        }
    }
}

fun checkoutCommand(args: Array<String>) {
    if (args.size == 1) {
        println("Commit id was not passed.")
    } else {
        if (commitsExists(args[1])) {
            val filenamesToCheckout = File("$mainPath\\index.txt").readLines()
            if (filenamesToCheckout.isNotEmpty())
                for (i in 1 until filenamesToCheckout.size)
                    File("$commitPath\\${args[1]}\\${filenamesToCheckout[i]}").copyTo(File("${filenamesToCheckout[i]}"),true)
            println("Switched to commit ${args[1]}.")
        } else {
            println("Commit does not exist.")
        }
    }
}

fun main(args: Array<String>) {
    createVCSFolder()
    when (args.firstOrNull()) {
        "config" -> configCommand(args)
        "--help" -> println(helpPage())
        "add" -> addCommand(args)
        "log" -> logCommand()
        "commit" -> commitCommand(args)
        "checkout" -> checkoutCommand(args)
        "" -> println(helpPage())
        null -> println(helpPage())
        else -> println("\'${args[0]}\' is not a SVCS command.")
    }
}