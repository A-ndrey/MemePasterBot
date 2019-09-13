import java.io.FileReader
import java.util.Properties

object Properties {
    private val properties = Properties()

    val token: String
        get() = properties["token"] as String

    init {
        val propertiesFile = System.getProperty("user.dir") + "/app.properties"
        val reader = FileReader(propertiesFile)
        properties.load(reader)
    }
}