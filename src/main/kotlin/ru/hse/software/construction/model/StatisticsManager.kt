package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize

class StatisticsManager (
    @JsonSerialize
    private val reviews: MutableList<Review> = mutableListOf()
) {

    fun addReview(rating: Int, description: String, order: Order) {
        val review = Review(rating, description, order)
        reviews.add(review)
    }

    fun getReviews(): MutableList<Review> {
        return reviews
    }

    @JsonIgnore
    fun getRestaurantRating(): Double {
        if (reviews.isEmpty()) {
            return 0.0
        }
        val sum = reviews.sumOf { it.getRating() }
        return sum.toDouble() / reviews.size
    }

    @JsonIgnore
    fun getAverageRatings(): Map<Dish, Double> {
        val dishRatings = mutableMapOf<Dish, MutableList<Int>>()

        reviews.forEach { review ->
            review.getOrder().getDishes().forEach { dish ->
                val ratings = dishRatings.getOrPut(dish) { mutableListOf() }
                ratings.add(review.getRating())
            }
        }

        val averageRatings = dishRatings.mapValues { (_, ratings) ->
            ratings.average()
        }

        return averageRatings
            .toList()
            .sortedByDescending { it.second }
            .toMap()
    }

    @JsonIgnore
    fun getPopularDishesByCount(): Map<Dish, Int> {
        val dishCounts = mutableMapOf<Dish, Int>()

        reviews.forEach { review ->
            review.getOrder().getDishes().forEach { dish ->
                dishCounts[dish] = dishCounts.getOrDefault(dish, 0) + 1
            }
        }

        return dishCounts
            .toList()
            .sortedByDescending { it.second }
            .toMap()
    }


}
