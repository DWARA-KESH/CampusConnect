package dwuu.demo.grievenceapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "onReceive";


    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private DatabaseReference complaintsRef;
    private ComplaintAdapter complaintAdapter;

    public HomeFragment() {

    }

    public static HomeFragment newInstance( String email) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String[] email_text = {null};
        if (getArguments() != null) {
            email_text[0] = getArguments().getString("email");
        }
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        complaintsRef = FirebaseDatabase.getInstance().getReference().child("complaints");

        retrieveComplaintsFromFirebase(email_text[0]);

        FloatingActionButton btnNewMail = rootView.findViewById(R.id.btnNewComplaint);
        btnNewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), GrievanceForm.class);
                intent.putExtra("email_str",email_text[0]);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void retrieveComplaintsFromFirebase(String userEmail) {
        complaintsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ComplaintForm> complaints = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ComplaintForm complaint = snapshot.getValue(ComplaintForm.class);

                    if (complaint != null && complaint.getEmail() != null && complaint.getEmail().equals(userEmail)) {
                        complaints.add(complaint);
                    }
                }
                complaintAdapter = new ComplaintAdapter(getContext(), complaints);
                recyclerView.setAdapter(complaintAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving complaints from Firebase: " + databaseError.getMessage());
            }
        });
    }
}