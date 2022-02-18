data class Fold (val axis: String, val num: Int) {

    companion object {
        fun of(input: List<String>): List<Fold> = input
            .filter { it.startsWith("fold") }
            .map { foldInstruction ->
                val (axis,num) = foldInstruction.substring(11).split("=")
                Fold(axis, num.toInt())
            }
    }

}

class Paper(
    val grid: Array<Array<String>>
) {

    companion object {
        fun of(input: List<String>): Paper {
            val coords = input
                .filter { it.matches(Regex("[0-9].*")) }
                .map { line -> line.split(",").map { it.toInt() } }
                .map { (x, y) -> (x to y) }

            val maxX = coords.maxOf { (x, _) -> x }
            val maxY = coords.maxOf { (_, y) -> y }

            val grid = Array(maxY + 1) { Array(maxX + 1) { "." } }
            coords.forEach { (x, y) ->
                grid[y][x] = "#"
            }

            return Paper(grid)
        }
    }

    fun visibileSquares(): Int = grid.flatten().count { it == "#" }

    fun fold(fold: Fold): Paper {
        val paperTofold = if (fold.axis == "y") this else Paper(this.grid.transpose())
        val firstHalf = paperTofold.grid.sliceArray(0 until fold.num)
        val secondHalf = paperTofold.grid.sliceArray(fold.num + 1 until paperTofold.grid.size)
        secondHalf
            .forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, cell ->
                    if (cell == "#") {
                        firstHalf[firstHalf.size - rowIndex - 1][colIndex] = cell
                    }
                }
            }
        return if (fold.axis == "y") Paper(firstHalf) else Paper(firstHalf.transpose())
    }

    fun print() {
        grid.forEachIndexed { rowIndex, row ->
            print(rowIndex.toString().padStart(2, ' ') + " ")
            row.forEachIndexed { colIndex, cell ->
                print(cell)
            }
            println()
        }
        println("----------------------------------")
    }

}


fun main() {
    val dayName = "Day13"

    fun part1(input: List<String>): Int {
        val folds = Fold.of(input)
        val paper = Paper.of(input)
        val newPaper = paper.fold(folds[0])
        return newPaper.visibileSquares()
    }

    fun part2(input: List<String>): Paper {
        val folds = Fold.of(input)
        var paper = Paper.of(input)
        folds.forEach { fold ->
            paper = paper.fold(fold)
        }
        return paper
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 17

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 837

    val outputPart2 = part2(input)
    outputPart2.print()
    // EPZGKCHU
}
