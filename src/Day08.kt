fun main() {
    val dayName = "Day08"

    fun decodePatterns(patternString: String): Array<Set<Char>?> {
        val patterns = arrayOfNulls<Set<Char>>(10)

        val patternStrings: List<Set<Char>> = patternString.split(" ").map { it.toSortedSet() }
        patternStrings
            .forEach {
                when (it.size) {
                    2 -> patterns[1] = it
                    3 -> patterns[7] = it
                    4 -> patterns[4] = it
                    7 -> patterns[8] = it
                }
            }

        patternStrings
            .filter { it.size == 6 }
            .forEach {
                when {
                    (patterns[4]!! - it).isEmpty() -> patterns[9] = it
                    (patterns[1]!! - it).isEmpty() -> patterns[0] = it
                    else -> patterns[6] = it
                }
            }

        patternStrings
            .filter { it.size == 5 }
            .forEach {
                when {
                    (patterns[7]!! - it).isEmpty() -> patterns[3] = it
                    (patterns[6]!! - it).size == 1 -> patterns[5] = it
                    else -> patterns[2] = it
                }
            }

        return patterns
    }


    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" | ") }
            .sumOf { (patternString, value) ->
                val allPatterns = decodePatterns(patternString)
                val simplePatterns = listOf(allPatterns[1], allPatterns[4], allPatterns[7], allPatterns[8])

                value
                    .split(" ")
                    .map { it.toSortedSet() }
                    .count { it in simplePatterns }
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" | ") }
            .sumOf { (patternString, value) ->
                val patterns = decodePatterns(patternString)
                value
                    .split(" ")
                    .map { it.toSortedSet() }
                    .map { patterns.indexOf(it) }
                    .joinToString("").toInt()
            }
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 26

    val outputPart1 = part1(input)
    outputPart1 isEqualTo  301

    val example = part2(listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))
    example isEqualTo 5353

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo  61229

    val outputPart2 = part2(input)
    outputPart2 isEqualTo  908067
}
