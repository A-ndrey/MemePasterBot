import rocks.waffle.telekt.contrib.filters.CommandFilter

enum class MessageCommand(val info: String) {
    START("activate bot and show greeting message"),
    HELP("show list of available commands"),
    FONTS("show list of available fonts");

    companion object {
        override fun toString() = values().joinToString(separator = "\n") { "/${it.name.toLowerCase()} - ${it.info}" }
    }
}

fun commandFilter(command: MessageCommand, vararg commands: MessageCommand) =
    CommandFilter(command.name.toLowerCase(), *commands.map { it.name.toLowerCase() }.toTypedArray(), ignoreCase = true)