typealias Value = String

data class Result(val text: String, val rules: Map<RuleType, Value>)

fun parseMessage(message: String): Result {
    if (!message.contains("###")) {
        return Result(message, emptyMap())
    }
    val (text, rawRules) = message.split("###", limit = 2)
    val rules = rawRules.split(" ")
        .map { it.split("=", limit = 2).map(String::trim) }
        .mapNotNull { it.takeIf { it.size == 2 } }
        .mapNotNull { (property, value) -> RuleType.byName(property)?.to(value) }
        .toMap()
    return Result(text, rules)
}
