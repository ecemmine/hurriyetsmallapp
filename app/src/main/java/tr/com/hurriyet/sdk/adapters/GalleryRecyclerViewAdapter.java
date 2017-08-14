package tr.com.hurriyet.sdk.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tr.com.hurriyet.opensourcesdk.model.response.Gallery;
import tr.com.hurriyet.sdk.R;

/**
 * Created by apple on 12/08/2017.
 */

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Gallery> mData = new ArrayList<Gallery>();


    public GalleryRecyclerViewAdapter(Context context, ArrayList<Gallery> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.article_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        if(0 != mData.size() &&  null != mData.get(position) && null != mData.get(position).files && 0 != mData.get(position).files.size()  && null != mData.get(position).files.get(0).fileUrl){
            Picasso.with(holder.articleImageView.getContext()).load(mData.get(position).files.get(0).fileUrl).resize(170,170).into(holder.articleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView articleImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            articleImageView = (ImageView) itemView.findViewById(R.id.article_image);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
