package ru.hse.software.construction.model

import java.util.concurrent.BlockingQueue

class Chef(
    private val orderQueue: BlockingQueue<Order>
) : Runnable {
    override fun run() {
        while (true) {
            val order = orderQueue.take()
            processOrder(order)
        }
    }

    private fun processOrder(order: Order) {
        order.setStatus(OrderStatus.PROCESSING)
        println(order)

        Thread.sleep(order.getProcessingTime().toLong() * 1000)

        order.setStatus(OrderStatus.READY)
        println(order)
    }
}