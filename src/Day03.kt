fun main() {

    fun Boolean.toInt(): Int = if(this) 1 else 0

    fun String.parseAsBinary(): Int = Integer.parseInt(this, 2)

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

    fun String.commonBit(most: Boolean): Int? {
        val zeroCount = this.count('0')
        val oneCount = this.count('1')

        if(zeroCount == oneCount)
            return null

        if(most)
            return (oneCount > zeroCount).toInt()

        return (zeroCount > oneCount).toInt()
    }

    fun String.mostCommonBit(): Char? = this.commonBit(true)?.digitToChar()

    fun String.leastCommonBit(): Char? = this.commonBit(false)?.digitToChar()

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

    fun findBy(input: List<String>, discriminate: (element: String) -> Char, index: Int = 0): Int {
        if (input.size == 1)
            return input[0].parseAsBinary()

        val charsAtTransposedIndex = input.transpose()[index]

        val discriminator = discriminate(charsAtTransposedIndex)

        return findBy(input.filter { it[index] == discriminator }, discriminate,index + 1)
    }

    fun part2(input: List<String>): Int {
        val o2 = findBy(input, { it.mostCommonBit() ?: '1' } )
        val co2 = findBy(input, { it.leastCommonBit() ?: '0' } )
        return o2 * co2
    }

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

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 6775520)
}
