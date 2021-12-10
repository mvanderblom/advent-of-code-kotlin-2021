import kotlin.math.abs

fun main() {
    val dayName = "Day07"

    fun part1(input: List<String>): Int {
        val crabs = input[0].split(",").map { it.toInt() }
        val counts = (crabs.minOrNull()!!..crabs.maxOrNull()!!).map { 0 }.toMutableList()

        crabs.forEach { crab ->
            counts.forEachIndexed { index, count ->
                counts[index] += abs(index - crab)
            }
        }
        println(counts)
        return counts.minOrNull()!!
    }

    fun part2(input: List<String>): Int {
        val crabs = input[0].split(",").map { it.toInt() }
        val crabRange = crabs.minOrNull()!!..crabs.maxOrNull()!!
        val counts = crabRange.map { 0 }.toMutableList()
        val costs = crabRange.toList()

        crabs.forEach { crab ->
            counts.forEachIndexed { index, count ->
                counts[index] += costs.subList(0, abs(index - crab) + 1).sum()
            }
        }
        println(counts)
        return counts.minOrNull()!!
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 37)

    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 357353)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
    check(testOutputPart2 == 168)

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 104822130)
}
