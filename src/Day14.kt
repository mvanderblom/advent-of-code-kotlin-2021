import kotlin.streams.toList

data class Polymerization(val template: String, val instructions: Map<String, String>) {
    companion object {
        fun of(input: List<String>): Polymerization {
            val template = input[0]
            val instructions = input.subList(2, input.size)
                .map { it.split(" -> ") }
                .associate { (pattern, insertionChar) -> pattern to insertionChar }
            return Polymerization(template, instructions)
        }
    }

    fun execute(n: Int): String {
        var input = template
//        println("Starting with $input")

        val polySingles = template.toSet().associateBy({ it }) { template.count { c -> c == it }.toLong() } as MutableMap
        val polyPairs = template.windowed(2).associateBy(
            keySelector = { it },
            valueTransform = { v -> template.windowed(2).count { c -> c == v }.toLong() }) as MutableMap

        for (step in 1..3) {
            // for each polymer chain pair..
            polyPairs.filter { it.value > 1 }.forEach { (pair, pairCount) ->
                instructions[pair]?.also { charToInsert ->
                    // add newly formed pairs from insertion
                    polyPairs.merge(pair[0] + charToInsert, pairCount) { a, b -> a + b }
                    polyPairs.merge(charToInsert + pair[1], pairCount) { a, b -> a + b }

                    // subtract pair that was split up
                    polyPairs.merge(pair, pairCount * -1) { a, b -> a + b }

                    // add inserted letter count to singles map
                    polySingles.merge(charToInsert[0], pairCount) { a, b -> a + b }
                }
            }

        }

//        repeat(n) {
//            input = input
//                .windowed(2)
//                .map { pair ->
//                    if(pair in instructions)
//                        pair[0] + instructions[pair]!!
//                    else
//                        pair
//                }
//                .joinToString("") + template.last()
//            println("After $it length: ${input.length}")
////            println("After ${it} steps: $input")
//        }
        return "input"
    }
}

fun main() {
    val dayName = "Day14"

    fun score(polymer: String): Int {
        val charCounts = polymer.toList()
            .groupBy { it }
            .entries.map { (char, list) -> char to list.size }
            .toMap()

        val max = charCounts.entries.maxOf { (_, count) -> count }
        val min = charCounts.entries.minOf { (_, count) -> count }

        return max - min
    }

    fun part1(input: List<String>): Int {
        val polymerization = Polymerization.of(input)
        val polymer = polymerization.execute(10)
        return score(polymer)
    }

    fun part2(input: List<String>): Int {
        val polymerization = Polymerization.of(input)
        val polymer = polymerization.execute(40)
        return score(polymer)
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

//    val testOutputPart1 = part1(testInput)
//    testOutputPart1 isEqualTo 1588
//
//    val outputPart1 = part1(input)
//    outputPart1 isEqualTo 2375

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 2188189693529

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 1
}
