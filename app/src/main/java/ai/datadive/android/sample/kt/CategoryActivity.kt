package ai.datadive.android.sample.kt

import ai.datadive.api.Datadive
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        if (!intent.hasExtra("position")) {
            Toast.makeText(this, "전달된 카테고리가 없음.", Toast.LENGTH_SHORT).show()
        }

        var categoryPosition = intent.getIntExtra("position", 0)
        var categories: List<Category> = getProductMenu(this)
        var category = categories[categoryPosition]

        val listView = findViewById<ListView>(R.id.lv_products)
        listView.adapter = ProductListAdapter(this, category.products)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("categoryPosition", categoryPosition)
            intent.putExtra("productPosition", position)
            startActivityForResult(intent, RESULT_OK)
        }

        /******************************/
        val eventProp = JSONObject()
        eventProp.put("category_code", category.id)
        eventProp.put("category_name", category.name)
        Datadive.getInstance().logEvent("view_category", eventProp)
        /******************************/
    }
}