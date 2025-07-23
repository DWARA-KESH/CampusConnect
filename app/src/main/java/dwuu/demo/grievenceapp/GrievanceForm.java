package dwuu.demo.grievenceapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GrievanceForm extends AppCompatActivity {

    private EditText FullNameEditText;
    private EditText StudentIdEditText;
    private EditText PhoneNumberEditText;
    private Spinner SubjectSpinner;
    private EditText DescriptionEditText;
    private EditText LocationEditText;
    private EditText WitnessNameEditText;
    private EditText WitnessIdEditText;
    private EditText WitnessPhoneNumEditText;
    private EditText ResolutionEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_form);
        //style for name
        CardView cardView = findViewById(R.id.fullNameView);
        ViewCompat.setElevation(cardView, getResources().getDimension(R.dimen.cardview_elevation));
        cardView.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));
        //style for id
        CardView cardView1 = findViewById(R.id.idView);
        ViewCompat.setElevation(cardView1, getResources().getDimension(R.dimen.cardview_elevation));
        cardView1.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));
        //style for description
        CardView cardView2 = findViewById(R.id.descriptionView);
        ViewCompat.setElevation(cardView2, getResources().getDimension(R.dimen.cardview_elevation));
        cardView2.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));
        //style for location
        CardView cardView3 = findViewById(R.id.locationView);
        ViewCompat.setElevation(cardView3, getResources().getDimension(R.dimen.cardview_elevation));
        cardView3.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));

        CardView cardView4 = findViewById(R.id.numberView);
        ViewCompat.setElevation(cardView4,getResources().getDimension(R.dimen.cardview_elevation));
        cardView4.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));

        CardView cardView5 = findViewById(R.id.witnessNameView);
        ViewCompat.setElevation(cardView5,getResources().getDimension(R.dimen.cardview_elevation));
        cardView5.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));

        CardView cardView6 = findViewById(R.id.witnessIdView);
        ViewCompat.setElevation(cardView6,getResources().getDimension(R.dimen.cardview_elevation));
        cardView6.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));

        CardView cardView7 = findViewById(R.id.witnessNumberView);
        ViewCompat.setElevation(cardView7,getResources().getDimension(R.dimen.cardview_elevation));
        cardView7.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));

        CardView cardView8 = findViewById(R.id.resolutionView);
        ViewCompat.setElevation(cardView8,getResources().getDimension(R.dimen.cardview_elevation));
        cardView8.setRadius(getResources().getDimension(R.dimen.cardview_corner_radius));

        FullNameEditText = findViewById(R.id.editTextFullName);
        StudentIdEditText = findViewById(R.id.editTextStudentID);
        PhoneNumberEditText = findViewById(R.id.editTextStudentNumber);
        SubjectSpinner = findViewById(R.id.spinnerGrievanceType);
        DescriptionEditText = findViewById(R.id.editTextDescription);
        LocationEditText = findViewById(R.id.editTextLocation);
        WitnessNameEditText = findViewById(R.id.editTextWitnessName);
        WitnessIdEditText = findViewById(R.id.editTextWitnessID);
        WitnessPhoneNumEditText = findViewById(R.id.editTextWitnessNumber);
        ResolutionEditText = findViewById(R.id.editTextResolution);
        submitButton = findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(v -> sendToService());
    }


    protected void sendToService(){

        ComplaintForm complaintForm = getFormData();
        Intent emailService = new Intent(this, EmailSenderService.class);
        emailService.putExtra("complaint_form", complaintForm);
        startService(emailService);
        finish();

        Intent serviceIntent = new Intent(this, UploadService.class);
        serviceIntent.putExtra("complaint_form",complaintForm);
        startService(serviceIntent);

    }
    private ComplaintForm getFormData(){

        String Email = getIntent().getStringExtra("email_str");
        String FullName = FullNameEditText.getText().toString();
        String StudentId = StudentIdEditText.getText().toString();
        String PhoneNumber = PhoneNumberEditText.getText().toString();
        String Subject = SubjectSpinner.getSelectedItem().toString();
        String Description = DescriptionEditText.getText().toString();
        String Location = LocationEditText.getText().toString();
        String WitnessName = WitnessNameEditText.getText().toString();
        String WitnessId = WitnessIdEditText.getText().toString();
        String WitnessNum = WitnessPhoneNumEditText.getText().toString();
        String resolution = ResolutionEditText.getText().toString();


        if (FullName.isEmpty()) FullName = null;
        if (StudentId.isEmpty()) StudentId = null;
        if (PhoneNumber.isEmpty()) PhoneNumber = null;
        if (Subject.isEmpty()) Subject = null;
        if (Description.isEmpty()) Description = null;
        if (Location.isEmpty()) Location = null;
        if (WitnessName.isEmpty()) WitnessName = null;
        if (WitnessId.isEmpty()) WitnessId = null;
        if (WitnessNum.isEmpty()) WitnessNum = null;
        if (resolution.isEmpty()) resolution = null;

        ComplaintForm complaintForm = new ComplaintForm(Email,FullName,StudentId,PhoneNumber,Subject,Description,Location,WitnessName, WitnessId, WitnessNum, resolution );
        return complaintForm;
    }

}