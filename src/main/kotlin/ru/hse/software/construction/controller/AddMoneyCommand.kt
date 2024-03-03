import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.model.Visitor
import ru.hse.software.construction.view.ConsoleOutputHandler
import ru.hse.software.construction.reader.ConsoleUserReader
import java.math.BigDecimal

class AddMoneyCommand(
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {
    override fun process(programInfo: ProgramInfo) {
        val visitor = programInfo.authSession.getUser() as? Visitor
        if (visitor != null) {
            outputHandler.displayMessage("Ваш текущий баланс: ${visitor.getBalance()}")
            outputHandler.displayMessage("Введите сумму для пополнения:")
            val amount = reader.readInt()?.toLong()
            if (amount != null && amount > 0) {
                visitor.addBalance(BigDecimal.valueOf( amount))
                outputHandler.displayMessage("Баланс успешно пополнен. Новый баланс: ${visitor.getBalance()}")
            } else {
                outputHandler.displayError("Некорректная сумма пополнения.")
            }
        } else {
            outputHandler.displayError("Не удалось определить пользователя.")
        }
    }
}
