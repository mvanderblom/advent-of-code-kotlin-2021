fun main() {

    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input
            .map { it.split(' ') }
            .forEach { (direction, amount) ->
                run {
                    when(direction) {
                        "forward" -> horizontal += amount.toInt()
                        "down" -> depth += amount.toInt()
                        "up" -> depth -= amount.toInt()
                    }
                }
            }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input
            .map { it.split(' ') }
            .forEach { (direction, amount) ->
                run {
                    when(direction) {
                        "forward" -> {
                            horizontal += amount.toInt()
                            depth += aim * amount.toInt()
                        }
                        "down" -> aim += amount.toInt()
                        "up" -> aim -= amount.toInt()
                    }
                }
            }

        return horizontal * depth
    }

    val testInput = readInput("Day02_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 150)

    val input = readInput("Day02")
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 1989014)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
    check(testOutputPart2 == 900)

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 2006917119)
}
