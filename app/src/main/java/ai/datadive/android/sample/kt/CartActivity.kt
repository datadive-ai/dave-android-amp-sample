package ai.datadive.android.sample.kt

import ai.datadive.api.Datadive
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val orders = Cart.orders

        if (orders.size <= 0) return

        val listView = findViewById<ListView>(R.id.lv_products_in_cart)
        listView.adapter = ProductListAdapter(this, orders.toTypedArray())

        var total_price = 0
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
        eventProp.put("products", products)
        eventProp.put("order_total_price", total_price)
        eventProp.put("order_total_count", orders.size)
        Datadive.getInstance().logEvent("view_userinfo", eventProp)
        /******************************/

        val textTotalPrice = findViewById<TextView>(R.id.tv_total_price_in_cart)
        textTotalPrice.text = "총 $total_price 원"

        val btnStartOrder = findViewById<Button>(R.id.btn_start_order)
        btnStartOrder.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivityForResult(intent, RESULT_OK)
        }
    }
}