fun main() {

    fun List<List<String>>.transpose(): Array<Array<String?>> {
        val rows = this.size
        val columns = this[0].size

        val transposed = Array(columns) { arrayOfNulls<String>(rows) }
        for (i in 0..rows - 1) {
            for (j in 0..columns - 1) {
                transposed[j][i] = this[i][j]
            }
        }

        return transposed
    }

    fun part1(input: List<String>): Int {
        val transposedInput = input
            .map { it.chunked(1) }
            .transpose()

        val size = transposedInput[0].size

        val gamma = transposedInput.map {
            if (it.count { bit -> bit == "1" } > size / 2) 1 else 0
        }.joinToString("")

        val epsilon = transposedInput.map {
            if (it.count { bit -> bit == "1" } < size / 2) 1 else 0
        }.joinToString("")

        return Integer.parseInt(gamma,2) * Integer.parseInt(epsilon,2)
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("Day03_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 198)

    val input = readInput("Day03")
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 2261546)

//    val testOutputPart2 = part2(testInput)
//    println(testOutputPart2)
//    check(testOutputPart2 == 5)
//
//    val outputPart2 = part2(input)
//    println(outputPart2)
//    check(outputPart2 == 1248)
}
