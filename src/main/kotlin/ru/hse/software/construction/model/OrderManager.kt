package ru.hse.software.construction.model

import java.util.concurrent.*

class OrderManager(
    private val executor: ExecutorService = Executors.newFixedThreadPool(3),
    private val orderQueue: BlockingQueue<Order> = LinkedBlockingQueue()
) {
    init {
        repeat(3) {
            executor.submit(Chef(orderQueue))
        }
    }

    fun processOrder(order: Order) {
        orderQueue.put(order)
    }
}