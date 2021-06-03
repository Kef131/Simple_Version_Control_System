val lambda: (Long, Long) -> Long = { leftBorder, rightBorder ->
    var prod = 1L
    for (i in leftBorder..rightBorder)
        prod *= i
    prod
}