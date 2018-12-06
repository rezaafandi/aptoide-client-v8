package cm.aptoide.pt.promotions;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import cm.aptoide.pt.app.DownloadModel;
import java.util.List;

public class PromotionsAdapter extends RecyclerView.Adapter<GeneralPromotionAppsViewHolder> {

  static final int UPDATE = 0;
  static final int DOWNLOAD = 1;
  static final int DOWNLOADING = 2;
  static final int INSTALL = 3;
  static final int CLAIM = 4;
  static final int CLAIMED = 5;
  static final int DOWNGRADE = 6;

  private List<PromotionViewApp> appsList;
  private PromotionsViewHolderFactory viewHolderFactory;

  public PromotionsAdapter(List<PromotionViewApp> appsList,
      PromotionsViewHolderFactory viewHolderFactory) {
    this.appsList = appsList;
    this.viewHolderFactory = viewHolderFactory;
  }

  @Override
  public GeneralPromotionAppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return viewHolderFactory.createViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(GeneralPromotionAppsViewHolder holder, int position) {
    holder.setApp(appsList.get(position));
  }

  @Override public int getItemViewType(int position) {
    PromotionViewApp app = appsList.get(position);
    int state;
    if (app.isClaimed()) {
      return CLAIMED;
    } else {
      DownloadModel downloadModel = app.getDownloadModel();

      if (downloadModel.isDownloading()) {
        // TODO: 12/6/18 parse downloading states;
        return DOWNLOADING;
      } else {
        switch (downloadModel.getAction()) {
          case DOWNGRADE:
            state = DOWNGRADE;
            break;
          case INSTALL:
            state = INSTALL;
            break;
          case OPEN:
            state = CLAIM;
            break;
          case UPDATE:
            state = UPDATE;
            break;
          default:
            throw new IllegalArgumentException("Invalid type of download action");
        }
        return state;
      }
    }
  }

  @Override public int getItemCount() {
    return appsList.size();
  }

  public void setPromotionApp(PromotionViewApp promotionViewApp) {
    this.appsList.add(promotionViewApp);
    notifyDataSetChanged();
  }
}
