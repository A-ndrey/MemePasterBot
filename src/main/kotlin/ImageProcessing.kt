import java.awt.Color
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.awt.RenderingHints
import java.io.File
import javax.imageio.ImageIO

fun pathToFile(fileId: String) = "${System.getProperty("user.dir")}/cache/${fileId}"

fun addTextG2d(path: String, text: String, rules: Map<RuleType, Value>): String {
    val image = ImageIO.read(File(path))
    val g2d = image.createGraphics()

    g2d.font = Font("Droid", Font.PLAIN, 20)
    val fontMetrics = g2d.fontMetrics
    val x = (image.width - fontMetrics.stringWidth(text)) / 2
    val y = fontMetrics.height
    g2d.color = Color.BLACK
    g2d.drawString(text, x, y)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.dispose()

    val modifiedPath = path + "modified.png"
    ImageIO.write(image, "png", File(modifiedPath))

    return modifiedPath
}

fun availableFonts() =
    GraphicsEnvironment.getLocalGraphicsEnvironment().allFonts
        .map { it.family.split(' ').first() }
        .distinct()