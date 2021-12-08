fun main() {
    fun List<List<Int>>.transpose(): List<List<Int>> {
        val columns = this[0].size

        val transposed = (0 until columns).map { mutableListOf<Int>() }.toMutableList()
        for (row in this) {
            for (column in 0 until columns) {
                transposed[column].add(row[column])
            }
        }

        return transposed
    }

    class Board(rawData: List<String>){
        val rows: List<List<Int>>
        val cols: List<List<Int>>
        val numbers: List<Int>

        init {
            this.rows = rawData.map { row ->
                row.chunked(3)
                    .map { number ->
                        number.trim().toInt()
                    }
            }

            this.cols = this.rows.transpose()
            this.numbers = this.rows.flatten()
        }

        fun score(drawnNumbers: List<Int>): Int {
            val winningRow by lazy { this.rows.any { row -> drawnNumbers.containsAll(row)  } }
            val winningCol by lazy { this.cols.any { col -> drawnNumbers.containsAll(col)  } }

            if(winningRow || winningCol) {
                return (this.numbers - drawnNumbers).sum() * drawnNumbers.last()
            }

            return -1
        }

        override fun toString(): String {
            return "Board" + System.lineSeparator() +
                    cols.joinToString(System.lineSeparator()) +
                    System.lineSeparator()
        }
    }

    fun getBoards(input: List<String>) = input
        .subList(2,input.size)
        .filter { it != "" }
        .chunked(5)
        .map(::Board)

    fun getNumbers(input: List<String>) = input[0]
        .split(',')
        .map { it.toInt() }

    fun part1(input: List<String>): Int {
        val boards = getBoards(input)
        println("boards: ${boards}")

        val numbers = getNumbers(input)
        println("randomNumbers: ${numbers}")

        for (i in (0..numbers.size)) {
            for (board in boards) {
                val score = board.score(numbers.subList(0, i+1))
                if (score > -1)
                    return score
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val boards = getBoards(input)
        println("boards: ${boards}")

        val numbers = getNumbers(input)
        println("randomNumbers: ${numbers}")

        val winningBoards = mutableListOf<Board>()
        for (i in (0..numbers.size)) {
            for (board in boards) {
                if (!winningBoards.contains(board)) {
                    val score = board.score(numbers.subList(0, i+1))
                    if (score > -1)
                        winningBoards.add(board)

                    if (winningBoards.size == boards.size)
                        return score
                }
            }
        }

        return -1
    }

    val testInput = readInput("Day04_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 4512)

    val input = readInput("Day04")
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 44088)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
    check(testOutputPart2 == 1924)

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 1248)
}
