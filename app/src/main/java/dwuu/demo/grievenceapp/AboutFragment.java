package dwuu.demo.grievenceapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        String aboutContent = getString(R.string.about_content);

        TextView textViewAbout = view.findViewById(R.id.textViewAbout);
        textViewAbout.setText(getFormattedText(aboutContent));

        return view;
    }

    private SpannableString getFormattedText(String text) {
        SpannableString spannableString = new SpannableString(text);

        String[] boldRanges = {"Easy and Convenient:", "Transparent and Categorized:", "Filter and Prioritize:", "Timely Resolution:", "Student-Friendly Updates:", "University Recognition:"};
        for (String boldRange : boldRanges) {
            int startIndex = text.indexOf(boldRange);
            int endIndex = startIndex + boldRange.length();
            spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, 0);
        }

        return spannableString;
    }
}