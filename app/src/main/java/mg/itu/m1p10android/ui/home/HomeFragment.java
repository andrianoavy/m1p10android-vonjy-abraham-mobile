package mg.itu.m1p10android.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import mg.itu.m1p10android.GpsTracker;
import mg.itu.m1p10android.R;
import mg.itu.m1p10android.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private GpsTracker gpsTracker;
    private double latitude;
    private double longitude;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String unencodedHtml = String.format(getResources().getString(R.string.youtube_webview),"bGNtRsjjysY" );
        Log.d("Webview", unencodedHtml);
        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                Base64.NO_PADDING);
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        binding.webview.setBackgroundColor(Color.argb(1, 255, 255, 255));
        binding.webview.loadData(encodedHtml, "text/html", "base64");

        final TextView textView = binding.textHome;
        binding.button.setOnClickListener(view -> {
            getLocation(root);
            Toast.makeText(getActivity(), String.format("%f, %f", longitude, latitude), Toast.LENGTH_SHORT).show();
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getLocation(View view) {
        gpsTracker = new GpsTracker(this.getActivity());
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
    }
}