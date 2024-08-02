package edu.phuong.asm_restapi.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.phuong.asm_restapi.R;
import edu.phuong.asm_restapi.adapter.CategoryAdapter;
import edu.phuong.asm_restapi.adapter.ProductAdapter;
import edu.phuong.asm_restapi.model.Category;
import edu.phuong.asm_restapi.model.Product;
import edu.phuong.asm_restapi.util.server;


public class HomeFragment extends Fragment {

    private RecyclerView categoryRecyclerView, productRecyclerView, popularRecyclerView;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private List<Category> categoryList;
    private List<Product> productList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Window w = getActivity().getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        categoryRecyclerView = view.findViewById(R.id.recylerView_category);
        productRecyclerView = view.findViewById(R.id.recylerView_product);
        popularRecyclerView = view.findViewById(R.id.recylerView_popular);


        categoryList = new ArrayList<>();
        productList = new ArrayList<>();

        setupCategoryRecyclerView();
        setupProductRecyclerView();
        setupPopularProductRecyclerView();

        fetchCategories();
        fetchProducts();


        return view;
    }

    private void setupCategoryRecyclerView() {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productAdapter = new ProductAdapter(getContext(), productList);
        productRecyclerView.setAdapter(productAdapter);
    }

    private void setupPopularProductRecyclerView() {
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productAdapter = new ProductAdapter(getContext(), productList);
        popularRecyclerView.setAdapter(productAdapter);
    }

    private void fetchCategories() {
        String url = server.getCategory; // Adjust this URL
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        categoryList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject categoryObject = response.getJSONObject(i);
                                int id = categoryObject.getInt("id");
                                String name = categoryObject.getString("name");
                                String image = categoryObject.getString("image");

                                Category category = new Category(id, name, image);
                                categoryList.add(category);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error fetching categories", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void fetchProducts() {
        String url = server.getProduct; // Adjust this URL
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject productObject = response.getJSONObject(i);
                                int id = productObject.getInt("id");
                                String name = productObject.getString("name");
                                int price = productObject.getInt("price");
                                String image = productObject.getString("image");
                                String description = productObject.getString("description");
                                Product product = new Product(id, name, price, image, description);
                                productList.add(product);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error fetching categories", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


}
