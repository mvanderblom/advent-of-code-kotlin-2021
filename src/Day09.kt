fun main() {
    val dayName = "Day09"

    fun part1(input: List<String>): Int {
        val rows = input.map { it.toList().map { it.toString().toInt() } }
        val lastRow = rows.size - 1
        val lastCol = rows[0].size - 1

        var lowPoints = 0
        rows
            .forEachIndexed { rowNum, values ->
                values.forEachIndexed { colNum, value ->
                    val up = if (rowNum > 0) rows[rowNum-1][colNum] else Int.MAX_VALUE
                    val down = if (rowNum < lastRow) rows[rowNum+1][colNum] else Int.MAX_VALUE
                    val left = if (colNum > 0) rows[rowNum][colNum-1] else Int.MAX_VALUE
                    val right = if (colNum < lastCol) rows[rowNum][colNum+1] else Int.MAX_VALUE

                    if ( value < up && value < down && value < left && value < right) {
                        lowPoints += 1 + value
                    }
                }
            }
        return lowPoints
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 15

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 564

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 1134

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 1
}
