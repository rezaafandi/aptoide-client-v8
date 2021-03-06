package cm.aptoide.pt.store.view.recommended;

import android.content.Context;
import cm.aptoide.accountmanager.AptoideAccountManager;
import cm.aptoide.pt.AptoideApplication;
import cm.aptoide.pt.R;
import cm.aptoide.pt.dataprovider.model.v7.store.Store;
import cm.aptoide.pt.navigator.FragmentNavigator;
import cm.aptoide.pt.store.RoomStoreRepository;
import cm.aptoide.pt.store.StoreCredentialsProvider;
import cm.aptoide.pt.store.StoreUtils;
import cm.aptoide.pt.store.StoreUtilsProxy;
import cm.aptoide.pt.view.recycler.displayable.DisplayablePojo;
import rx.Observable;

/**
 * Created by trinkes on 06/12/2016.
 */

public class RecommendedStoreDisplayable extends DisplayablePojo<Store> {

  private AptoideAccountManager accountManager;
  private RoomStoreRepository storeRepository;
  private StoreUtilsProxy storeUtilsProxy;
  private StoreCredentialsProvider storeCredentialsProvider;
  private String origin = "";

  public RecommendedStoreDisplayable() {
  }

  public RecommendedStoreDisplayable(Store pojo, RoomStoreRepository storeRepository,
      AptoideAccountManager accountManager, StoreUtilsProxy storeUtilsProxy,
      StoreCredentialsProvider storeCredentialsProvider) {
    super(pojo);
    this.storeRepository = storeRepository;
    this.accountManager = accountManager;
    this.storeUtilsProxy = storeUtilsProxy;
    this.storeCredentialsProvider = storeCredentialsProvider;
  }

  public RecommendedStoreDisplayable(Store store, RoomStoreRepository storeRepository,
      AptoideAccountManager accountManager, StoreUtilsProxy storeUtilsProxy,
      StoreCredentialsProvider storeCredentialsProvider, String origin) {
    super(store);
    this.storeRepository = storeRepository;
    this.accountManager = accountManager;
    this.storeUtilsProxy = storeUtilsProxy;
    this.storeCredentialsProvider = storeCredentialsProvider;
    this.origin = origin;
  }

  @Override protected Configs getConfig() {
    return new Configs(1, false);
  }

  @Override public int getViewLayout() {
    return R.layout.displayable_recommended_store;
  }

  Observable<Boolean> isFollowing() {
    return storeRepository.isSubscribed(getPojo().getId());
  }

  public void subscribeStore() {
    storeUtilsProxy.subscribeStore(getPojo().getName());
  }

  void unsubscribeStore(Context context) {
    if (accountManager.isLoggedIn()) {
      accountManager.unsubscribeStore(getPojo().getName(),
          storeCredentialsProvider.get(getPojo().getName())
              .getName(), storeCredentialsProvider.get(getPojo().getName())
              .getPasswordSha1());
    }
    StoreUtils.unSubscribeStore(getPojo().getName(), accountManager, storeCredentialsProvider,
        storeRepository);
  }

  void openStoreFragment(FragmentNavigator navigator) {
    navigator.navigateTo(AptoideApplication.getFragmentProvider()
        .newStoreFragment(getPojo().getName(), getPojo().getAppearance()
            .getTheme()), true);
  }

  public String getOrigin() {
    return origin;
  }
}
