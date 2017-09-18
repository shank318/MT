package code.moneytap.features;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import code.moneytap.R;
import code.moneytap.realmmodels.RealmPage;
import code.moneytap.utils.ImageUtil;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by shank on 21/07/17.
 */

public class SearchPagesAdapter extends RealmBasedRecyclerViewAdapter<RealmPage, SearchPagesAdapter.ViewHolder> {

    Context mContext;


    public SearchPagesAdapter(Context context,
                              RealmResults<RealmPage> realmResults,
                              boolean automaticUpdate,
                              boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
        mContext = context;

    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search
                , parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        RealmPage page = realmResults.get(position);
        holder.title.setText(page.getTitle());
        if(page.getDescription()!=null){
            holder.description.setText(page.getDescription());
        }
        if(page.getImageUrl()!=null)
         ImageUtil.setImage(mContext, page.getImageUrl(), holder.profileImageView);
//        holder.itemView.setOnClickListener(view ->
//                ((ContactsActivity)mContext).showContactDetail(contact));

    }

    public class ViewHolder extends RealmViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.image)
        ImageView profileImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
