package mg.itu.m1p10android.ui.articles;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import mg.itu.m1p10android.data.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    public final ImageView articleImage;
    public final TextView articleTitre;
    public final TextView articleDescr;
    public final TextView articleType;
    public Article article;


    public ArticleViewHolder(FragmentArticleBinding binding) {
        super(binding.getRoot());
        articleImage = binding.articleImage;
        articleTitre = binding.articleTitre;
        articleDescr = binding.articleDescr;
        articleType = binding.articleType;
        itemView.setOnClickListener( view ->{
            ArticleFragmentDirections.NavArticleListToDetails listToDetails = ArticleFragmentDirections.navArticleListToDetails(article.getId());
            Navigation.findNavController(view).navigate(listToDetails);
        });
    }


}