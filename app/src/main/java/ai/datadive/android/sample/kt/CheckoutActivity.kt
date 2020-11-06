package ai.datadive.android.sample.kt

import ai.datadive.api.Datadive
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val orders = Cart.orders

        if (orders.size <= 0) return

        var total_price = 0
        for (product in orders) {
            total_price += product.price
        }
        Log.i("Datadive", "Total price: $total_price")

        val btnCheckout = findViewById<Button>(R.id.btn_checkout)
        btnCheckout.setOnClickListener {
            /******************************/
            // 결제할 전체 products 배열 생성
            var products = JSONArray()
            for (product in orders) {
                var prodProperty = JSONObject()
                prodProperty.put("itemid", product.id)
                prodProperty.put("product_name", product.name)
                prodProperty.put("price", product.price)
                prodProperty.put("quantity", 1)
                products.put(prodProperty)

                total_price += product.price
            }
            // 이벤트 생성
            var eventProp = JSONObject()
            eventProp.put("orderserial", UUID.randomUUID().toString())
            eventProp.put("products", products)
            eventProp.put("order_total_price", total_price)
            eventProp.put("order_total_count", orders.size)
            eventProp.put("payment_total_price", total_price)
            eventProp.put("payment_method", "신용카드")
            eventProp.put("delivery_fee", 0)
            eventProp.put("total_discount", 0)
            Datadive.getInstance().logEvent("order_complete", eventProp)
            /******************************/
            Cart.orders.clear()
            Toast.makeText(this, "주문이 결제되었습니다.", Toast.LENGTH_LONG)

        }

    }
}