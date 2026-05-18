package com.ksheerasagara.dairy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.ksheerasagara.dairy.data.Cow
import com.ksheerasagara.dairy.data.Entry
import com.ksheerasagara.dairy.data.Expense
import java.util.UUID

class DairyViewModel : ViewModel() {
    val isLoggedIn = mutableStateOf(false)
    val userName = mutableStateOf("Guest")

    val cows = mutableStateListOf(
        Cow("c1", "Lakshmi", 12.0, 4.2, 8.6),
        Cow("c2", "Ganga", 9.5, 3.8, 8.4),
        Cow("c3", "Kamadhenu", 11.0, 4.0, 8.5),
    )
    val entries = mutableStateListOf<Entry>()

    init {
        // seed sample entries across last 6 months
        val now = System.currentTimeMillis()
        val day = 24L * 60 * 60 * 1000
        repeat(30) { i ->
            val cow = cows.random()
            entries.add(
                Entry(
                    id = UUID.randomUUID().toString(),
                    cowId = cow.id,
                    cowName = cow.name,
                    milkLiters = cow.milkLiters + (-2..2).random(),
                    fatPct = cow.fatPct,
                    snfPct = cow.snfPct,
                    pricePerLiter = 35.0,
                    expenses = listOf(
                        Expense("Feed", (80..150).random().toDouble()),
                        Expense("Labour", (30..70).random().toDouble()),
                        Expense("Vet", (10..40).random().toDouble()),
                        Expense("Other", (5..25).random().toDouble()),
                    ),
                    timestamp = now - (i * 6L * day),
                )
            )
        }
    }

    fun login(name: String) {
        userName.value = if (name.isBlank()) "Farmer" else name
        isLoggedIn.value = true
    }

    fun loginAsGuest() {
        userName.value = "Guest"
        isLoggedIn.value = true
    }

    fun logout() {
        isLoggedIn.value = false
    }

    fun addCow(name: String) {
        if (name.isBlank()) return
        cows.add(Cow(UUID.randomUUID().toString(), name, 8.0, 3.8, 8.4))
    }

    fun addEntry(
        cowId: String, milk: Double, fat: Double, snf: Double, price: Double,
        feed: Double, labour: Double, vet: Double, other: Double
    ) {
        val cow = cows.find { it.id == cowId } ?: return
        entries.add(
            Entry(
                id = UUID.randomUUID().toString(),
                cowId = cow.id,
                cowName = cow.name,
                milkLiters = milk,
                fatPct = fat,
                snfPct = snf,
                pricePerLiter = price,
                expenses = listOf(
                    Expense("Feed", feed),
                    Expense("Labour", labour),
                    Expense("Vet", vet),
                    Expense("Other", other),
                ),
            )
        )
    }

    fun totalProfit(): Double = entries.sumOf { it.profit }
    fun totalRevenue(): Double = entries.sumOf { it.revenue }
    fun totalExpense(): Double = entries.sumOf { it.totalExpense }

    fun expenseByCategory(): Map<String, Double> {
        val map = mutableMapOf("Feed" to 0.0, "Labour" to 0.0, "Vet" to 0.0, "Other" to 0.0)
        entries.forEach { e -> e.expenses.forEach { map[it.category] = (map[it.category] ?: 0.0) + it.amount } }
        return map
    }

    fun profitByCow(): List<Pair<Cow, Double>> =
        cows.map { c -> c to entries.filter { it.cowId == c.id }.sumOf { it.profit } }
            .sortedByDescending { it.second }

    fun last6MonthProfits(): List<Pair<String, Double>> {
        val months = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
        val cal = java.util.Calendar.getInstance()
        val now = cal.get(java.util.Calendar.MONTH)
        val year = cal.get(java.util.Calendar.YEAR)
        val result = mutableListOf<Pair<String, Double>>()
        for (i in 5 downTo 0) {
            val m = ((now - i) % 12 + 12) % 12
            val label = months[m]
            val profit = entries.filter {
                val c = java.util.Calendar.getInstance().apply { timeInMillis = it.timestamp }
                c.get(java.util.Calendar.MONTH) == m
            }.sumOf { it.profit }
            result.add(label to profit)
        }
        return result
    }
}
