package com.example.sampleapp;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBQueries {
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();
    public static List<String> wishlist = new ArrayList<>();

    public static void loadCategories(RecyclerView categoryRecyclerView, final Context context) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),
                                        documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(RecyclerView homePageRecyclerView, Context context, final int index, String categoryName) {

        firebaseFirestore.collection("CATEGORIES").document(categoryName.toUpperCase()).collection("TOP_DEALS")
                .orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                    for (long x = 1; x < no_of_banners + 1; x++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString()
                                                , documentSnapshot.get("banner_" + x + "_background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    lists.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString()
                                            , documentSnapshot.get("background").toString()));
                                } else if ((long) documentSnapshot.get("view_type") == 2) {

                                    List<WishlistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));

                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_full_title_" + x).toString()
                                                , (long) documentSnapshot.get("free_coupons_" + x)
                                                , documentSnapshot.get("average_rating_" + x).toString()
                                                , (long) documentSnapshot.get("total_ratings_" + x)
                                                , documentSnapshot.get("product_price_" + x).toString()
                                                , documentSnapshot.get("cutTed_price_" + x).toString()
                                                , (Boolean) documentSnapshot.get("COD_" + x)));

//                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_ID_" + x).toString()
//                                                , documentSnapshot.get("product_image_" + x).toString()
//                                                , documentSnapshot.get("product_full_title_" + x).toString()
//                                                , (long) documentSnapshot.get("free_coupons_" + x)
//                                                , documentSnapshot.get("average_rating_" + x).toString()
//                                                , (long) documentSnapshot.get("total_ratings_" + x)
//                                                , documentSnapshot.get("product_price_" + x).toString()
//                                                , documentSnapshot.get("cutted_price_" + x).toString()
//                                                , (Boolean) documentSnapshot.get("COD_" + x)
//                                                , (Boolean) documentSnapshot.get("in_stock_" + x)));
                                    }
                                    lists.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString()
                                            , documentSnapshot.get("layout_background").toString(),
                                            horizontalProductScrollModelList, viewAllProductList));


                                } else if ((long) documentSnapshot.get("view_type") == 3) {

                                    List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        gridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));

//                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_ID_" + x).toString()
//                                                , documentSnapshot.get("product_image_" + x).toString()
//                                                , documentSnapshot.get("product_full_title_" + x).toString()
//                                                , (long) documentSnapshot.get("free_coupons_" + x)
//                                                , documentSnapshot.get("average_rating_" + x).toString()
//                                                , (long) documentSnapshot.get("total_ratings_" + x)
//                                                , documentSnapshot.get("product_price_" + x).toString()
//                                                , documentSnapshot.get("cutted_price_" + x).toString()
//                                                , (Boolean) documentSnapshot.get("COD_" + x)
//                                                , (Boolean) documentSnapshot.get("in_stock_" + x)));
                                    }
                                    lists.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString()
                                            , documentSnapshot.get("layout_background").toString(),
                                            gridLayoutModelList/*, viewAllProductList*/));

                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        firebaseFirestore.collection("CATEGORIES")
