import rocks.waffle.telekt.bot.*
import rocks.waffle.telekt.contrib.filters.CommandFilter
import rocks.waffle.telekt.contrib.filters.ContentTypeFilter
import rocks.waffle.telekt.dispatcher.*
import rocks.waffle.telekt.network.InputFile
import rocks.waffle.telekt.types.Message
import rocks.waffle.telekt.types.enums.ContentType
import rocks.waffle.telekt.util.Recipient
import rocks.waffle.telekt.util.answerOn
import rocks.waffle.telekt.util.handlerregistration.dispatch
import rocks.waffle.telekt.util.handlerregistration.handle
import rocks.waffle.telekt.util.handlerregistration.messages
import rocks.waffle.telekt.util.splitByLength
import java.io.File

suspend fun main() {
    val bot = Bot(Properties.token)
    val dp = Dispatcher(bot)

    dp.dispatch {
        messages {
            handle(commandFilter(MessageCommand.START)) {
                bot.answerOn(it, Properties.greeting)
            }

            handle(commandFilter(MessageCommand.HELP)) {
                bot.answerOn(it, "List of available commands:\n$MessageCommand")
            }

            handle(commandFilter(MessageCommand.FONTS)) { message ->
                availableFonts()
                    .chunked(100)
                    .map { it.joinToString(separator = "\n") }
                    .forEach { bot.answerOn(message, it) }
            }

            handle(ContentTypeFilter(ContentType.PHOTO)) {
                val fileId = it.photo!!.last().fileId
                val path = pathToFile(fileId)
                if (!File(path).exists()) {
                    bot.downloadFileByFileId(fileId, path)
                }
                val (text, rules) = parseMessage(it.caption ?: "")
                val pathToModified = addTextG2d(path, text, rules)
                val file = File(pathToModified)
                bot.sendPhoto(
                    Recipient(it.from!!.id),
                    InputFile(file)
                )
            }

            handle {

            }
        }
    }

    dp.poll()
}