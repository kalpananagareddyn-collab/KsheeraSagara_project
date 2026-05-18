package com.ksheerasagara.dairy.data

data class Cow(
    val id: String,
    val name: String,
    val milkLiters: Double,
    val fatPct: Double,
    val snfPct: Double,
)

data class Expense(
    val category: String, // Feed, Labour, Vet, Other
    val amount: Double,
)

data class Entry(
    val id: String,
    val cowId: String,
    val cowName: String,
    val milkLiters: Double,
    val fatPct: Double,
    val snfPct: Double,
    val pricePerLiter: Double,
    val expenses: List<Expense>,
    val timestamp: Long = System.currentTimeMillis(),
) {
    val revenue: Double get() = milkLiters * pricePerLiter
    val totalExpense: Double get() = expenses.sumOf { it.amount }
    val profit: Double get() = revenue - totalExpense
}
