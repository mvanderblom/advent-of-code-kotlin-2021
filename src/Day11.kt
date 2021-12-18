class Octopus(
    var energy: Int,
    val grid: Grid
) {
    private var hasFlashed = false
    lateinit var neighbours: List<Octopus>

    fun executeStep() {
        energy++
        if(energy == 10) {
            flash()
        }
    }

    fun flash() {
        if(!hasFlashed) {
            energy = 0
            grid.countFlash()
            neighbours.forEach { it.executeStep() }
        }
    }

    fun reset() {
        hasFlashed = false
    }
}

class Grid {
    var flashCount = 0
    private val gridMap: MutableMap<Pair<Int, Int>, Octopus> = mutableMapOf()
    private val grid: List<List<Octopus>>

    constructor(input: List<String>) {
        this.grid = input.mapIndexed { rowIndex, row ->
            row
                .toList()
                .mapIndexed { colIndex, c ->
                    val energy = c.toString().toInt()
                    val octopus = Octopus( energy, this)
                    gridMap.put(rowIndex to colIndex, octopus)
                    octopus
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

                if (neighbourRow >= 0 && neighbourRow < grid.size && neighbourCol >= 0 && neighbourCol < grid[0].size)
                    gridMap[neighbourRow to neighbourCol]
                else
                    null
            }
    }

    fun executeStep(): Int {
        gridMap.values.forEach {
            it.reset()
            it.executeStep()
        }
        return flashCount
    }

    fun print() {
        println(grid.joinToString(System.lineSeparator()) { it.map { it.energy.toString().padStart(2) }.joinToString()})
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

    val testOutputPart1 = part1(testInput, 2)
    testOutputPart1 isEqualTo 1656

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 1

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 1
}
