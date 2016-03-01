package com.lee.luweather.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lee.luweather.R;
import com.lee.luweather.base.MyApplication;
import com.lee.luweather.model.SelectCity;
import com.lee.luweather.util.DbHelper;
import com.lee.luweather.util.StatusBarUtils;
import com.lee.luweather.util.Utils;

import java.util.List;

public class CityManagerActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    MyApplication application;
    List<SelectCity> selectCityList;
    RecyclerAdapter recyclerAdapter;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        application = (MyApplication) getApplication();
        application.pushTask(this);

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSelectCity();
    }

    private void initData() {
        selectCityList = DbHelper.getInstance().getAllSelectCity();
    }

    private void initView() {

        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimaryDark));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("城市管理");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(CityManagerActivity.this, CitySelectActivity.class);
                CityManagerActivity.this.startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_selectcity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(selectCityList);
        recyclerView.setAdapter(recyclerAdapter);

        //set swipe delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                viewHolder.itemView.setVisibility(View.GONE);
                SelectCity selectCity = selectCityList.get(position);
                DbHelper.getInstance().deleteCity(selectCity);
                Utils.showToast(CityManagerActivity.this,
                        String.format("%s is removed successfully", selectCity.getCityname()));
                recyclerAdapter.notifyItemRemoved(position);
                refreshSelectCity();
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
        }).attachToRecyclerView(recyclerView);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.swipe_color_1,R.color.swipe_color_2,R.color.swipe_color_3,R.color.swipe_color_4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSelectCity();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemTextView;

        public RecyclerItemViewHolder(final View parent, TextView itemTextView) {
            super(parent);
            mItemTextView = itemTextView;
        }

        public static RecyclerItemViewHolder newInstance(View parent) {
            TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextView);

            return new RecyclerItemViewHolder(parent, itemTextView);
        }

        public void setItemText(CharSequence text) {
            mItemTextView.setText(text);
        }

    }

    static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerItemViewHolder> {
        private List<SelectCity> mItemList;

        public RecyclerAdapter(List<SelectCity> itemList) {
            mItemList = itemList;
        }

        @Override
        public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_city, parent, false);
            return RecyclerItemViewHolder.newInstance(view);
        }

        @Override
        public void onBindViewHolder(RecyclerItemViewHolder viewHolder, int position) {
            String itemText = mItemList.get(position).getCityname();
            viewHolder.setItemText(itemText);
        }

        @Override
        public int getItemCount() {
            return mItemList == null ? 0 : mItemList.size();
        }

    }

    /**
     *
     */
    private void refreshSelectCity() {
        initData();
        recyclerAdapter = new RecyclerAdapter(selectCityList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

        if (null != swipeRefreshLayout) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
