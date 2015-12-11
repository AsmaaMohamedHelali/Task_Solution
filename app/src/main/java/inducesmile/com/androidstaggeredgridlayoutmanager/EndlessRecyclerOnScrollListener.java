package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerOnScrollListener extends
		RecyclerView.OnScrollListener {
	public static String TAG = EndlessRecyclerOnScrollListener.class
			.getSimpleName();

	private int previousTotal = 0;
	private boolean loading = true;
	private int visibleThreshold = 5;
	int[] firstVisibleItems = null;
	int  pastVisibleItems,visibleItemCount, totalItemCount;

	private int current_page = 0;

	private StaggeredGridLayoutManager mLinearLayoutManager;

	public EndlessRecyclerOnScrollListener(
			StaggeredGridLayoutManager linearLayoutManager) {
		this.mLinearLayoutManager = linearLayoutManager;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);

		visibleItemCount = recyclerView.getChildCount();
		totalItemCount = mLinearLayoutManager.getItemCount();
		firstVisibleItems = mLinearLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
		if(firstVisibleItems != null && firstVisibleItems.length > 0) {
			pastVisibleItems = firstVisibleItems[0];
		}
		if (loading) {
			if (totalItemCount > previousTotal) {
				loading = false;
				previousTotal = totalItemCount;
			}
		}
		if (!loading
				&& (totalItemCount - visibleItemCount) <= (pastVisibleItems + visibleThreshold)) {
			current_page+=10;

			onLoadMore(current_page);

			loading = true;
		}
	}

	public abstract void onLoadMore(int current_page);
}