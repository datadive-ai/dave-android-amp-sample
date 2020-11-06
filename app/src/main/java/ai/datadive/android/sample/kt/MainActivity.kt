package ai.datadive.android.sample.kt

import ai.datadive.api.Datadive
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /******************************/
        Datadive.getInstance().initialize(this, "dac284af6cf94f96aafa75d854a96718")
        Datadive.getInstance().setUserId("TESTUSERID")
        /******************************/

        // 상품 정보 Json 로드
        var categories: List<Category> = getProductMenu(this)

        val listView = findViewById<ListView>(R.id.lv_categories)
        listView.adapter = CategoryListAdapter(this, categories.toTypedArray())
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("position", position)
            startActivityForResult(intent, RESULT_OK)
        }
    }

    override fun onStart() {
        super.onStart()

        /******************************/
        Datadive.getInstance().logEvent("view_main")
        /******************************/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivityForResult(intent, RESULT_OK)
            }
            R.id.it_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivityForResult(intent, RESULT_OK)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}