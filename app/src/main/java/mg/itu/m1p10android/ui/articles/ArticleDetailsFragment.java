package mg.itu.m1p10android.ui.articles;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mg.itu.m1p10android.BuildConfig;
import mg.itu.m1p10android.R;
import mg.itu.m1p10android.SlideAdapter;
import mg.itu.m1p10android.SlideAdapterLink;
import mg.itu.m1p10android.SlideItem;
import mg.itu.m1p10android.SlideItemLink;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleDetailsBinding;
import mg.itu.m1p10android.models.MyApp;

public class ArticleDetailsFragment extends Fragment {

    private FragmentArticleDetailsBinding binding;
    private Integer id;
    private String titre;
    private ImageView hero;
    private Article article;
    private ArticleDetailsViewModel viewModel;

    Context context;
    ViewPager2 viewPager2;

    private Handler slideHandler = new Handler();

    public ArticleDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.artDetContenu.setMovementMethod(LinkMovementMethod.getInstance());
        viewModel = new ViewModelProvider(this).get(ArticleDetailsViewModel.class);
        id = new Integer(ArticleDetailsFragmentArgs.fromBundle(getArguments()).getIdArticle());

        viewModel.fetchById(id, this::displayArticle);
//        titre = new Article().getTitre();
//        Log.e("Titre ---------:",titre);

//        String titre = getTitre();
//        Log.e("Titre article:-------",titre);
        //slide image
        viewPager2 = root.findViewById(R.id.viewPagerHero);
        List<SlideItemLink> slideItemLinks = new ArrayList<>();
        slideItemLinks.add(new SlideItemLink(BuildConfig.ApiUrl+"/article/7/slides/0.webp"));
        slideItemLinks.add(new SlideItemLink(BuildConfig.ApiUrl+"/article/7/slides/1.webp"));
        slideItemLinks.add(new SlideItemLink(BuildConfig.ApiUrl+"/article/7/slides/2.webp"));

        viewPager2.setAdapter(new SlideAdapterLink(slideItemLinks,viewPager2));

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


    private final Runnable slideeRunnable = new Runnable() {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.fetchById(id, this::displayArticle);
//        Picasso.get()
//                .load(String.join("/", BuildConfig.ApiUrl,"article",id.toString(),"cover"))
//                .placeholder(R.drawable.loading_placeholder)
//                .error(R.drawable.error_placeholder)
//                .into(binding.hero);
    }

    private void displayArticle(Article a) {

        article = a;

        binding.artDetTitre.setText(article.getTitre());
        binding.artDetDesc.setText(article.getDescr());

        if(a.getVideo() != null && !a.getVideo().isEmpty() && MyApp.isConnected(getContext())) {
            String unencodedHtml = String.format(getResources().getString(R.string.youtube_webview),a.getVideo() );
            Log.d("Webview", unencodedHtml);
            String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                    Base64.NO_PADDING);
            WebSettings webSettings = binding.artDetVideo.getSettings();
            webSettings.setJavaScriptEnabled(true);

            binding.artDetVideo.setBackgroundColor(Color.argb(1, 255, 255, 255));
            binding.artDetVideo.loadData(encodedHtml, "text/html", "base64");
        }
        else {
            ConstraintSet set = new ConstraintSet();
            set.clone(binding.detCl);
            binding.detCl.removeView(binding.artDetVideo);
            set.clear(R.id.art_det_video);
            set.connect(R.id.art_det_contenu, ConstraintSet.TOP, R.id.art_det_desc, ConstraintSet.BOTTOM, 32);
            set.applyTo(binding.detCl);
        }


        binding.artDetContenu.setText(a.getHtmlContenu());

    }



}