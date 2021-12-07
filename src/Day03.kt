fun main() {

    fun Boolean.toInt(): Int = if(this) 1 else 0

    fun String.parseAsBinary(): Int = Integer.parseInt(this,2)

    fun List<List<String>>.transpose(): Array<Array<String?>> {
        val rows = this.size
        val columns = this[0].size

        val transposed = Array(columns) { arrayOfNulls<String>(rows) }
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                transposed[j][i] = this[i][j]
            }
        }

        return transposed
    }

    fun Array<String?>.count(needle: String): Int = this.count{ hay -> hay == needle}

    fun Array<String?>.mostCommonBit(): Int = (this.count("1") > this.size / 2).toInt()

    fun Array<String?>.leastCommonBit(): Int = (this.count("1") < this.size / 2).toInt()

    fun part1(input: List<String>): Int {
        val transposedInput = input
            .map { it.chunked(1) }
            .transpose()


        val gamma = transposedInput
            .map { it.mostCommonBit() }
            .joinToString("")
            .parseAsBinary()

        val epsilon = transposedInput
            .map { it.leastCommonBit() }
            .joinToString("")
            .parseAsBinary()

        return gamma * epsilon
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

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
    check(testOutputPart2 == 230)

//    val outputPart2 = part2(input)
//    println(outputPart2)
//    check(outputPart2 == 1248)
}
