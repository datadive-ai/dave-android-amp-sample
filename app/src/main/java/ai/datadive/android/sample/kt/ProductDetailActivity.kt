package ai.datadive.android.sample.kt

import ai.datadive.api.Datadive
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONObject

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        if (!intent.hasExtra("categoryPosition")) {
            Toast.makeText(this, "전달된 제품 정보가 없음.", Toast.LENGTH_SHORT).show()
        }
        val categoryPosition = intent.getIntExtra("categoryPosition", 0)
        val productPosition = intent.getIntExtra("productPosition", 0)
        var categories: List<Category> = getProductMenu(this)
        var category = categories[categoryPosition]
        var product = category.products[productPosition]

        val title = findViewById<TextView>(R.id.tv_product_name)
        val info = findViewById<TextView>(R.id.tv_product_info)
        val body = findViewById<TextView>(R.id.tv_product_body)
        title.text = product.name
        info.text = "${product.price} 원, 제조사: ${product.brand_name}"
        body.text = "평점: ${product.review_avg_rating}, 상품평 ${product.review_count} 개"

        // 장바구니 담기 이벤트
        val btnAddToCart = findViewById<Button>(R.id.btn_add_to_cart)
        btnAddToCart.setOnClickListener {
            /******************************/
            val eventProp = JSONObject()
            eventProp.put("itemid", product.id)
            eventProp.put("product_name", product.name)
            eventProp.put("price", product.price)
            eventProp.put("quantity", 1)
            eventProp.put("category_code", category.id)
            eventProp.put("category_name", category.name)
            eventProp.put("brand_id", product.brand_id)
            eventProp.put("brand_name", product.brand_name)
            eventProp.put("review_count", product.review_count)
            eventProp.put("review_avg_rating", product.review_avg_rating)
            Datadive.getInstance().logEvent("click_shoppingbag_in_product", eventProp)
            /******************************/

            Cart.orders.add(product)
        }

        val btnSubmitReview = findViewById<Button>(R.id.btn_submit_review)
        val reviewRating = findViewById<RatingBar>(R.id.rb_review_rating)
        val reviewBody = findViewById<EditText>(R.id.et_review_text)
        btnSubmitReview.setOnClickListener {
            if (reviewRating.rating > 0) {
                val strReviewBody = if (reviewBody.text.isEmpty()) "" else reviewBody.text.toString()
                /******************************/
                val eventProp = JSONObject()
                eventProp.put("itemid", product.id)
                eventProp.put("product_name", product.name)
                eventProp.put("price", product.price)
                eventProp.put("category_code", category.id)
                eventProp.put("category_name", category.name)
                eventProp.put("brand_id", product.brand_id)
                eventProp.put("brand_name", product.brand_name)
                eventProp.put("review_rating", reviewRating.rating)
                eventProp.put("review_body", strReviewBody)
                Datadive.getInstance().logEvent("review_submitted", eventProp)
                /******************************/
            } else {
                Toast.makeText(this, "평점을 먼저 선택하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        /******************************/
        val eventProp = JSONObject()
        eventProp.put("itemid", product.id)
        eventProp.put("product_name", product.name)
        eventProp.put("price", product.price)
        eventProp.put("category_code", category.id)
        eventProp.put("category_name", category.name)
        eventProp.put("brand_id", product.brand_id)
        eventProp.put("brand_name", product.brand_name)
        eventProp.put("review_count", product.review_count)
        eventProp.put("review_avg_rating", product.review_avg_rating)
        Datadive.getInstance().logEvent("view_product", eventProp)
        /******************************/
    }
}