package cn.zhangls.decoration

import android.annotation.SuppressLint
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
import com.zhangls.decoration.linear.EndOffsetItemDecoration
import com.zhangls.decoration.linear.StartOffsetItemDecoration


/**
 * @author zhangls
 */
class MainActivity : AppCompatActivity() {

    private val linearLayoutManager by lazy { LinearLayoutManager(this) }
    private val gridLayoutManager by lazy { GridLayoutManager(this, 4) }
    private lateinit var recyclerView: RecyclerView
    private val drawable by lazy { ContextCompat.getDrawable(this, R.drawable.divider) }
    private val drawable2 by lazy { ContextCompat.getDrawable(this, R.drawable.divider_2) }
    private val dividerDecoration by lazy { StartOffsetItemDecoration(drawable!!, 32) }
    private val gridDecoration by lazy { GridDividerItemDecoration(drawable!!, drawable2!!, 4) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.rgLayout)
        radioGroup.setOnCheckedChangeListener { _, i ->
            if (i == R.id.rbLinear) {
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.addItemDecoration(dividerDecoration)
                recyclerView.removeItemDecoration(gridDecoration)
            } else {
                recyclerView.layoutManager = gridLayoutManager
                recyclerView.addItemDecoration(gridDecoration)
                recyclerView.removeItemDecoration(dividerDecoration)
            }
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(dividerDecoration)
        recyclerView.adapter = RecyclerAdapter()
    }

    private inner class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            view.setBackgroundColor(ContextCompat.getColor(parent.context, android.R.color.darker_gray))
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = "Item $position"
        }

        override fun getItemCount(): Int {
            return 49
        }
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView as TextView
    }
}
