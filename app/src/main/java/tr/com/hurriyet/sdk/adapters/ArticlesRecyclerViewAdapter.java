package tr.com.hurriyet.sdk.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tr.com.hurriyet.opensourcesdk.model.response.Article;
import tr.com.hurriyet.sdk.R;



/**
 * Created by apple on 12/08/2017.
 */

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter< ArticlesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Article> mData = new ArrayList<Article>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public ArticlesRecyclerViewAdapter(Context context, ArrayList<Article> data,ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)  {
        holder.setIsRecyclable(false);

        if(0 != mData.size() &&  null != mData.get(position) && null != mData.get(position).files && 0 != mData.get(position).files.size()  && null != mData.get(position).files.get(0).fileUrl){
            Picasso.with(holder.articlePhoto.getContext()).load(mData.get(position).files.get(0).fileUrl).resize(170,170).into(holder.articlePhoto);
        }
        holder.articleDescription.setText(mData.get(position).description);


    }

    @Override
    public int getItemCount() {
        return mData.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView articleDescription;
        public ImageView articlePhoto;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.card_view);
            articleDescription = (TextView)itemView.findViewById(R.id.article_description);
            articlePhoto = (ImageView)itemView.findViewById(R.id.article_photo);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
