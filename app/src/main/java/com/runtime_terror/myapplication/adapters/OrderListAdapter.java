package com.runtime_terror.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtime_terror.myapplication.KitchenTableOrder;
import com.runtime_terror.myapplication.R;
import com.runtime_terror.myapplication.models.Food;
import com.runtime_terror.myapplication.models.FoodOrder;
import com.runtime_terror.myapplication.models.Order;

import java.util.Date;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private List<? extends Order> ordersList;
    private Context context;

    public OrderListAdapter(Context context, List<? extends Order> ordersList) {
        this.ordersList = ordersList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull MyViewHolder holder, final int position) {
        holder.setTableNumber(ordersList.get(position).getTableNumber());
        holder.setElapsedTime(ordersList.get(position).getRequestDate());
        holder.setCardColor(ordersList.get(position).getColor());

        if(ordersList.get(position).isClickable())
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context.getApplicationContext(), KitchenTableOrder.class));
                }
            });

        if(ordersList.get(position).isClosable())
            holder.closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ordersList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        else holder.closeButton.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tableNumber;
        TextView elapsedTime;
        ImageButton closeButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView;
            tableNumber = itemView.findViewById(R.id.table_number);
            elapsedTime = itemView.findViewById(R.id.elapsed_time);
            closeButton = itemView.findViewById(R.id.close_button);

        }

        public void setTableNumber(int number) {
            tableNumber.setText(Integer.toString(number));
        }

        public void setElapsedTime(Date timestamp) {
            int timeDifferenceInMills = (int)(new Date().getTime() - timestamp.getTime())/1000/60;
            String time = timeDifferenceInMills + 1 + (timeDifferenceInMills > 1 ? " mins ago" : " min ago");

            elapsedTime.setText(time);
        }

        public void setCardColor(int cardColor) {
            int color = context.getResources().getColor(cardColor);
            container.setBackgroundColor(color);
        }

    }
}
