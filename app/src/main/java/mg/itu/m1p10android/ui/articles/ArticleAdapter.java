package mg.itu.m1p10android.ui.articles;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private final List<Article> mValues;

    public ArticleAdapter(List<Article> mValues) {
        super();
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(FragmentArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        holder.article = mValues.get(position);
        holder.articleImage.setImageResource(R.drawable.article_placeholder);
        holder.articleTitre.setText(mValues.get(position).getTitre());
        holder.articleDescr.setText(mValues.get(position).getDescr());
        holder.articleType.setText(mValues.get(position).getType().label);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
