package ai.datadive.android.sample.kt

data class Product (
    val id: String,
    val name: String,
    val brand_id: String,
    val brand_name: String,
    val price: Int,
    val review_count: Int,
    val review_avg_rating: Double
)


class Cart {
    companion object {
        var orders = ArrayList<Product>()
    }
}

data class Category (
    val id: String,
    val name: String,
    val products: Array<Product>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (name != other.name) return false
        if (!products.contentEquals(other.products)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + products.contentHashCode()
        return result
    }
}
