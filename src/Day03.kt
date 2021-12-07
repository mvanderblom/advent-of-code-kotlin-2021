fun main() {

    fun Boolean.toInt(): Int = if(this) 1 else 0

    fun String.parseAsBinary(): Int = Integer.parseInt(this,2)

    fun List<String>.transpose(): List<String> {
        val columns = this[0].length

        val transposed = (0 until columns).map { "" }.toMutableList()
        for (row in this) {
            for (column in 0 until columns) {
                transposed[column] += row[column].toString()
            }
        }

        return transposed
    }

    fun String.count(needle: Char): Int = this.count{ hay -> hay == needle}

    fun String.mostCommonBit(): Int = (this.count('1') > this.length / 2).toInt()

    fun String.leastCommonBit(): Int = (this.count('1') < this.length / 2).toInt()

    fun part1(input: List<String>): Int {
        val transposedInput = input
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