//                .document(categoryName.toUpperCase())
//                .collection("TOP_DEALS").orderBy("index").get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//
//                            if ((long) documentSnapshot.get("view_type") == 0) {
//                                List<SliderModel> sliderModelList = new ArrayList<>();
//                                long no_of_banners = (long) documentSnapshot.get("no_of_banners");
//                                for (long x = 1; x < no_of_banners + 1; x++) {
//                                    sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString()
//                                            , documentSnapshot.get("banner_" + x + "_background").toString()));
//                                }
//                                lists.get(index).add(new HomePageModel(0, sliderModelList));
//                            } else if ((long) documentSnapshot.get("view_type") == 1) {
//                                lists.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString()
//                                        , documentSnapshot.get("background").toString()));
//                            } else if ((long) documentSnapshot.get("view_type") == 2) {
//
//                                List<WishlistModel> viewAllProductList = new ArrayList<>();
//                                List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//                                long no_of_products = (long) documentSnapshot.get("no_of_products");
//                                for (long x = 1; x < no_of_products + 1; x++) {
//                                    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
//                                            , documentSnapshot.get("product_image_" + x).toString()
//                                            , documentSnapshot.get("product_title_" + x).toString()
//                                            , documentSnapshot.get("product_subtitle_" + x).toString()
//                                            , documentSnapshot.get("product_price_" + x).toString()));
//
//                                    viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_ID_" + x).toString()
//                                            , documentSnapshot.get("product_image_" + x).toString()
//                                            , documentSnapshot.get("product_full_title_" + x).toString()
//                                            , (long) documentSnapshot.get("free_coupons_" + x)
//                                            , documentSnapshot.get("average_rating_" + x).toString()
//                                            , (long) documentSnapshot.get("total_ratings_" + x)
//                                            , documentSnapshot.get("product_price_" + x).toString()
//                                            , documentSnapshot.get("cutted_price_" + x).toString()
//                                            , (Boolean) documentSnapshot.get("COD_" + x)
//                                            , (Boolean) documentSnapshot.get("in_stock_" + x)));
//                                }
//                                lists.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList, viewAllProductList));
//
//
//                            } else if ((long) documentSnapshot.get("view_type") == 3) {
//                                List<HorizontalProductScrollModel> GridLayoutModelList = new ArrayList<>();
//                                long no_of_products = (long) documentSnapshot.get("no_of_products");
//                                for (long x = 1; x < no_of_products + 1; x++) {
//                                    GridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
//                                            , documentSnapshot.get("product_image_" + x).toString()
//                                            , documentSnapshot.get("product_title_" + x).toString()
//                                            , documentSnapshot.get("product_subtitle_" + x).toString()
//                                            , documentSnapshot.get("product_price_" + x).toString()));
//                                }
//                                lists.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), GridLayoutModelList));
//                            }
//                        }
//                        HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
//                        homePageRecyclerView.setAdapter(homePageAdapter);
//                        homePageAdapter.notifyDataSetChanged();
//                        HomeFragment.swipeRefreshLayout.setRefreshing(false);
//                    } else {
//                        String error = task.getException().getMessage();
//                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    public static void loadWishList(final Context context/*, Dialog dialog, final Boolean loadProductData*/) {
//        wishlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                                wishlist.add(task.getResult().get("product_ID_" + x).toString());

//                                if (DBqueries.wishlist.contains(ProductDetailsActivity.productID)) {
//                                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
//                                    if (ProductDetailsActivity.addToWishlistBtn != null) {
//                                        ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.btnREDLight));
//                                    }
//                                } else {
//                                    if (ProductDetailsActivity.addToWishlistBtn != null) {
//                                        ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
//                                    }
//                                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
//                                }

//                                if (loadProductData) {
//                                    wishlistModelList.clear();
//                                    String productID = task.getResult().get("product_ID_" + x).toString();
//                                    firebaseFirestore.collection("PRODUCTS").document(productID)
//                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//
//                                                        wishlistModelList.add(new WishlistModel(productID
//                                                                , task.getResult().get("product_image_1").toString()
//                                                                , task.getResult().get("product_title").toString()
//                                                                , (long) task.getResult().get("free_coupons")
//                                                                , task.getResult().get("average_rating").toString()
//                                                                , (long) task.getResult().get("total_ratings")
//                                                                , task.getResult().get("product_price").toString()
//                                                                , task.getResult().get("cutted_price").toString()
//                                                                , (Boolean) task.getResult().get("COD")
//                                                                , (Boolean) task.getResult().get("in_stock")));
//
//                                                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
//                                                    }
//                                                }
//                                            });
//                                }
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
//                        dialog.dismiss();
                    }
                });
    }

}
