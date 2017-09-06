package code.boilerplate.features;

import java.util.List;

import code.boilerplate.pojo.FilmLocation;

/**
 * Created by shank on 06/09/17.
 */

public interface IUiView {
    void showErrorMessage(Throwable throwable);

    void onDataReceived(List<FilmLocation> realmFilmLocations);

    void hideDialog();

    void showDialog();

    void onEmptySearchResult();

    void showNoInternetError(Throwable throwable);
}
