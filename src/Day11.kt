class Octopus(var energy: Int, private val grid: Grid) {
    private var hasFlashed = false
    lateinit var neighbours: List<Octopus>

    fun executeStep(step: Int) {
        if(!hasFlashed) {
            energy++
            if (energy == 10) {
                flash(step)
            }
        }
    }

    private fun flash(step: Int) {
        hasFlashed = true
        energy = 0
        grid.countFlash(step)
        neighbours.forEach { it.executeStep(step) }
    }

    fun reset() {
        hasFlashed = false
    }
}

class Grid(input: List<String>) {
    private val height: Int
    private val width: Int
    private val gridMap: MutableMap<Pair<Int, Int>, Octopus> = mutableMapOf()

    val flashCounts = mutableMapOf<Int, Int>()
    val flashCount get() = flashCounts.values.sum()
    val size get() = height * width

    init {
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
        println("Constructed grid:")
        println(this)
    }

    fun executeStep(step: Int) {
        println("Executing step $step: ")
        gridMap.values.forEach {
            it.reset()
        }
        gridMap.values.forEach {
            it.executeStep(step)
        }
        println("After step $step: ")
        println(this)
    }

    fun countFlash(step: Int) {
        flashCounts[step] = flashCount(step) + 1
    }

    fun flashCount(step: Int) = flashCounts.getOrDefault(step, 0)

    override fun toString(): String = (0 until height)
        .joinToString(System.lineSeparator()) { row ->
            (0 until width).joinToString { col ->
                gridMap[row to col]!!.energy.toString().padStart(2)
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

}

fun main() {
    val dayName = "Day11"

    fun part1(input: List<String>, steps: Int = 100): Int {
        val grid = Grid(input)
        (1..steps).forEach{ step ->
            grid.executeStep(step)
        }
        return grid.flashCount
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)
        var step = 1
        do {
            grid.executeStep(step)
            println("FlashCount for step ${grid.flashCount(step)}")
        } while (grid.flashCount(step++) < grid.size)
        return step-1
    }

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
    testOutputPart2 isEqualTo 195

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 440
}
