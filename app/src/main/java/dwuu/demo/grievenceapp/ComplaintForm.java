package dwuu.demo.grievenceapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ComplaintForm implements Parcelable {
    private String Email,FullName,StudentId,PhoneNumber,Subject,Description,Location,WitnessName,WitnessId,WitnessNum,Resolution;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getWitnessName() {
        return WitnessName;
    }

    public void setWitnessName(String witnessName) {
        WitnessName = witnessName;
    }

    public String getWitnessId() {
        return WitnessId;
    }

    public void setWitnessId(String witnessId) {
        WitnessId = witnessId;
    }

    public String getWitnessNum() {
        return WitnessNum;
    }

    public void setWitnessNum(String witnessNum) {
        WitnessNum = witnessNum;
    }

    public String getResolution() {
        return Resolution;
    }

    public void setResolution(String resoltion) {
        Resolution = resoltion;
    }

    public ComplaintForm(){
    }
    public ComplaintForm(String Email,String FullName, String StudentId, String PhoneNumber, String Subject, String Description, String Location, String WitnessName, String WitnessId, String WitnessNum, String Resolution){
        this.Email=Email;
        this.FullName=FullName;
        this.StudentId=StudentId;
        this.PhoneNumber=PhoneNumber;
        this.Subject=Subject;
        this.Description=Description;
        this.Location=Location;
        this.WitnessName=WitnessName;
        this.WitnessId=WitnessId;
        this.WitnessNum=WitnessNum;
        this.Resolution=Resolution;
    }

    protected ComplaintForm(Parcel in) {
        Email = in.readString();
        FullName = in.readString();
        StudentId = in.readString();
        PhoneNumber = in.readString();
        Subject = in.readString();
        Description = in.readString();
        Location = in.readString();
        WitnessName = in.readString();
        WitnessId = in.readString();
        WitnessNum = in.readString();
        Resolution = in.readString();
    }

    public static final Creator<ComplaintForm> CREATOR = new Creator<ComplaintForm>() {
        @Override
        public ComplaintForm createFromParcel(Parcel in) {
            return new ComplaintForm(in);
        }

        @Override
        public ComplaintForm[] newArray(int size) {
            return new ComplaintForm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Email);
        parcel.writeString(FullName);
        parcel.writeString(StudentId);
        parcel.writeString(PhoneNumber);
        parcel.writeString(Subject);
        parcel.writeString(Description);
        parcel.writeString(Location);
        parcel.writeString(WitnessName);
        parcel.writeString(WitnessId);
        parcel.writeString(WitnessNum);
        parcel.writeString(Resolution);

    }

}
