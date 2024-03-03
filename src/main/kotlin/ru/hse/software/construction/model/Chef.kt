package ru.hse.software.construction.model

import ru.hse.software.construction.view.ConsoleOutputHandler
import java.util.concurrent.BlockingQueue

class Chef(
    private val orderQueue: BlockingQueue<Order>,
    @Volatile
    var shouldStop: Boolean = false
) : Runnable {
    override fun run() {
        while (!shouldStop) {
            val order = orderQueue.poll() ?: continue
            processOrder(order)
        }
    }

    private fun processOrder(order: Order) {
        order.setStatus(OrderStatus.PROCESSING)
        val outputHandler = ConsoleOutputHandler()
        outputHandler.displayOrderStatus(order)

        Thread.sleep(order.getProcessingTime().toLong() * 60 * 1000)

        order.setStatus(OrderStatus.READY)
        outputHandler.displayOrderStatus(order)
    }

    fun stop() {
        shouldStop = true
    }
}
