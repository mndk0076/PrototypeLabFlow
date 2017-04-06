package vxestate.prototypelabflow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kristina on 2017-04-06.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
    ArrayList<Appointment> arrayList = new ArrayList<>();

    public AppointmentAdapter(ArrayList<Appointment> arrayList){
        this.arrayList = arrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_appointment_request, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Date.setText(arrayList.get(position).getDate());
        holder.Time.setText(arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Date, Time;

        public MyViewHolder(View itemView) {
            super(itemView);

            Date = (TextView)itemView.findViewById(R.id.date);
            Time = (TextView)itemView.findViewById(R.id.myAppointment);
        }
    }
}
