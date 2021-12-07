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

    fun String.commonBit(most: Boolean): Int?
    {
        val zeroCount = this.count('1')
        val oneCount = this.count('1')

        if(zeroCount == oneCount)
            return null

        if(most)
            return (oneCount > zeroCount).toInt()
        else
            return return (zeroCount > oneCount).toInt()
    }

    fun String.mostCommonBit(): Int? = this.commonBit(true)

    fun String.leastCommonBit(): Int? = this.commonBit(false)

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

    fun findO2(input: List<String>, index: Int = 0): Int {
        if (input.size == 1)
            return input[0].parseAsBinary()

        var discriminator = input
            .transpose()[index]
            .mostCommonBit()

        if(discriminator == null)
            discriminator = 1

        return findO2(input.filter { it[index] == discriminator!!.digitToChar() }, index + 1)
    }

    fun findCO2(input: List<String>, index: Int = 0): Int {
        if (input.size == 1)
            return input[0].parseAsBinary()

        val charsAtTransposedIndex = input
            .transpose()[index]

        var discriminator = charsAtTransposedIndex
            .leastCommonBit()

        if(discriminator == null)
            discriminator = 1

        return findCO2(input.filter { it[index] == discriminator!!.digitToChar() }, index + 1)
    }

    fun part2(input: List<String>): Int {

        val o2 = findO2(input)
        val co2 = findCO2(input)

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

//    val outputPart2 = part2(input)
//    println(outputPart2)
//    check(outputPart2 == 1248)
}
