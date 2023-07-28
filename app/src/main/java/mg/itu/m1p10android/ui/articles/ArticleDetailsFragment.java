package mg.itu.m1p10android.ui.articles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleDetailsBinding;

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

        viewModel = new ViewModelProvider(this).get(ArticleDetailsViewModel.class);
        id = new Integer(ArticleDetailsFragmentArgs.fromBundle(getArguments()).getIdArticle());
        viewModel.fetchById(id, this::displayArticle);
        return root;
    }

    private void displayArticle(Article a) {

        article = a;
        binding.artDetTitre.setText(article.getTitre());
        binding.artDetDesc.setText(article.getDescr());

    }

}