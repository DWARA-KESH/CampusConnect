package dwuu.demo.grievenceapp;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadService extends IntentService {

    private static final String TAG = "UploadService";

    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            ComplaintForm complaintForm = intent.getParcelableExtra("complaint_form");

            if (complaintForm != null) {

                uploadComplaintFormToFirebase(complaintForm);
            } else {
                Log.e(TAG, "ComplaintForm object is null");
            }
        }
    }

    private void uploadComplaintFormToFirebase(ComplaintForm complaintForm) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("complaints");
        String complaintId = databaseReference.push().getKey();

        if (complaintId != null) {
            databaseReference.child(complaintId).setValue(complaintForm)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "ComplaintForm uploaded to Firebase successfully"))
                    .addOnFailureListener(e -> Log.e(TAG, "Error uploading ComplaintForm to Firebase: " + e.getMessage()));
        } else {
            Log.e(TAG, "Failed to get complaint ID from Firebase");
        }
    }
}
