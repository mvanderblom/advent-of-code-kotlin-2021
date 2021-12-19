class Links(input: List<String>){
    private val links: Map<String, List<Pair<String, String>>>
    val paths: List<List<String>>

    init {
        links = input
            .map {
                val (source, dest) = it.split("-")
                    .sortedBy { x -> x != "start" }
                    .sortedBy { x -> x == "end" }
                source to dest
            }
            .groupBy { (source, _) -> source }
        links.entries.forEach { (source, dest) -> }
        println("Links: $links")
        paths = links["start"]!!
            .map { (_, dest) -> explore(dest, mutableListOf("start")) }
    }

    private fun explore(dest: String, path: MutableList<String>): List<String> {
        path.add(dest)
        if(dest.matches(Regex("[A-Z]")) || !path.contains(dest)) {
            this.links[dest]
                ?.map { (_, dest) -> explore(dest, path) }
        }
        return path
    }
}

fun main() {
    val dayName = "Day12"


    fun part1(input: List<String>): Int {
        val links = Links(input)
        println(links.paths.joinToString(System.lineSeparator()))
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

    part2(testInput) isEqualTo 1

    part2(input) isEqualTo 1
}
