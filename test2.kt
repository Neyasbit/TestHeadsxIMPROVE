fun main() {
    println(generateListOfLists(2, 7, 0, 100, 5))
}

fun generateListOfLists(
        minSize: Int,
        maxSize: Int,
        minNumber: Int,
        maxNumber: Int,
        numberOfLists: Int
): List<List<Int>> {
    val sizes = generateDistinctSizes(minSize, maxSize, numberOfLists)
    return generateLists(sizes, minNumber, maxNumber, mutableListOf(), 0)
}

private fun generateDistinctSizes(
        minSize: Int,
        maxSize: Int,
        numberOfLists: Int
): List<Int> {
    val distinctRandomSizes = mutableSetOf<Int>()
    if (maxSize - minSize < numberOfLists) {
         throw Exception("the difference between the maximum and minimum size of the array must be greater than the number of required massive")
    }
    while (distinctRandomSizes.size < numberOfLists) {
        distinctRandomSizes.add((minSize..maxSize).random())
    }
    return distinctRandomSizes.toList()
}


tailrec fun generateLists(
        sizes: List<Int>,
        minNumber: Int,
        maxNumber: Int, acc: MutableList<List<Int>>, currentSizeIndex: Int
): List<List<Int>> {
    return if (currentSizeIndex == sizes.size) {
        acc
    } else {
        val size = sizes[currentSizeIndex]
        acc.add(generateRandomSortedList(size, minNumber, maxNumber, chooseComparator(currentSizeIndex)))
        generateLists(sizes, minNumber, maxNumber, acc, currentSizeIndex + 1)
    }
}

fun generateRandomSortedList(
        size: Int,
        minNumber: Int,
        maxNumber: Int,
        comparator: Comparator<Int>
): List<Int> =
        List(size) { (minNumber..maxNumber).random() }
                .sortedWith(comparator)


fun chooseComparator(i: Int): Comparator<Int> =
        if (i % 2 == 0)
            ascending
        else
            descending

object ascending : Comparator<Int> {
    override fun compare(x: Int, y: Int): Int = x - y
}

object descending : Comparator<Int> {
    override fun compare(x: Int, y: Int): Int = y - x
}