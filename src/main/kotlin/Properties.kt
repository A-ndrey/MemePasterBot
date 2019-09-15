import java.io.FileReader
import java.util.Properties

object Properties {
    private val properties = Properties()

    val token: String
        get() = getValue("token")

    val defaultFont: String
        get() = getValue("default.font")

    val defaultSize: Int
        get() = getValue("default.size").toInt()

    val defaultColor: String
        get() = getValue("default.color")

    val defaultPosition: String
        get() = getValue("default.position")

    init {
        val propertiesFile = System.getProperty("user.dir") + "/app.properties"
        val reader = FileReader(propertiesFile)
        properties.load(reader)
    }

    private fun getValue(key: String) = properties[key] as String
}