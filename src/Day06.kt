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

    fun part1(input: List<String>): Long {
        val initialState = input[0]
        val fish = initialState.split(',')
            .map { LanternFish(it.toInt()) }
            .toMutableList()

        (1..80).forEach { _ ->
            val newFish = fish.mapNotNull { it.age() }
            fish.addAll(newFish)
        }

        return fish.size.toLong()
    }

    fun part2(input: List<String>): Long {
        var fish = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0)
        input[0].split(',')
            .map { it.toInt() }
            .forEach { fish[it]++ }

        (0 until 256).forEach { day ->
            val newFish = fish[0]
            fish = fish.subList(1, fish.size).toMutableList()
            fish.add(newFish)
            fish[6] += newFish
//            fish[(day + 7) % 9] += fish[day % 9]
            println("$fish (${fish.sum()})")
        }

        return fish.sum()
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    println(testOutputPart1)
    check(testOutputPart1 == 5934L)

    val outputPart1 = part1(input)
    println(outputPart1)
    check(outputPart1 == 356190L)

    val testOutputPart2 = part2(testInput)
    println(testOutputPart2)
    check(testOutputPart2 == 26984457539L)

    val outputPart2 = part2(input)
    println(outputPart2)
    check(outputPart2 == 1617359101538L)
}
