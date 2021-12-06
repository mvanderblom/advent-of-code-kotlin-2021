fun main() {

    fun part1(input: List<String>): Int = input
        .map { it.toInt() }
        .windowed(2)
        .count { (prev, cur) -> cur > prev}

    fun part2(input: List<String>): Int = input
            .map { it.toInt() }
            .windowed(3)
            .map { it.sum() }
            .windowed(2)
            .count { (prev, cur) -> cur > prev}

    val testInput = readInput("Day01_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 7)

    val input = readInput("Day01")
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 1298)

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 1248)
}
