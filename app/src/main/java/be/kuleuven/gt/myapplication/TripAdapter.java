package be.kuleuven.gt.myapplication;
import static android.content.Intent.getIntent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        requestImages(trip.getImageUrl(), holder);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View trip;
        public ImageView tripImage;
        public ViewHolder(View tripView) {
            super(tripView);
            trip = (View) tripView;
            tripImage = tripView.findViewById(R.id.tripImage);
            trip.setOnClickListener(this);
        }
        public void onClick(View tripView) {
            TextView tripName = (TextView) tripView.findViewById(R.id.tripName);
            TextView tripStartDate = (TextView) tripView.findViewById(R.id.tripStartDate);
            TextView tripEndDate = (TextView) tripView.findViewById(R.id.tripEndDate);
            Trip trip = new Trip(
                    tripName.getText().toString(),
                    tripStartDate.getText().toString(),
                    tripEndDate.getText().toString()
            );
            Intent intent = new Intent(tripView.getContext(), TripLogActivity.class);
            intent.putExtra("Trip", trip);
            tripView.getContext().startActivity(intent);

        }}


        private void requestImages(String image_URL, ViewHolder holder) {
            RequestQueue requestQueue = Volley.newRequestQueue(holder.trip.getContext());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    image_URL ,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            processJSONResponse2(response, holder);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    } );
            requestQueue.add(jsonArrayRequest );

        }


        private void processJSONResponse2(JSONArray response, ViewHolder holder) {
            // Retrieve the image URL from the JSON response
            try {
                JSONObject jsonObject = response.getJSONObject(0);
                if (!jsonObject.isNull("image")) {
                    String imageUrl = jsonObject.getString("image");

                    // Load the image using Picasso
                    Picasso.get()
                            .load(imageUrl)
                            .into(holder.tripImage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }






}