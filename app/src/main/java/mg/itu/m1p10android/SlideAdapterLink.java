package mg.itu.m1p10android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapterLink extends RecyclerView.Adapter<SlideAdapterLink.SlideViewHolder>{
    private List<SlideItemLink> slideItems;
    private ViewPager2 viewPager2;

    public SlideAdapterLink(List<SlideItemLink> slideItems, ViewPager2 viewPager2) {
        this.slideItems = slideItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideAdapterLink.SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideAdapterLink.SlideViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SlideAdapterLink.SlideViewHolder holder, int position) {
//        holder.setImage(slideItems.get(position));
        SlideItemLink slideItemLink = slideItems.get(position);

        // Charger l'image à partir du lien
        Glide.with(holder.itemView.getContext())
                .load(slideItemLink.getLink())
                .into(holder.imageView);

//        if (position == slideItems.size() - 2){
//            viewPager2.post(runnable);
//        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SlideItem slideItem){


            imageView.setImageResource(slideItem.getImage());
        }
    }

    public Runnable runnable =new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };
}
