package mg.itu.m1p10android.ui.articles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.Article;
import mg.itu.m1p10android.databinding.FragmentArticleDetailsBinding;

public class ArticleDetailsFragment extends Fragment {

    private FragmentArticleDetailsBinding binding;
    private Long id;
    private ImageView hero;

    public ArticleDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        id = new Long(ArticleDetailsFragmentArgs.fromBundle(getArguments()).getIdArticle());
        Article article = Article.mockSingle(id);
        binding.artDetTitre.setText(article.getTitre());
        binding.artDetDesc.setText(article.getDescr());

        return root;
    }

    public void setId(Long id) {
        this.id = id;
    }
}