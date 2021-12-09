data class LanternFish(var timer: Int = 8) {
    fun age(): LanternFish? {
        timer--
        if(timer < 0) {
            timer = 6
            return LanternFish()
        }
        return null
    }

    override fun toString(): String {
        return timer.toString()
    }
}

fun main() {
    val dayName = "Day06"

    fun part1(input: List<String>): Int {
        val initialState = input[0]
        val fish = initialState.split(',')
            .map { LanternFish(it.toInt()) }
            .toMutableList()

        (1..80).forEach { _ ->
            val newFish = fish.mapNotNull { it.age() }
            fish.addAll(newFish)
        }

        return fish.size
    }

    fun part2(input: List<String>): Int = 1

    val testInput = readInput("${dayName}_test")
    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 5934)

    val input = readInput(dayName)
    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 356190)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
//    check(testOutputPart2 == 5)

    val outputPart2 = part2(input)
    println(outputPart2)
//    check(outputPart2 == 1248)
}
