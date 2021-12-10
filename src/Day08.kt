fun main() {
    val dayName = "Day08"

    val letters = ('a'..'g').toList()
    val numbers = listOf(
        listOf(0,1,2,4,5,6), // 0
        listOf(2,5),
        listOf(0,2,3,4,6),
        listOf(0,2,3,5,6), // 3
        listOf(1,2,3,5),
        listOf(0,1,3,5,6),
        listOf(0,1,3,4,5,6), // 6
        listOf(0,2,5),
        listOf(0,1,2,3,4,5,6),
        listOf(0,1,2,3,5,6) // 9
    ).map { segments -> segments.map { letters[it] }.joinToString("") }


    println(letters)

    fun String.sorted(): String {
        return this.toSortedSet().joinToString("")
    }

    fun decodePatterns(patterns: String) {
        val decodePatterns = patterns.split(" ")
            .map { it.sorted() }
            .associate {
                when (it.length) {
                    2 -> it to 1
                    3 -> it to 7
                    4 -> it to 4
                    7 -> it to 8
                    else -> it to -1
                }
            }
    }


    fun part1(input: List<String>): Int {
        val x = input
            .map { it.split(" | ") }
            .map { (patterns, value) ->
                val decodedPatterns = decodePatterns(patterns)
                val values = value.split(" ")
                    .map { it.sorted() }
            }
        return input.size
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 26)

    val outputPart1 = part1(input)
    println(outputPart1)
//    check(outputPart1 == 1298)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
//    check(testOutputPart2 == 5)

    val outputPart2 = part2(input)
    println(outputPart2)
//    check(outputPart2 == 1248)
}
