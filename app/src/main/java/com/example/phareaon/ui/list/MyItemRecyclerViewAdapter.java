package com.example.phareaon.ui.list;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.phareaon.DetailPhare;
import com.example.phareaon.MainActivity;
import com.example.phareaon.R;
import com.example.phareaon.ui.list.ItemFragment.OnListFragmentInteractionListener;
import com.example.phareaon.ui.list.dummy.DummyContent.DummyItem;

import java.util.List;

import static androidx.core.content.ContextCompat.getDrawable;
import static java.lang.String.valueOf;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Resources resources = MainActivity.getContext().getResources();
        final int drawableID = resources.getIdentifier(mValues.get(position).filename, "drawable",
                MainActivity.getContext().getPackageName());

        holder.mItem = mValues.get(position);
        holder.mIdView.setImageDrawable(getDrawable(MainActivity.getContext(),drawableID));
        holder.mContentView.setText(mValues.get(position).name);
        holder.mRegionView.setText(mValues.get(position).region);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(mContext, DetailPhare.class);
                details.putExtra("nomPhare", mValues.get(position).name);
                details.putExtra("regionPhare", mValues.get(position).region);
                details.putExtra("imagePhare", mValues.get(position).filename);
                details.putExtra("constructionPhare", valueOf(mValues.get(position).construction));
                details.putExtra("lattitudePhare", valueOf(mValues.get(position).latLng.latitude));
                details.putExtra("longitudePhare", valueOf(mValues.get(position).latLng.longitude));
                details.putExtra("clignotementPhare", valueOf(mValues.get(position).period));
                mContext.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIdView;
        public final TextView mContentView;
        public final TextView mRegionView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (ImageView) view.findViewById(R.id.itemPhare);
            mContentView = (TextView) view.findViewById(R.id.content);
            mRegionView = (TextView) view.findViewById(R.id.RegionListe);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
