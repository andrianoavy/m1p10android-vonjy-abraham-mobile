package mg.itu.m1p10android.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import mg.itu.m1p10android.GpsTracker;
import mg.itu.m1p10android.R;
import mg.itu.m1p10android.SlideAdapter;
import mg.itu.m1p10android.SlideItem;
import mg.itu.m1p10android.databinding.ActivityMainBinding;
import mg.itu.m1p10android.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private GpsTracker gpsTracker;
    private double latitude;
    private double longitude;

    public HomeFragment() {
    }


    private AppBarConfiguration mAppBarConfiguration;
//    private ActivityMainBinding binding;

    ViewPager2 viewPager2;

    private Handler slideHandler = new Handler();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //slide image
        viewPager2 = root.findViewById(R.id.viewPager);
        List<SlideItem> slideItem = new ArrayList<>();
        slideItem.add(new SlideItem(R.drawable.slide1));
//        slideItem.add(new SlideItem(R.drawable.slide2));
        slideItem.add(new SlideItem(R.drawable.slide3));
        slideItem.add(new SlideItem(R.drawable.slide4));
        slideItem.add(new SlideItem(R.drawable.slide5));
        slideItem.add(new SlideItem(R.drawable.slide6));

        viewPager2.setAdapter(new SlideAdapter(slideItem,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer) ;

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slideHandler.removeCallbacks(slideeRunnable);
                slideHandler.postDelayed(slideeRunnable,2000);
            }
        });


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

    private Runnable slideeRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideeRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideeRunnable,2000);
    }


}