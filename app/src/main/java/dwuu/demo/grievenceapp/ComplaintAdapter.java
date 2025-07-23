package dwuu.demo.grievenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private Context context;
    private List<ComplaintForm> complaintList;

    public ComplaintAdapter(Context context, List<ComplaintForm> complaintList) {
        this.context=context;
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.complaint_item, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        ComplaintForm complaintForm = complaintList.get(position);
        holder.bind(complaintForm);
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewSubject;
        private TextView textViewSender;
        private TextView textViewStatus;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
            textViewSender = itemView.findViewById(R.id.textViewSender);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }

        public void bind(ComplaintForm complaintForm) {
            textViewSubject.setText(complaintForm.getSubject());
            textViewSender.setText(complaintForm.getFullName());
            textViewStatus.setText("Complaint Filed");
        }
    }
}

