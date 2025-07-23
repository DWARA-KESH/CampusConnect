package dwuu.demo.grievenceapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Timer;
import java.util.TimerTask;

import dwuu.demo.grievenceapp.R;

public class ImageContainerFragment extends Fragment {

    private ImageView imageView;
    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private int currentIndex = 0;
    private Handler handler;
    private Runnable runnable;
    private static final long DELAY_MS = 3000; // Delay between image changes
    private static final long PERIOD_MS = 3000; // Time period for changing images

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_container, container, false);
        imageView = rootView.findViewById(R.id.imageView);
        handler = new Handler(Looper.getMainLooper());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        startImageSlider();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopImageSlider();
    }

    private void startImageSlider() {
        runnable = new Runnable() {
            public void run() {
                if (currentIndex == images.length) {
                    currentIndex = 0;
                }
                imageView.setImageResource(images[currentIndex]);
                currentIndex++;
                handler.postDelayed(this, DELAY_MS);
            }
        };
        handler.postDelayed(runnable, DELAY_MS);
    }

    private void stopImageSlider() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
