package mg.itu.m1p10android.ui.articles;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.TypeArticle;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private final Article[] mValues;

    public ArticleAdapter(Article[] mValues) {
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

        holder.article = mValues[position];
        holder.articleImage.setImageResource(R.drawable.article_placeholder);
        holder.articleTitre.setText(mValues[position].getTitre());
        holder.articleDescr.setText(mValues[position].getDescr());
        holder.articleType.setText(TypeArticle.DESTINATION.label);

    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }
}
