package mg.itu.m1p10android.ui.articles;

import android.graphics.Color;
import android.os.Bundle;
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

import com.squareup.picasso.Picasso;

import mg.itu.m1p10android.BuildConfig;
import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleDetailsBinding;
import mg.itu.m1p10android.models.MyApp;

public class ArticleDetailsFragment extends Fragment {

    private FragmentArticleDetailsBinding binding;
    private Integer id;
    private ImageView hero;
    private Article article;
    private ArticleDetailsViewModel viewModel;

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.fetchById(id, this::displayArticle);
        Picasso.get()
                .load(String.join("/", BuildConfig.ApiUrl,"article",id.toString(),"cover"))
                .placeholder(R.drawable.loading_placeholder)
                .error(R.drawable.error_placeholder)
                .into(binding.hero);
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