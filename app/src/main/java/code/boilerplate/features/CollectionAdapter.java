package code.boilerplate.features;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cleartrip.cleartrip.R;
import cleartrip.cleartrip.networking.responses.pojo.Collection;
import cleartrip.cleartrip.utility.ImageUtil;
import code.boilerplate.pojo.FilmLocation;

/**
 * Created by shank on 24/08/17.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>{
    private List<FilmLocation> collections;
    private Context mContext;


    public CollectionAdapter(Context context, List<FilmLocation> collections){
        this.collections = collections;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView imageView;

        @BindView(R.id.overlay)
        View view;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.sub_title)
        TextView subTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_collections,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        FilmLocation item = collections.get(position);
//        holder.view.setBackgroundColor(Color.parseColor(item.getVibrantColor()));
//        ImageUtil.setImage(mContext,item.getImage(),holder.imageView);
//        holder.title.setText(item.getCollectionName());
//        holder.subTitle.setText(item.getCountText());
    }

    @Override
    public int getItemCount(){
        return collections.size();
    }


}
