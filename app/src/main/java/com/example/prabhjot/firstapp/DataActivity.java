package com.example.prabhjot.firstapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {


    private static String REQUEST_URL = "https://api.androidhive.info/json/movies.json";
    private List<Item> itemsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;

    //VerticalLines needs to be declared globally because we are accessing it in different functions
    private RecyclerView.ItemDecoration VerticalLines;
    public boolean isViewAList=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //We create the object of Vertical Lines in onCreate() because we need to make
        //changes to the same object every time we are switching from List View to Grid View.
        VerticalLines = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL);

        // Start the AsyncTask to fetch the data
        MyAsyncTask task = new MyAsyncTask();
        task.execute(REQUEST_URL);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.grid_or_list:
                isViewAList=!isViewAList;
                invalidateOptionsMenu();
                recyclerView.setLayoutManager(isViewAList ? new LinearLayoutManager(this) : new GridLayoutManager(this, 2));

                mAdapter = new ItemAdapter(itemsList,isViewAList,DataActivity.this);
                recyclerView.setAdapter(mAdapter);



                //The presence of Vertical Lines will depend upon whether it is a Grid Layout or List Layout

                //If it is not a list view (!isViewAList), we add vertical lines.
                if(!isViewAList) {
                    recyclerView.addItemDecoration(VerticalLines);
                }

                //If it is a list view (isViewAList), we remove the vertical lines previously added.
                else
                {
                    recyclerView.removeItemDecoration(VerticalLines);
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(!isViewAList)
        {
            menu.findItem(R.id.grid_or_list).setTitle("List");
        }

        else
        {
            menu.findItem(R.id.grid_or_list).setTitle("Grid");

        }
        return super.onPrepareOptionsMenu(menu);
    }

    private class MyAsyncTask extends AsyncTask<String, Void, List<Item>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {

            progressDialog = new ProgressDialog(DataActivity.this);
            progressDialog.setMessage("loading...");
            progressDialog.show();

        }

        @Override
        protected List<Item> doInBackground(String... urls) {
            //Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Item> result = QueryUtils.fetchProductData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Item> data) {



            itemsList=data;

            if(progressDialog!= null && progressDialog.isShowing() )
                progressDialog.dismiss();


            RecyclerView.LayoutManager mLayoutManager;
            RecyclerView.ItemDecoration HorizontalLines;

            mLayoutManager = new LinearLayoutManager(DataActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new ItemAdapter(itemsList,isViewAList,DataActivity.this);
            recyclerView.setAdapter(mAdapter);

            //Horizontal Lines will always be there. So adding them permanently after setting the adapter
            HorizontalLines = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);

            recyclerView.addItemDecoration(HorizontalLines);

        }
    }

    


}