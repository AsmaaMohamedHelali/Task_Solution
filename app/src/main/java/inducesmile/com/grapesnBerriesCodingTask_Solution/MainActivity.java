package inducesmile.com.grapesnBerriesCodingTask_Solution;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static String SERVICE_URL = "http://grapesnberries.getsandbox.com/products?";
    private static final String COUNT = "count";
    private static final String FROM = "from";
    private static final String PRODUCT_DESC = "productDescription";
    private static final String PRODUCT_IMAGE = "image";
    private static final String PRODUCT_IMAGE_URL = "url";
    private static final String PRODUCT_PRICE = "price";
    private ProgressDialog pDialog;
    private int from=1,count=10;
    private List<ItemObjects> listViewItems=null;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private  SolventRecyclerViewAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        listViewItems = new ArrayList<ItemObjects>();
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        rcAdapter = new SolventRecyclerViewAdapter(MainActivity.this, listViewItems);
        recyclerView.setAdapter(rcAdapter);
        new GetProducts().execute();
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(gaggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                from+=10;
                new GetProducts().execute();

            }

        });


    }




    private class GetProducts extends AsyncTask<Void, Void, List<ItemObjects>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//             Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected List<ItemObjects> doInBackground(Void... arg0) {
            ServiceHandler servicehandler = new ServiceHandler();
            String jsonStr = servicehandler.makeServiceCall(SERVICE_URL + COUNT + "="+count+"&" + FROM + "="+from, ServiceHandler.GET);
            if(jsonStr!=null){
                Bitmap bitmap=null;
                JSONArray jsonarr;
                JSONObject jsonobj, imageobj;
                String pr_desc,pr_price;
                try {
                    jsonarr = new JSONArray(jsonStr);
                    if (jsonarr.length() != 0) {
                        for (int i = 0; i < jsonarr.length(); i++) {
                            jsonobj = jsonarr.getJSONObject(i);
                            pr_desc = jsonobj.getString(PRODUCT_DESC);
                            pr_price = jsonobj.getString(PRODUCT_PRICE);
                            imageobj = jsonobj.getJSONObject(PRODUCT_IMAGE);

                            try {

                                 bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageobj.getString(PRODUCT_IMAGE_URL)).getContent());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            listViewItems.add(new ItemObjects(pr_desc, bitmap, pr_price+"$"));


                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return listViewItems;
        }

        @Override
        protected void onPostExecute(List<ItemObjects> result) {
            super.onPostExecute(result);



            if (pDialog.isShowing()) {
                pDialog.dismiss();

            }
            else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Can't reload Network Error", Toast.LENGTH_SHORT).show();

            }
            gaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            rcAdapter= new SolventRecyclerViewAdapter(MainActivity.this, listViewItems);
            recyclerView.setAdapter(rcAdapter);



        }
    }
    @Override
    public void onResume() {
        super.onResume();
        gaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rcAdapter= new SolventRecyclerViewAdapter(MainActivity.this, listViewItems);
        recyclerView.setAdapter(rcAdapter);

    }

    }

