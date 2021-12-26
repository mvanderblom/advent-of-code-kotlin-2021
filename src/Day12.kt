val upperCase = Regex("[A-Z]+")
val lowerCase = Regex("[a-z]+")

class Chart(input: List<String>) {
    val links: Map<String, List<String>>

    init {
        links = input
            .flatMap { parseLine(it) }
            .filter { (source, target) -> source != "end" && target != "start" }
            .groupBy (
                {(source, _) -> source},
                {(_, target) -> target}
            ).also {
                println(it)
                println("-----------------")
            }
    }

    private fun parseLine(it: String): List<Pair<String, String>> {
        val (source, dest) = it.split("-")
        return listOf(source to dest, dest to source)
    }

}

class Explorer(val chart: Chart,
               val destinationFilter: (currentPath: List<String>, destination: String) -> Boolean) {

    val paths by lazy { explore("start") }

    private fun explore(target: String,
                        currentPath: MutableList<String> = mutableListOf(),
                        validPaths: MutableList<List<String>> = mutableListOf()
    ): List<List<String>> {
        currentPath.add(target)
//        println("Explore $currentPath")

        if (target == "end") {
            validPaths.add(currentPath)
        } else{
            val destinations = this.chart.links[target]
//            println("destinations $destinations")
            destinations
                ?.filter { destinationFilter(currentPath, it) }
                ?.forEach { newDest ->
                    explore(newDest, currentPath.toMutableList(), validPaths)
                }
        }

//        println("validPaths: $validPaths")
//        println("--------------------")
        return validPaths
    }
}

fun main() {
    val dayName = "Day12"

    fun part1(input: List<String>): Int {
        val chart = Chart(input)
        val explorer = Explorer(chart) { path, dest -> !path.contains(dest) || dest.matches(upperCase) }
        return explorer.paths.size
    }

    fun part2(input: List<String>): Int {
        val chart = Chart(input)
        val explorer = Explorer(chart) { path, dest ->
            val lowerCaseChars = path.filter { it.matches(lowerCase) }
            dest.matches(upperCase) || lowerCaseChars.size - lowerCaseChars.toSet().size <= 1
        }
        return explorer.paths.size
    }

    val testInputSimple1 = readInput("${dayName}_test_simple_1")
    val testInputSimple2 = readInput("${dayName}_test_simple_2")
    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    part1(testInputSimple1) isEqualTo 10

    part1(testInputSimple2) isEqualTo 19

    part1(testInput) isEqualTo 226

    part1(input) isEqualTo 5874

    part2(testInputSimple1) isEqualTo 36

    part2(testInputSimple2) isEqualTo 103

    part2(testInput) isEqualTo 3509

    part2(input) isEqualTo 153592
}
