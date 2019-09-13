import org.im4java.core.ConvertCmd
import org.im4java.core.IMOperation

fun pathToFile(fileId: String) = "${System.getProperty("user.home")}/MemePasterBot/${fileId}"

fun addText(path: String, text: String): String {
    val modifiedPath = path + "modified.png"

    val cmd = ConvertCmd()

    val op = IMOperation()
    op.addImage(path)
    op.font("Lobster")
    op.pointsize(40)
    op.gravity("north")
    op.fill("white")
    op.annotate(null, null, 0, 30)
    op.addRawArgs(text)
    op.addImage(modifiedPath)

    cmd.run(op)

    return modifiedPath
}