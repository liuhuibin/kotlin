fun bar(x: Int): Int = x + 1

fun foo() {
    val x: Int? = null
    
    bar(x)
    if (x == null) return
    try {
        bar(x)
    }
    catch (e: Exception) {
        bar(x)
    }
    bar(x)
}
