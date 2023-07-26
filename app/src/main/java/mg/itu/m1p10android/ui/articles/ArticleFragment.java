package mg.itu.m1p10android.ui.articles;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;
import mg.itu.m1p10android.databinding.FragmentArticleListBinding;

public class ArticleFragment extends Fragment {

    private FragmentArticleListBinding binding;

    SendArticleId sendId;

    public interface SendArticleId{
        void sendData(Long id);
    }

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

         View root = binding.getRoot();

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArticleAdapter adapter = new ArticleAdapter(Article.mock());
        recyclerView.setAdapter(adapter);


        return root;

    }
}