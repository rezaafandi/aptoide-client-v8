package cm.aptoide.pt.view.recycler.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import cm.aptoide.pt.AptoideApplication;
import cm.aptoide.pt.R;
import cm.aptoide.pt.ads.MinimalAd;
import cm.aptoide.pt.crashreports.CrashReport;
import cm.aptoide.pt.networking.image.ImageLoader;
import cm.aptoide.pt.search.model.SearchAdResult;
import cm.aptoide.pt.view.recycler.displayable.GridAdDisplayable;
import com.jakewharton.rxbinding.view.RxView;
import java.text.DecimalFormat;

/**
 * Created by neuro on 20-06-2016.
 */
public class GridAdWidget extends Widget<GridAdDisplayable> {

  private TextView name;
  private ImageView icon;
  private TextView rating;

  public GridAdWidget(View itemView) {
    super(itemView);
  }

  @Override protected void assignViews(View itemView) {
    name = itemView.findViewById(R.id.name);
    icon = itemView.findViewById(R.id.icon);
    rating = itemView.findViewById(R.id.rating_label);
  }

  @Override public void bindView(GridAdDisplayable displayable, int position) {
    MinimalAd pojo = displayable.getPojo();
    name.setText(pojo.getName());

    final FragmentActivity context = getContext();
    ImageLoader.with(context)
        .load(pojo.getIconPath(), icon);

    compositeSubscription.add(RxView.clicks(itemView)
        .subscribe(v -> {
          getFragmentNavigator().navigateTo(AptoideApplication.getFragmentProvider()
              .newAppViewFragment(new SearchAdResult(pojo), displayable.getTag()), true);
        }, throwable -> CrashReport.getInstance()
            .log(throwable)));

    try {
      DecimalFormat oneDecimalFormatter = new DecimalFormat("0.0");
      rating.setText(oneDecimalFormatter.format(pojo.getStars()));
    } catch (Exception e) {
      rating.setText(R.string.appcardview_title_no_stars);
    }
  }
}
