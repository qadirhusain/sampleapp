package com.example.sampleapp;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>{

    private List<WishlistModel> wishlistModelList;
    private Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {
//        String productID = wishlistModelList.get(position).getProductID();
        String resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        long freeCoupons = wishlistModelList.get(position).getFreeCoupons();
        String rating = wishlistModelList.get(position).getRating();
        long totalRatings = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cutTedPrice = wishlistModelList.get(position).getCutTedPrice();
        Boolean paymentMethod = wishlistModelList.get(position).getCOD();
//        Boolean inStock = wishlistModelList.get(position).getInStock();

        holder.setData(/*productID,*/resource, title, freeCoupons, rating, totalRatings, productPrice, cutTedPrice, paymentMethod/*, position, inStock*/);

//        if (lastPosition < position) {
//            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
//            holder.itemView.setAnimation(animation);
//            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private ImageView couponIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cutTedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_wishlist_image);
            productTitle = itemView.findViewById(R.id.product_wishlist_title);
            freeCoupons = itemView.findViewById(R.id.free_coupon_wishlist);
            couponIcon = itemView.findViewById(R.id.coupon_icon_wishlist);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings_wishlist);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_wishlist_price);
            cutTedPrice = itemView.findViewById(R.id.cutTed_wishlist_price);
            paymentMethod = itemView.findViewById(R.id.payment_method);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

        private void setData(String resource, String title, long freeCouponsNo, String averageRate, long totalRatingsNo, String price, String cutTedPriceValue, Boolean COD/*, int index, Boolean inStock*/) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);
            productTitle.setText(title);
            if (freeCouponsNo != 0 /*&& inStock*/) {
                couponIcon.setVisibility(View.VISIBLE);
                if (freeCouponsNo == 1) {
                    freeCoupons.setText("free " + freeCouponsNo + " coupon");
                } else {
                    freeCoupons.setText("free " + freeCouponsNo + " coupons");
                }
            } else {
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            LinearLayout linearLayout = (LinearLayout) rating.getParent();
//            if (inStock){
//                rating.setVisibility(View.VISIBLE);
//                totalRatings.setVisibility(View.VISIBLE);
//                productPrice.setTextColor(Color.parseColor("#000000"));
//                cutTedPrice.setVisibility(View.VISIBLE);
//                linearLayout.setVisibility(View.VISIBLE);

                rating.setText(averageRate);
                totalRatings.setText("(" + totalRatingsNo + ")ratings");
                productPrice.setText("Rs." + price + "/-");
                cutTedPrice.setText("Rs." + cutTedPriceValue + "/-");
            if (COD) {
                    paymentMethod.setVisibility(View.VISIBLE);
                } else {
                    paymentMethod.setVisibility(View.INVISIBLE);
                }

//                if (COD) {
//                    paymentMethod.setVisibility(View.VISIBLE);
//                } else {
//                    paymentMethod.setVisibility(View.INVISIBLE);
//                }

//            } else {
//                linearLayout.setVisibility(View.INVISIBLE);
//                rating.setVisibility(View.INVISIBLE);
//                totalRatings.setVisibility(View.INVISIBLE);
//                productPrice.setText("Out of Stock");
//                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.btnREDLight));
//                cuttedPrice.setVisibility(View.INVISIBLE);
//                paymentMethod.setVisibility(View.INVISIBLE);
//            }

            if (wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(v -> {
//                if (!ProductDetailsActivity.running_wishlist_querry) {
//                    ProductDetailsActivity.running_wishlist_querry = true;
//                    DBqueries.removeFromWishList(index, itemView.getContext());
//                }
                Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
            });

            itemView.setOnClickListener(v -> {
                Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
//                productDetailsIntent.putExtra("PRODUCT_ID"/*, productID*/);
                itemView.getContext().startActivity(productDetailsIntent);
            });
        }
    }
}
