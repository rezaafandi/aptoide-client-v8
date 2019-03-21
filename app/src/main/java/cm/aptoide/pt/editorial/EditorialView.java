package cm.aptoide.pt.editorial;

import android.support.v7.graphics.Palette;
import cm.aptoide.pt.presenter.View;
import cm.aptoide.pt.reactions.data.ReactionType;
import java.util.List;
import rx.Observable;

/**
 * Created by D01 on 27/08/2018.
 */

public interface EditorialView extends View {

  void showLoading();

  void hideLoading();

  Observable<Void> retryClicked();

  Observable<EditorialEvent> appCardClicked(EditorialViewModel model);

  Observable<EditorialEvent> actionButtonClicked();

  void populateView(EditorialViewModel editorialViewModel);

  void showError(EditorialViewModel.Error error);

  void showDownloadModel(EditorialDownloadModel model);

  Observable<Boolean> showRootInstallWarningPopup();

  void openApp(String packageName);

  Observable<EditorialDownloadEvent> installButtonClick(EditorialViewModel editorialViewModel);

  Observable<EditorialDownloadEvent> pauseDownload(EditorialViewModel editorialViewModel);

  Observable<EditorialDownloadEvent> resumeDownload(EditorialViewModel editorialViewModel);

  Observable<EditorialDownloadEvent> cancelDownload(EditorialViewModel editorialViewModel);

  Observable<Void> isViewReady();

  Observable<ScrollEvent> placeHolderVisibilityChange();

  void removeBottomCardAnimation();

  void addBottomCardAnimation();

  Observable<EditorialEvent> mediaContentClicked();

  void managePlaceHolderVisibity();

  Observable<Palette.Swatch> paletteSwatchExtracted();

  void applyPaletteSwatch(Palette.Swatch swatch);

  Observable<EditorialEvent> mediaListDescriptionChanged();

  void manageMediaListDescriptionAnimationVisibility(EditorialEvent editorialEvent);

  void setMediaListDescriptionsVisible(EditorialEvent editorialEvent);

  Observable<Boolean> handleMovingCollapse();

  Observable<Boolean> showDowngradeMessage();

  void showDowngradingMessage();

  Observable<Void> reactionsButtonClicked();

  void setReactions(ReactionType userReaction, List<ReactionType> reactions,
      String numberOfReactions);

  void showReactionsPopup(String cardId);

  Observable<ReactionEvent> reactionClicked();

  void setUserReaction(ReactionType reaction);

  void showLogInDialog();

  Observable<Void> snackLogInClick();

  void showErrorToast();
}
