fun main() {
    val dayName = "Day11"

    fun getNeighbours(point: Pair<Int, Int>, input: List<List<Int>>) =
        listOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0, -1 to -1, 1 to 1, -1 to 1, 1 to -1)
            .mapNotNull { (addRow, addCol) ->
                val (rowIndex, colIndex) = point
                val neighbourRow = rowIndex + addRow
                val neighbourCol = colIndex + addCol

                if (neighbourRow >= 0 && neighbourRow < input.size && neighbourCol >= 0 && neighbourCol < input[0].size)
                    neighbourRow to neighbourCol
                else
                    null
            }

    fun updateNeighbours(point: Pair<Int, Int>, input: List<MutableList<Int>>) {
        getNeighbours(point, input)
            .forEach { (rowIndex, colIndex) ->
                input[rowIndex][colIndex]++
            }
    }

    fun findFlashes(input: List<List<Int>>) = input
        .mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, value ->
                if (value % 10 == 0)
                    rowIndex to colIndex
                else
                    null
            }.filterNotNull()
        }.flatten()

    fun executeStep(input: List<MutableList<Int>>): Int {
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, _ ->
                input[rowIndex][colIndex]++
            }
        }

        do {
            var flashes = findFlashes(input)
            flashes.forEach { updateNeighbours(it, input) }
        }
        while (flashes.isNotEmpty())

        var flashCount = 0
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                if (value > 10) {
                    flashCount++
                    input[rowIndex][colIndex] = 0
                }
            }
        }

        return flashCount
    }

    fun part1(input: List<String>, steps: Int = 100): Int {
        val rows = input.map { row ->
            row.toList()
                .map { it.toString().toInt() }
                .toMutableList()
        }
        println("Before any steps:")
        println(rows.joinToString(System.lineSeparator()) { it.joinToString("") })

        return (1..steps).sumOf { i ->
            println("After step $i: ")
            val flashes = executeStep(rows)
            println(rows.joinToString(System.lineSeparator()) { it.joinToString("") })
            flashes
        }
    }

    fun part2(input: List<String>): Int = input.size

    val testInputSimple = readInput("${dayName}_test_simple")
    val x = part1(testInputSimple, 2)
    x isEqualTo 9

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 1656

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 1

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 1
}
