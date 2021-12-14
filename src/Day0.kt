fun main() {
    val dayName = "Day0"

    fun part1(input: List<String>): Int = input.size

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 1

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 1

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 1
}
