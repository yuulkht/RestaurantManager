package ru.hse.software.construction.model

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

        Thread.sleep(order.getProcessingTime().toLong() * 60 * 1000)

        order.setStatus(OrderStatus.READY)
    }

    fun stop() {
        shouldStop = true
    }
}
