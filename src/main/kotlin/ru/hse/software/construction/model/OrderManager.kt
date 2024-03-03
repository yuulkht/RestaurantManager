package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class OrderManager(
    private val executor: ExecutorService = Executors.newFixedThreadPool(3),
    private val chefs: MutableList<Chef> = mutableListOf(),
    private val orderQueue: BlockingQueue<Order> = LinkedBlockingQueue()
) {
    // TODO возмжны какие-то проблемы опять с многопточкой

    init {
        repeat(3) {
            val chef = Chef(orderQueue)
            chefs.add(chef)
            executor.submit(chef)
        }
    }

    fun processOrder(order: Order) {
        orderQueue.put(order)
    }

    fun stopAllChefs() {
        for (chef in chefs) {
            chef.stop()
        }
        executor.shutdown()
    }
}

