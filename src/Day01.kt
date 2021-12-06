fun main() {

    fun part1(input: List<String>): Int = input.subList(1, input.size)
        .zip(input)
        .map { (curr, prev) -> if (curr.toInt() > prev.toInt()) "increased" else "decreased" }
        .count { it.equals("increased") }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day01_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 7)

    val input = readInput("Day01")
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 1298)

    println(part2(input))
}
