package mg.itu.m1p10android.ui.articles;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.http.ArticleHttp;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;
import mg.itu.m1p10android.databinding.FragmentArticleListBinding;

public class ArticleFragment extends Fragment {

    private FragmentArticleListBinding binding;

    private ArticleViewModel viewModel;

    public ArticleFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentArticleListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ArticleViewModel.class);

        View root = binding.getRoot();
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.fetchAll(this::populateRecyclerView);

        return root;

    }

    private void populateRecyclerView(Article[] articles){
        binding.list.setAdapter(new ArticleAdapter(articles));
    }
}