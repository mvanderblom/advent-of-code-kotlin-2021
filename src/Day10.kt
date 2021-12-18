fun main() {
    val dayName = "Day10"

    val chunks = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    fun findCorruptedChar(line: String): Char? {
        val openChars = mutableListOf<Char>()
        line.toList().forEach {
            if(it in chunks.keys)
                openChars.add(it)
            else {
                if( openChars.isNotEmpty() && chunks[openChars.last()] == it )
                    openChars.removeLast()
                else
                    return it
            }
        }
        return null
    }

    val scoreTable = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    fun part1(input: List<String>): Int {
        return input
            .mapNotNull { findCorruptedChar(it) }
            .sumOf { scoreTable[it]!! }
    }


    fun findCompletionString(line: String): List<Char> {
        val openChars = mutableListOf<Char>()
        line.toList().forEach {
            if(it in chunks.keys)
                openChars.add(it)
            else if( openChars.isNotEmpty() && chunks[openChars.last()] == it )
                openChars.removeLast()
            }
        return openChars
            .reversed()
            .map { chunks[it]!! }
    }

    fun <T> List<T>.middle(): T? {
        println("--------------------")
        println(this)
        println(size)
        println((size-1)/2+1)
        println("--------------------")
        if(size % 2 == 0)
            return null
        return this[(size-1)/2]
    }

    val scoreTable2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    fun part2(input: List<String>): Long {
        return input
            .filter { findCorruptedChar(it) == null }
            .map { findCompletionString(it) }
            .map { it.fold(0L) { sum, char -> (sum * 5) + scoreTable2[char]!! } }
            .sorted()
            .middle()!!
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 26397

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 392097

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 288957L

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 4263222782L
}
