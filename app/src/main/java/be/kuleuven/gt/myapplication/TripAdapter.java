package be.kuleuven.gt.myapplication;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import be.kuleuven.gt.model.Trip;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View tripView = layoutInflater.inflate(R.layout.trip_view, parent, false);
        ViewHolder myViewHolder = new ViewHolder(tripView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(TripAdapter.ViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        //name of the trip
        ((TextView) holder.trip.findViewById(R.id.tripName))
                .setText(trip.getName());
        //duration of the trip, start (add on end)
        ((TextView) holder.trip.findViewById(R.id.tripStartDate))
                .setText(trip.getStartDate());
        //trip picture - implement
//        ((TextView) holder.trip.findViewById(R.id.orderQuantity))
//                .setText(Integer.toString(coffeeOrder.getQuantity()));
        ((TextView) holder.trip.findViewById(R.id.tripEndDate))
                .setText(trip.getEndDate());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View trip;
        public ViewHolder(View tripView) {
            super(tripView);
            trip = (View) tripView;
            trip.setOnClickListener(this);
        }

        public void onClick(View tripView) {
            Intent intent = new Intent(tripView.getContext(), TripLogActivity.class);
            tripView.getContext().startActivity(intent);

        }
    }
}