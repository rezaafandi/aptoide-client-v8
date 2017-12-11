package cm.aptoide.pt.social.commentslist;

import android.support.annotation.NonNull;
import cm.aptoide.pt.crashreports.CrashReport;
import cm.aptoide.pt.presenter.Presenter;
import cm.aptoide.pt.presenter.View;
import rx.Scheduler;

/**
 * Created by jdandrade on 28/09/2017.
 */

public class PostCommentsPresenter implements Presenter {

  private final PostCommentsView view;
  private final Comments comments;
  private final String postId;
  private final Scheduler viewScheduler;
  private final CrashReport crashReporter;

  public PostCommentsPresenter(@NonNull PostCommentsView commentsView, Comments comments,
      Scheduler viewScheduler, CrashReport crashReporter, String postId) {
    this.view = commentsView;
    this.comments = comments;
    this.viewScheduler = viewScheduler;
    this.crashReporter = crashReporter;
    this.postId = postId;
  }

  @Override public void present() {
    view.getLifecycle()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMapSingle(created -> comments.getComments(postId))
        .observeOn(viewScheduler)
        .doOnNext(comments -> view.showComments(comments))
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(comments -> {
        }, throwable -> crashReporter.log(throwable));

    view.getLifecycle()
        .filter(lifecycleEvent -> lifecycleEvent.equals(View.LifecycleEvent.CREATE))
        .flatMap(created -> view.refreshes()
            .flatMapSingle(__ -> comments.getComments(postId))
            .observeOn(viewScheduler)
            .doOnNext(comments -> view.showComments(comments))
            .doOnNext(comments -> view.hideRefresh())
            .retry())
        .compose(view.bindUntilEvent(View.LifecycleEvent.DESTROY))
        .subscribe(comments -> {
        }, throwable -> crashReporter.log(throwable));
  }
}