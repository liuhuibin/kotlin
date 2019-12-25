class Bar {
    fun next(): Bar? {
        if (2 == 4)
            return this
        else
            return null
    }
}

fun foo(): Bar {
    var x: Bar? = Bar()
    var y: Bar?
    y = Bar()
    while (x != null) {
        // Here call is unsafe because of inner loop
        y.next()
        while (y != null) {
            if (x == y)
                // x is not null because of outer while
                return x
            // y is not null because of inner while
            y = y.next()
        }
        // x is not null because of outer while
        x = x.next()
    }
    return Bar()
}