package com.example.sampleapp;

import static com.example.sampleapp.MainActivity.showCart;
import static com.example.sampleapp.RegisterActivity.setSignUpFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager productImagesViewPager;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cutTedPrice;
    private ImageView codIndicator;
    private TextView tvCodIndicator;
    private TabLayout viewPagerIndicator;
    private LinearLayout couponRedemptionLayout;
    private Button couponRedeemBtn;
    private TextView rewardTitle;
    private TextView rewardBody;

    // Product Description

    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private TextView productOnlyDescriptionBody;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;

    // Product Description

    ////////  RATING LAYOUT
    private LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;

    ////////  RATING LAYOUT

    private Button buyNowBtn;
    private LinearLayout addToCartBtn;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static FloatingActionButton addToWishlistBtn;
    private FirebaseFirestore firebaseFirestore;

    //////// Coupon Dialog
    public static TextView couponTitle;
    public static TextView couponExpiryDate;
    public static TextView couponBody;
    private static RecyclerView couponsRecyclerView;
    private static LinearLayout selectedCoupon;

    //////// Coupon Dialog

    private Dialog signInDialog;
    private FirebaseUser currentUser;
    private String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tabLayout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        couponRedeemBtn = findViewById(R.id.coupon_redemption_btn);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniView);
        totalRatingMiniView = findViewById(R.id.total_ratings_miniView);
        productPrice = findViewById(R.id.product_price);
        cutTedPrice = findViewById(R.id.cut_price);
        codIndicator = findViewById(R.id.cod_indicator_imageview);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating = findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        couponRedemptionLayout = findViewById(R.id.coupon_redemption_layout);
//        initialRating = -1;

        firebaseFirestore = FirebaseFirestore.getInstance();
        List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");

        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                                productImages.add(documentSnapshot.get("product_image_" + x).toString());
                            }
                            ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                            productImagesViewPager.setAdapter(productImagesAdapter);

                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                            totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ")ratings");
                            productPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                            cutTedPrice.setText("Rs." + documentSnapshot.get("cutTed_price").toString() + "/-");

                            if ((boolean) documentSnapshot.get("COD")) {
                                codIndicator.setVisibility(View.VISIBLE);
                                tvCodIndicator.setVisibility(View.VISIBLE);
                            } else {
                                codIndicator.setVisibility(View.INVISIBLE);
                                tvCodIndicator.setVisibility(View.INVISIBLE);
                            }
                            rewardTitle.setText((long) documentSnapshot.get("free_coupons") + documentSnapshot.get("free_coupon_title").toString());
                            rewardBody.setText(documentSnapshot.get("free_coupon_body").toString());

                            if ((boolean) documentSnapshot.get("use_tab_layout")) {
                                productDetailsTabsContainer.setVisibility(View.VISIBLE);
                                productDetailsOnlyContainer.setVisibility(View.GONE);
                                productDescription = documentSnapshot.get("product_description").toString();
                                productOtherDetails = documentSnapshot.get("product_other_details").toString();

                                for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {

                                    productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));

                                    for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {

                                        productSpecificationModelList.add(new ProductSpecificationModel(1,
                                                documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(),
                                                documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()));

                                    }
                                }

                            } else {
                                productDetailsTabsContainer.setVisibility(View.GONE);
                                productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                                productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                            }

                            totalRatings.setText((long) documentSnapshot.get("total_ratings") + " ratings");

                            for (int x = 0; x < 5; x++) {
                                TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                                rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_star")));

                                ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                            }

                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));
                            averageRating.setText(documentSnapshot.get("average_rating").toString());
                            productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));

                            if (DBQueries.wishlist.size() == 0) {
                                DBQueries.loadWishList(ProductDetailsActivity.this);
                            }

                            if (DBQueries.wishlist.contains(productID)) {
                                ALREADY_ADDED_TO_WISHLIST = true;
                            } else {
                                ALREADY_ADDED_TO_WISHLIST = false;
                            }

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {

                    Map<String, Object> addProduct = new HashMap<>();
                    addProduct.put("product_ID_" + String.valueOf(DBQueries.wishlist.size()), productID);
//                    addProduct.put("list_size", (long) DBQueries.wishlist.size() + 1);


                    if (ALREADY_ADDED_TO_WISHLIST) {
                        ALREADY_ADDED_TO_WISHLIST = false;
                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
                    } else {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.black));
                    }
                }
            }
        });


        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ////////  RATING LAYOUT

        rateNowContainer = findViewById(R.id.rate_now_container);

        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        setRating(starPosition);
                    }
                }
            });
        }

        ////////  RATING LAYOUT

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    /////todo: add to cart
                }
            }
        });

        ///////  coupon Dialog

        Dialog checkCouponPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCouponPriceDialog.setContentView(R.layout.coupon_redeem_dialog);
        checkCouponPriceDialog.setCancelable(true);
        checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerview);
        couponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupons_recyclerview);
        selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupon);
        couponTitle = checkCouponPriceDialog.findViewById(R.id.coupon_title);
        couponBody = checkCouponPriceDialog.findViewById(R.id.coupon_body);
        couponExpiryDate = checkCouponPriceDialog.findViewById(R.id.coupon_validity);

        TextView originalPrice = checkCouponPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback", "till 2nd June, 2016", "GET 20% CASHBACK on any product above Rs. 200/- and below Rs. 30000/-"));
        rewardModelList.add(new RewardModel("Discount", "till 2nd June, 2016", "GET 20% CASHBACK on any product above Rs. 200/- and below Rs. 30000/-"));
        rewardModelList.add(new RewardModel("Buy 1 get 1 free", "till 2nd June, 2016", "GET 20% CASHBACK on any product above Rs. 200/- and below Rs. 30000/-"));
        rewardModelList.add(new RewardModel("Cashback", "till 2nd June, 2016", "GET 20% CASHBACK on any product above Rs. 200/- and below Rs. 30000/-"));
        rewardModelList.add(new RewardModel("Discount", "till 2nd June, 2016", "GET 20% CASHBACK on any product above Rs. 200/- and below Rs. 30000/-"));
        rewardModelList.add(new RewardModel("Buy 1 get 1 free", "till 2nd June, 2016", "GET 20% CASHBACK on any product above Rs. 200/- and below Rs. 30000/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList, true);
        couponsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });

        ///////  coupon Dialog

        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCouponPriceDialog.show();
            }
        });

        /////// signIn dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn_dialog);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn_dialog);

        Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(v -> {
            SignInFragment.disableCloseBtn = true;
            SignUpFragment.disableCloseBtn = true;
            signInDialog.dismiss();
            setSignUpFragment = false;
            startActivity(registerIntent);
        });

        dialogSignUpBtn.setOnClickListener(v -> {
            SignInFragment.disableCloseBtn = true;
            SignUpFragment.disableCloseBtn = true;
            signInDialog.dismiss();
            setSignUpFragment = true;
            startActivity(registerIntent);
        });
        /////// signIn dialog

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            couponRedemptionLayout.setVisibility(View.GONE);
        } else {
            couponRedemptionLayout.setVisibility(View.VISIBLE);
        }
    }

    public static void showDialogRecyclerView() {
        if (couponsRecyclerView.getVisibility() == View.GONE) {
            couponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    private void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#BEBEBE")));
            if (x <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFBB00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}