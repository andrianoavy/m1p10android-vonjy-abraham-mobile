package mg.itu.m1p10android.ui.articles;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import mg.itu.m1p10android.BuildConfig;
import mg.itu.m1p10android.R;
import mg.itu.m1p10android.data.models.Article;
import mg.itu.m1p10android.databinding.FragmentArticleBinding;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private final Article[] mValues;


    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault());
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
        Picasso.get()
                .load(String.join("/", BuildConfig.ApiUrl,"article",mValues[position].getId().toString(), "cover"))
                .placeholder(R.drawable.loading_placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.articleImage);
        holder.articleTitre.setText(mValues[position].getTitre());
        holder.articleDescr.setText(mValues[position].getDescr());
        holder.articleType.setText(mValues[position].getDateModif().format(formatter));

    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }
}
