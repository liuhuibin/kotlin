package a

class A {
    class Nested
    inner class Inner


    companion object {

        class Nested2

        val c: Int = 1

        object Obj2 {
            val c: Int = 1
        }
    }

    object Obj
}

object O {
    class A

    object O
}

fun f() {
    A.c
    A.hashCode()
    A().Nested
    A.Nested()
    A().Inner()
    A.Companion.<!UNRESOLVED_REFERENCE!>Nested<!>
    A.Companion.<!UNRESOLVED_REFERENCE!>Inner<!>
    A.Inner
    A.Companion.c
    A.Companion.Obj2
    A.Companion.Obj2.c

    A.Companion.Nested2()
    A.Companion.c
    A.Obj
    A.Companion.Obj2
    A.Obj2
    A.Obj2.c
    A.Nested2

    O.O
    O.A()
}