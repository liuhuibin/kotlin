// IGNORE_BACKEND_FIR: JVM_IR
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS, NATIVE

class Test {
    private var iv = 1

    public fun exec() {
        val t = object : Thread() {
            override fun run() {
                Test::iv.get(this@Test)
                Test::iv.set(this@Test, 2)
            }
        }
        t.start()
        t.join(1000)
    }

    fun result() = if (iv == 2) "OK" else "Fail $iv"
}

fun box(): String {
    val t = Test()
    t.exec()
    return t.result()
}
