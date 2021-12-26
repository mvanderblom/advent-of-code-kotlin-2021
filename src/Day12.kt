val uppercase = Regex("[A-Z]+")

class Links(input: List<String>){
    private val links: Map<String, List<String>>

    init {
        links = input
            .map { parseLine(it) }
            .flatMap { (source, dest) -> reversePaths(source, dest) }
            .groupBy (
                {(source, _) -> source},
                {(_, target) -> target}
            )
            .also { println(it.entries.joinToString(System.lineSeparator())) }
        println("-----------------")
    }
    private fun parseLine(it: String): Pair<String, String> {
        val (source, dest) = it.split("-")
            .sortedBy {
                when {
                    it.contains("start") -> -1
                    it.contains("end") -> 1
                    else -> 0
                }
            }
        return source to dest
    }

    private fun reversePaths(
        source: String,
        dest: String
    ) = if (source != "start" && dest != "end"
    )
        listOf(source to dest, dest to source)
    else
        listOf(source to dest)


    val paths by lazy { explore("start") }

    private fun explore(target: String,
                        currentPath: MutableList<String> = mutableListOf(),
                        validPaths: MutableList<List<String>> = mutableListOf()
    ): List<List<String>> {
        currentPath.add(target)
        val destinations = this.links[target]

        if(target == "end") {
            validPaths.add(currentPath)
        } else{
            destinations
                ?.filter { !currentPath.contains(it) || it.matches(uppercase) }
                ?.forEach { newDest ->
                    explore(newDest, currentPath.toMutableList(), validPaths)
                }
        }

//        println("Explore $currentPath")
//        println("destinations $destinations")
//        println("validPaths: $validPaths")
//        println("--------------------")
        return validPaths

    }
}

fun main() {
    val dayName = "Day12"


    fun part1(input: List<String>): Int {
        val links = Links(input)
        return links.paths.size
    }

    fun part2(input: List<String>): Int = input.size

    val testInputSimple1 = readInput("${dayName}_test_simple_1")
    val testInputSimple2 = readInput("${dayName}_test_simple_2")
    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    part1(testInputSimple1) isEqualTo 10

    part1(testInputSimple2) isEqualTo 19

    part1(testInput) isEqualTo 226

    part1(input) isEqualTo 1

//    part2(testInput) isEqualTo 1
//
//    part2(input) isEqualTo 1
}
