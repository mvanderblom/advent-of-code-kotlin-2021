class Octopus(
    var energy: Int,
    val grid: Grid
) {
    private var hasFlashed = false
    lateinit var neighbours: List<Octopus>

    fun executeStep() {
        if(!hasFlashed) {
            energy++
            if (energy == 10) {
                flash()
            }
        }
    }

    private fun flash() {
        hasFlashed = true
        energy = 0
        grid.countFlash()
        neighbours.forEach { it.executeStep() }
    }

    fun reset() {
        hasFlashed = false
    }
}

class Grid {
    var flashCount = 0
    private val gridMap: MutableMap<Pair<Int, Int>, Octopus> = mutableMapOf()
    private val height: Int
    private val width: Int

    constructor(input: List<String>) {
        height = input.size
        width = input[0].length

        input.forEachIndexed { rowIndex, row ->
            row
                .toList()
                .forEachIndexed { colIndex, c ->
                    val energy = c.toString().toInt()
                    val octopus = Octopus(energy, this)
                    gridMap[rowIndex to colIndex] = octopus
                }
        }

        gridMap.entries.forEach { (coord, octo)->
            octo.neighbours = getNeighbours(coord)
        }
    }

    private fun getNeighbours(coord: Pair<Int, Int>): List<Octopus> {
        return listOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0, -1 to -1, 1 to 1, -1 to 1, 1 to -1)
            .mapNotNull { (addRow, addCol) ->
                val (rowIndex, colIndex) = coord
                val neighbourRow = rowIndex + addRow
                val neighbourCol = colIndex + addCol

                if (neighbourRow in 0 until height
                    && neighbourCol in 0 until  width)
                    gridMap[neighbourRow to neighbourCol]
                else
                    null
            }
    }

    fun executeStep(): Int {
        gridMap.values.forEach {
            it.reset()
        }
        gridMap.values.forEach {
            it.executeStep()
        }
        return flashCount
    }

    fun print() {
        for (row in 0 until height){
            for (col in 0 until width) {
                val octo = gridMap[row to col]!!
                print(octo.energy.toString().padStart(2))
            }
            println()
        }
    }

    fun countFlash() {
        flashCount++
    }

}

fun main() {
    val dayName = "Day11"

    fun part1(input: List<String>, steps: Int = 100): Int {
        val grid = Grid(input)
        println("Before any steps:")
        grid.print()

        (1..steps).forEach{ i ->
            println("Executing step $i: ")
            grid.executeStep()
            println("After step $i: ")
            grid.print()
        }
        return grid.flashCount
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
    outputPart1 isEqualTo 1741

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 1

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 1
}
