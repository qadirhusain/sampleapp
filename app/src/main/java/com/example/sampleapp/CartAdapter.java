package com.example.sampleapp;

import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
//    private int lastPosition = -1;
//    private TextView cartTotalAmount;
//    private Boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList/*, TextView cartTotalAmount, Boolean showDeleteBtn  */) {
        this.cartItemModelList = cartItemModelList;
//        this.cartTotalAmount = cartTotalAmount;
//        this.showDeleteBtn = showDeleteBtn;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.CART_TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new CartItemViewholder(cartItemView);
            case CartItemModel.CART_TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                return new CartTotalAmountViewholder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                int resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                int freeCoupons = cartItemModelList.get(position).getFreeCoupons();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cutTedPrice = cartItemModelList.get(position).getCutTedPrice();
                int offersApplied = cartItemModelList.get(position).getOffersApplied();

                ((CartItemViewholder) holder).setItemDetails(resource, title, freeCoupons, productPrice, cutTedPrice, offersApplied);
                break;

            case CartItemModel.CART_TOTAL_AMOUNT:
                String totalItems = cartItemModelList.get(position).getTotalItems();
                String totalItemPrice = cartItemModelList.get(position).getTotalItemPrice();
                String deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String savedAmount = cartItemModelList.get(position).getSavedAmount();

//                for (int x = 0; x < cartItemModelList.size(); x++) {
//                    if (cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).getInStock()) {
//                        totalItems++;
//                        totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice().replace(",", ""));
//                    }
//                }
//                if (totalItemPrice > 500) {
//                    deliveryPrice = "Free";
//                    totalAmount = totalItemPrice;
//                } else {
//                    deliveryPrice = "60";
//                    totalAmount = totalItemPrice + 60;
//                }

                ((CartTotalAmountViewholder) holder).setTotalAmount(totalItems, totalItemPrice, deliveryPrice, totalAmount, savedAmount);
                break;
            default:
                return;
        }

//        if (lastPosition < position) {
//            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
//            holder.itemView.setAnimation(animation);
//            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewholder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView freeCouponIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cutTedPrice;
        private TextView offersApplied;
        private TextView couponsApplied;
        private TextView productQuantity;
//        private LinearLayout couponRedemptionLayout;
//        private LinearLayout deleteBtn;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_image_title);
            freeCouponIcon = itemView.findViewById(R.id.free_coupon_icon);
            freeCoupons = itemView.findViewById(R.id.tv_free_coupon);
            productPrice = itemView.findViewById(R.id.product_image_price);
            cutTedPrice = itemView.findViewById(R.id.product_cutTed_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            couponsApplied = itemView.findViewById(R.id.coupons_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
        }

        private void setItemDetails(int resource, String title, int freeCouponsNo, String productPriceText, String cutTedPriceText, int offersAppliedNo) {
            productImage.setImageResource(resource);
            productTitle.setText(title);

            if (freeCouponsNo > 0) {
                freeCouponIcon.setVisibility(View.VISIBLE);
                freeCoupons.setVisibility(View.VISIBLE);
                if (freeCouponsNo == 1) {
                    freeCoupons.setText("free " + freeCouponsNo + " Coupon");
                } else {
                    freeCoupons.setText("free " + freeCouponsNo + " Coupons");
                }
            } else {
                freeCouponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }

            productPrice.setText("Rs." + productPriceText + "/-");
//                productPrice.setTextColor(Color.parseColor("#000000"));
            cutTedPrice.setText("Rs." + cutTedPriceText + "/-");
//                couponRedemptionLayout.setVisibility(View.VISIBLE);

                productQuantity.setOnClickListener(v -> {
                    Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    EditText quantityNo = quantityDialog.findViewById(R.id.quantity_no);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn = quantityDialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(v1 -> quantityDialog.dismiss());

                    okBtn.setOnClickListener(v1 -> {
                        productQuantity.setText("Qty: " + quantityNo.getText());
                        quantityDialog.dismiss();
                    });
                    quantityDialog.show();
                });

            if (offersAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + " Offers applied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }

//            } else {
//                productPrice.setText("Out of Stock");
//                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.btnREDLight));
//                cuttedPrice.setText("");
//                couponRedemptionLayout.setVisibility(View.GONE);
//                freeCoupons.setVisibility(View.INVISIBLE);
//                productQuantity.setVisibility(View.INVISIBLE);
//                couponsApplied.setVisibility(View.GONE);
//                offersApplied.setVisibility(View.GONE);
//                freeCouponIcon.setVisibility(View.INVISIBLE);
//            }

//            if (showDeleteBtn){
//                deleteBtn.setVisibility(View.VISIBLE);
//            } else {
//                deleteBtn.setVisibility(View.GONE);
//            }

//            deleteBtn.setOnClickListener(v -> {
//                if (!ProductDetailsActivity.running_cart_querry) {
//                    ProductDetailsActivity.running_cart_querry = true;
//                    DBqueries.removeFromCart(position, itemView.getContext(), cartTotalAmount);
//                }
//            });
        }

    }

    class CartTotalAmountViewholder extends RecyclerView.ViewHolder {
        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }

        private void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedAmountText) {
            totalItems.setText("Price(" + totalItemText + " items)");
            totalItemPrice.setText("Rs." + totalItemPriceText + "/-");
//            if (deliveryPriceText.equals("Free")) {
                deliveryPrice.setText(deliveryPriceText);
//            } else {
                deliveryPrice.setText("Rs." + deliveryPriceText + "/-");
//            }
            totalAmount.setText("Rs." + totalAmountText + "/-");
//            cartTotalAmount.setText("Rs." + totalAmountText + "/-");
            savedAmount.setText("You saved Rs." + savedAmountText + "/- on this order.");

//            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();

//            if (totalItemPriceText == 0){
//                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size() - 1);
//                parent.setVisibility(View.GONE);
//            } else {
//                parent.setVisibility(View.VISIBLE);
//            }
        }

    }
}
