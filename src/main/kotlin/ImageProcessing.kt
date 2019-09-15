import org.im4java.core.ConvertCmd
import org.im4java.core.IMOperation

fun pathToFile(fileId: String) = "${System.getProperty("user.home")}/MemePasterBot/${fileId}"

fun addText(path: String, text: String, rules: Map<RuleType, Value>): String {
    val modifiedPath = path + "modified.png"

    val cmd = ConvertCmd()

    val op = IMOperation()
    op.addImage(path)
    op.font(rules[RuleType.FONT] ?: Properties.defaultFont)
    op.pointsize(rules[RuleType.SIZE]?.toInt() ?: Properties.defaultSize)
    op.gravity("north")
    op.fill(rules[RuleType.COLOR] ?: Properties.defaultColor)
    op.annotate(null, null, 0, 30)
    op.addRawArgs(text)
    op.addImage(modifiedPath)

    cmd.run(op)

    return modifiedPath
}