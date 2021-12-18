fun main() {
    val dayName = "Day09"

    fun List<Int>.product(): Int {
        if(isEmpty())
            return 0
        var product = this[0]
        for (element in this.subList(1, this.size)) {
            product *= element
        }
        return product
    }

    fun getNeighbours(rowNum: Int, colNum: Int, rows: List<List<Int>>) =
        listOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0).mapNotNull { (addRow, addCol) ->
            val neighbourRow = rowNum + addRow
            val neighbourCol = colNum + addCol

            if (neighbourRow >= 0 && neighbourRow < rows.size && neighbourCol >= 0 && neighbourCol < rows[0].size)
                neighbourRow to neighbourCol
            else
                null
        }

    fun getLowPoints(rows: List<List<Int>>): List<Pair<Int, Int>> = rows
        .mapIndexed { rowNum, values ->
            values.mapIndexedNotNull { colNum, _ ->
                val neighbours = getNeighbours(rowNum, colNum, rows)
                val allNeighboursAreHigher = neighbours
                    .map { (neighbourRow, neighbourCol) -> rows[neighbourRow][neighbourCol] }
                    .all { neighbour -> neighbour > rows[rowNum][colNum] }

                if(allNeighboursAreHigher) {
                    rowNum to colNum
                }
                else {
                    null
                }
            }
        }
        .flatten()

    fun part1(input: List<String>): Int {
        val rows = input.map { it.toList().map { it.toString().toInt() } }
        val lowPoints = getLowPoints(rows)
        return lowPoints
            .sumOf { (rowNum, colNum) -> rows[rowNum][colNum] + 1 }
    }

    fun part2(input: List<String>): Int {
        val rows = input.map { it.toList().map { it.toString().toInt() } }
        val lowPoints = getLowPoints(rows)

        return lowPoints.map { (rowNum, colNum) ->
            var basinSize = 0

            var up = rowNum - 1
            while (up >= 0 && rows[up][colNum] != 9){
                basinSize += rows[up][colNum]
                up--
            }

            // Look down
            var down = rowNum + 1
            while (down < rows.size && rows[down][colNum] != 9){
                basinSize += rows[down][colNum]
                down++
            }

            // Look left
            var left = colNum - 1
            while (left >= 0 && rows[rowNum][left] != 9){
                basinSize += rows[rowNum][left]
                left--
            }

            // Look right
            var right = rowNum + 1
            while (right < rows.size && rows[rowNum][right] != 9){
                basinSize += rows[rowNum][right]
                right++
            }

            basinSize
        }
        .sortedDescending()
        .subList(0, 3)
        .product()

    }

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
