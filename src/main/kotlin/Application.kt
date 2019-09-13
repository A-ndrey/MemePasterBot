import rocks.waffle.telekt.bot.*
import rocks.waffle.telekt.contrib.filters.CommandFilter
import rocks.waffle.telekt.contrib.filters.ContentTypeFilter
import rocks.waffle.telekt.dispatcher.*
import rocks.waffle.telekt.network.InputFile
import rocks.waffle.telekt.types.enums.ContentType
import rocks.waffle.telekt.util.Recipient
import rocks.waffle.telekt.util.answerOn
import rocks.waffle.telekt.util.handlerregistration.dispatch
import rocks.waffle.telekt.util.handlerregistration.handle
import rocks.waffle.telekt.util.handlerregistration.messages
import java.io.File

suspend fun main() {
    val bot = Bot(Properties.token)
    val dp = Dispatcher(bot)

    dp.dispatch {
        messages {
            handle(CommandFilter("start", "help")) {
                bot.answerOn(it, "Hi! I'm MemePasterBot v1.0. Send me photo with caption and I'll combine them and send you.")
            }

            handle(ContentTypeFilter(ContentType.PHOTO)) {
                val fileId = it.photo!!.last().fileId
                val path = pathToFile(fileId)
                bot.downloadFileByFileId(fileId, path)
                val pathToModified = addText(path, it.caption ?: "")
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
