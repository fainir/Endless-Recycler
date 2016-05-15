package winit.mini.endlessrecycler.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import winit.mini.endlessrecycler.Core.AppConstants;
import winit.mini.endlessrecycler.Interfaces.OnLoadMoreListener;
import winit.mini.endlessrecycler.R;
import winit.mini.endlessrecycler.Models.Lottery;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LotteriesDataAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static List<Lottery> lotteries;
    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem;
    private int totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context context;
    private boolean isErrorMessage = false;

    public LotteriesDataAdapter(List<Lottery> lotteriesInput, RecyclerView recyclerView, Context context) {
        lotteries = lotteriesInput;
        this.context = context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return lotteries.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
            vh = new LotteryViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LotteryViewHolder) {
            final Lottery singleLottery = lotteries.get(position);

            Picasso.with(context)
                    .load(AppConstants.Http.GET_COVER + singleLottery.getImageId() + "/")
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(((LotteryViewHolder) holder).coverImage);

            ((LotteryViewHolder) holder).title.setText(singleLottery.getPrize());

            ((LotteryViewHolder) holder).company.setText(context.getString(R.string.hosted) + " " + singleLottery.getHost());

            ((LotteryViewHolder) holder).link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String linkString = singleLottery.getUrl();
                    if (!linkString.startsWith("http://") && !linkString.startsWith("https://")) {
                        linkString = "http://" + linkString;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkString));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                }
            });
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            if (isErrorMessage) {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
                ((ProgressViewHolder) holder).textView.setVisibility(View.VISIBLE);
                isErrorMessage = false;
            }
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return lotteries.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void clear() {
        lotteries.clear();
        notifyDataSetChanged();

    }

    public void showErrorMessage() {
        int lastItemPosition = lotteries.size() - 1;
        isErrorMessage = true;
        lotteries.remove(lastItemPosition);
        notifyItemRemoved(lastItemPosition);
        lotteries.add(null);
        notifyItemInserted(lastItemPosition);
        notifyItemRangeChanged(lastItemPosition, lastItemPosition + 1);
    }

    public static class LotteryViewHolder extends RecyclerView.ViewHolder {

        public ImageView coverImage;
        public TextView title;
        public TextView company;
        public Button link;

        public LotteryViewHolder(View v) {
            super(v);
            coverImage = (ImageView) v.findViewById(R.id.imageView);
            title = (TextView) v.findViewById(R.id.prize_text);
            company = (TextView) v.findViewById(R.id.host_text);
            link = (Button) v.findViewById(R.id.link);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //this method calls when you click the card_item
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;
        public TextView textView;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            textView = (TextView) v.findViewById(R.id.errorTextView);
        }
    }

}