package mg.itu.m1p10android.ui.articles;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuHostHelper;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.http.ArticleHttp;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;
import mg.itu.m1p10android.databinding.FragmentArticleListBinding;

public class ArticleFragment extends Fragment implements MenuProvider {

    private FragmentArticleListBinding binding;

    private ArticleViewModel viewModel;

    public ArticleFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MenuHost host = this.getActivity();
        host.addMenuProvider(this, getViewLifecycleOwner());
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
        binding.refresher.setOnRefreshListener(() -> {
            viewModel.fetchAll(this::populateRecyclerView);
            binding.refresher.setRefreshing(false);
        });
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.fetchAll(this::populateRecyclerView);

        return root;

    }


    private void populateRecyclerView(Article[] articles){
        binding.list.setAdapter(new ArticleAdapter(articles));
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.article_menu,menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {
        MenuProvider.super.onPrepareMenu(menu);

        MenuItem menuItem = menu.getItem(0);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.search(s, response -> populateRecyclerView(response));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}