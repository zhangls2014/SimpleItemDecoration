package cn.zhangls.decoration

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangls.decoration.grid.GridDividerItemDecoration
import com.zhangls.decoration.linear.DividerItemDecoration


/**
 * @author zhangls
 */
class MainActivity : AppCompatActivity() {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var dividerDecoration: DividerItemDecoration? = null
    private var gridDecoration: GridDividerItemDecoration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawable = ContextCompat.getDrawable(this, R.drawable.divider)
        val drawable2 = ContextCompat.getDrawable(this, R.drawable.divider_2)

        dividerDecoration = DividerItemDecoration(drawable!!, 32)
        gridDecoration = GridDividerItemDecoration(drawable, drawable2!!, 24, 24, 4)
        linearLayoutManager = LinearLayoutManager(this)
        gridLayoutManager = GridLayoutManager(this, 4)

        val radioGroup = findViewById<RadioGroup>(R.id.rgLayout)
        radioGroup.setOnCheckedChangeListener { _, i ->
            if (i == R.id.rbLinear) {
                recyclerView!!.layoutManager = linearLayoutManager
                recyclerView!!.addItemDecoration(dividerDecoration!!)
                recyclerView!!.removeItemDecoration(gridDecoration!!)
            } else {
                recyclerView!!.layoutManager = gridLayoutManager
                recyclerView!!.addItemDecoration(gridDecoration!!)
                recyclerView!!.removeItemDecoration(dividerDecoration!!)
            }
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.addItemDecoration(dividerDecoration!!)
        recyclerView!!.adapter = RecyclerAdapter()
    }

    private inner class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = "Item $position"
            holder.text.setBackgroundColor(
                    ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
        }

        override fun getItemCount(): Int {
            return 45
        }
    }

    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var text: TextView = itemView as TextView

    }
}
