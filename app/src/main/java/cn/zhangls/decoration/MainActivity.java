package cn.zhangls.decoration;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.zhangls.decoration.grid.GridDividerItemDecoration;
import cn.zhangls.decoration.linear.DividerItemDecoration;


/**
 * @author zhangls
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private DividerItemDecoration dividerDecoration;
    private GridDividerItemDecoration gridDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
        final Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.divider_2);

        dividerDecoration = new DividerItemDecoration(drawable, 32);
        gridDecoration = new GridDividerItemDecoration(drawable, drawable2, 32, 32, 4);
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 4);

        RadioGroup radioGroup = findViewById(R.id.rgLayout);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.rbLinear) {
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.addItemDecoration(dividerDecoration);
                recyclerView.removeItemDecoration(gridDecoration);
            } else {
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.addItemDecoration(gridDecoration);
                recyclerView.removeItemDecoration(dividerDecoration);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerDecoration);
        recyclerView.setAdapter(new RecyclerAdapter());
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.text.setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }
}
