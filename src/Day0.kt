fun main() {
    val dayName = "Day0"

    fun part1(input: List<String>): Int = 1

    fun part2(input: List<String>): Int = 1

    val testInput = readInput("${dayName}_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
//    check(testOutputPart1 == 7)

    val input = readInput(dayName)
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
