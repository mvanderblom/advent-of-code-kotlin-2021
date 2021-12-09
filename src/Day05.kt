data class Point(val x: Int, val y: Int) {
    companion object {
        fun of(stringValue: String): Point {
            val (x,y) = stringValue.split(',').map { it.toInt() }
            return Point(x, y)
        }
    }
}

data class Line(val start: Point, val end: Point) {

    fun getPoints(includeDiagonals: Boolean = false): List<Point> {
        val xes = rangeBetween(start.x, end.x)
        val ys = rangeBetween(start.y, end.y)

        if (start.x == end.x) {
            val lines = ys.map {
                Point(start.x, it)
            }
            return lines
        }

        if (start.y == end.y) {
            val lines = xes.map {
                Point(it, start.y)
            }
            return lines
        }

        if (includeDiagonals) {
            val diagXes = if (start.x > end.x) xes.reversed() else xes
            val diagYs = if (start.y > end.y) ys.reversed() else ys

            val lines = diagXes.zip(diagYs)
                .map { (x, y) -> Point(x, y) }
            return lines
        }

        return emptyList()
    }

    companion object {
        fun of(stringValue: String): Line {
            val (start,end) = stringValue.split(" -> ").map { Point.of(it) }
            return Line(start, end)
        }
    }
}

fun rangeBetween(thiz: Int, that: Int): IntRange {
    if (thiz < that)
        return thiz..that
    return that..thiz
}

fun main() {

    fun part1(input: List<String>, includeDiagonals: Boolean = false): Int {
        return input
            .map { Line.of(it) }
            .flatMap { it.getPoints(includeDiagonals) }
            .groupBy { it }
            .entries.count { (_, points) -> points.size >= 2 }
    }

    fun part2(input: List<String>): Int = part1(input, true)

    val testInput = readInput("Day05_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 5)

    val input = readInput("Day05")
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 5197)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
    check(testOutputPart2 == 12)

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 18605)
}
