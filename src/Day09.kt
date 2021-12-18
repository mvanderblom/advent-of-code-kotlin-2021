fun main() {
    val dayName = "Day09"

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

    fun getBasinPoints(rowNum: Int, colNum: Int, rows: List<List<Int>>, points: MutableSet<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val currentPoint = rowNum to colNum
        if (points.contains(currentPoint) || rows[rowNum][colNum] == 9) {
            return points
        }

        points.add(currentPoint)

        getNeighbours(rowNum, colNum, rows).forEach { (nRow, nCol) ->
            getBasinPoints(nRow, nCol, rows, points)
        }

        return points
    }

    fun List<Int>.product(): Int {
        if(isEmpty())
            return 0
        var product = this[0]
        for (element in this.subList(1, this.size)) {
            product *= element
        }
        return product
    }

    fun part1(input: List<String>): Int {
        val rows = input.map { it.toList().map { it.toString().toInt() } }
        val lowPoints = getLowPoints(rows)
        return lowPoints
            .sumOf { (rowNum, colNum) -> rows[rowNum][colNum] + 1 }
    }

    fun part2(input: List<String>): Int {
        val rows = input.map { it.toList().map { it.toString().toInt() } }

        return getLowPoints(rows)
            .map { (row, col) ->
                getBasinPoints(row, col, rows, mutableSetOf()).size
            }
            .sortedDescending()
            .subList(0,3)
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
    outputPart2 isEqualTo 1038240
}
