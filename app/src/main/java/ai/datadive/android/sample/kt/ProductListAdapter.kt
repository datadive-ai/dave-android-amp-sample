package ai.datadive.android.sample.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProductListAdapter(val context: Context, val itemList: Array<Product>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, null)

        val title = view.findViewById<TextView>(R.id.tv_title)
        val body = view.findViewById<TextView>(R.id.tv_body)

        val item = itemList[position]
        title.text = item.name
        body.text = "${item.price} 원"

        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

}