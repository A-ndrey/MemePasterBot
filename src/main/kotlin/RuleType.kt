import java.util.*

enum class RuleType {
    FONT,
    SIZE,
    COLOR,
    POSITION;

    companion object {
        fun byName(name: String): RuleType? {
            return try {
                enumValueOf<RuleType>(name.toUpperCase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}

