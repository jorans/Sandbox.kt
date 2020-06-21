import java.lang.IllegalArgumentException

class Termin(val kod5: String): Comparable<Termin> {
    init {
        val pattern = "2([0-9]{3})[12]"
        if (!kod5.matches(pattern.toRegex())) {
                throw IllegalArgumentException("$kod5 does not match $pattern")
        }
    }

    val kod3: String
        get(){
        val prefix = if(kod5.endsWith('1')) "V" else "H"
        return prefix + kod5.substring(2, 4)
    }
    override fun compareTo(other: Termin) = kod5.compareTo(other.kod5)

}

operator fun Termin.rangeTo(that: Termin) = TerminProgression(this, that)

operator fun Termin.inc(step: Int = 1): Termin {
    if(step === 0){
        throw IllegalArgumentException("The value of 'step' may not be 0")
    }
    val terminStep = step%2
    val terminCurrent = if (kod5.endsWith('1')) 1 else 2
    val arCurrent = kod5.substring(0, 4).toInt()

    val nextTermin  = if((terminStep + terminCurrent)%2 == 1) 1 else 2
    val nextAr = arCurrent + (step / 2) +
            (if(terminStep + terminCurrent == 3) 1 else 0) +
            (if(terminStep + terminCurrent == 0) -1 else 0)
    return Termin("$nextAr$nextTermin")
}
operator fun Termin.dec(step: Int = 1): Termin {
    return inc(-1 * step)
}


class TerminIterator(val start: Termin, val endInclusive: Termin, val step: Int = 1): Iterator<Termin> {
    var initialValue = start

    override fun hasNext(): Boolean {
        return if(step > 0) initialValue <= endInclusive else initialValue >= endInclusive
    }

    override fun next(): Termin {
        val currentValue = initialValue
        initialValue = initialValue.inc(step)
        return currentValue
    }

}

class TerminProgression(override val start: Termin,
                        override val endInclusive: Termin,
                        val step: Int = 1): ClosedRange<Termin>, Iterable<Termin> {

    init {
        if(step === 0){
            throw IllegalArgumentException("The value of 'step' may not be 0")
        }
    }
    override fun iterator(): Iterator<Termin> {
        return TerminIterator(start, endInclusive, step)
    }

    infix fun step(terminer: Int) = TerminProgression(start, endInclusive, terminer)

    fun toList() = iterator()
        .asSequence()
        .toList()
}