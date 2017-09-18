package code.moneytap.features;

import java.util.List;

import code.moneytap.pojo.Page;

/**
 * Created by shank on 06/09/17.
 */

public interface IUiView {
    void showErrorMessage(Throwable throwable);

    void onDataReceived(List<Page> pages);

    void hideDialog();

    void showDialog();

    void onEmptySearchResult();

    void showNoInternetError(Throwable throwable);
}
