package com.android.sqsoft.sunshine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sqsoft.sunshine.ForecastFragment.OnListFragmentInteractionListener;
import com.android.sqsoft.sunshine.R;
import com.android.sqsoft.sunshine.Utility;
import com.android.sqsoft.sunshine.entities.DayForecast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DayForecast} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class DayForecastRecyclerViewAdapter extends RecyclerView.Adapter<DayForecastRecyclerViewAdapter.ViewHolder> {

    private final List<DayForecast> mValues;
    private final OnListFragmentInteractionListener mListener;

    public DayForecastRecyclerViewAdapter(List<DayForecast> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIconView.setImageResource(Utility.getIconResourceForWeatherCondition(holder.mItem.getWeather().get(0).getId()));
        holder.mDateView.setText(DateFormat.getDateInstance().format(holder.mItem.getDate()));
        holder.mDescriptionView.setText(String.valueOf(holder.mItem.getWeather().get(0).getDescription()));
        holder.mLowTempView.setText(Utility.formatTemperature(holder.mItem.getTmin()));
        holder.mHighTempView.setText(Utility.formatTemperature(holder.mItem.getTmax()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIconView;
        public final TextView mDateView;
        public final TextView mDescriptionView;
        public final TextView mHighTempView;
        public final TextView mLowTempView;

        public DayForecast mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIconView = (ImageView) view.findViewById(R.id.list_item_icon);
            mDateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            mDescriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            mHighTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            mLowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}
