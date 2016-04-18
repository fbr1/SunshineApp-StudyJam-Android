package com.android.sqsoft.sunshine.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.sqsoft.sunshine.ForecastFragment.OnListFragmentInteractionListener;
import com.android.sqsoft.sunshine.R;
import com.android.sqsoft.sunshine.entities.DayForecast;

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
        holder.mIdView.setText((mValues.get(position)).getDate().toString());
        holder.mContentView.setText(String.valueOf((mValues.get(position)).getTmax()));

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
        public final TextView mIdView;
        public final TextView mContentView;
        public DayForecast mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
