package uws.ah.bustrackergeo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewLatLng, textViewName;
    View view;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewLatLng = itemView.findViewById(R.id.textViewLatLng);
        textViewName = itemView.findViewById(R.id.textViewName);
        view=itemView;

    }
}
