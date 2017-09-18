package code.moneytap.features;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import code.moneytap.R;
import code.moneytap.base.BaseActivity;
import code.moneytap.di.DaggerMainComponent;
import code.moneytap.di.MainComponent;
import code.moneytap.di.MainModule;
import code.moneytap.pojo.Page;
import code.moneytap.realmmodels.RealmPage;
import code.moneytap.utils.Logger;
import code.moneytap.utils.ViewUtil;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements IUiView {

    @Inject
    Service service;
    @BindView(R.id.empty_view)
    View emptytView;
    @BindView(R.id.recycler_view)
    RealmRecyclerView recyclerView;
    ProgressDialog progressDialog;
    MainComponent component;
    Presenter presenter;
    @BindView(R.id.snakbar)
    CoordinatorLayout snackbarHolder;
    List<Page> pages;
    private SearchPagesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initializeDependencies();
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        // Read data
        RealmResults<RealmPage> realmResults = presenter.readCacheDataFromRealm();
        // SetAdapter
        mAdapter = new SearchPagesAdapter(this, realmResults, true, true);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(),1));
        recyclerView.setAdapter(mAdapter);
    }

    void initializeDependencies(){
        // injection
        component = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule()).build();
        component.inject(this);

        // presenter
        presenter = (Presenter) getLastCustomNonConfigurationInstance();
        Logger.debug("Presenter: "+presenter);
        if (presenter == null) {
            presenter = new Presenter(service);
        }
        presenter.attachView(this);
        presenter.getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    @Override
    public void onDataReceived(List<Page> data) {
        if(isNull(data) || data.size()==0){
            recyclerView.setVisibility(View.GONE);
            emptytView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void showDialog() {
        ViewUtil.showProgressDialog(progressDialog);

    }

    @Override
    public void hideDialog() {
        ViewUtil.hideDialog(progressDialog);
    }

    @Override
    public void onEmptySearchResult() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter!=null) mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ViewUtil.hideDialog(progressDialog);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void showNoInternetError(Throwable throwable) {
        final Snackbar snackbar = Snackbar
                .make(snackbarHolder, throwable.getMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), view -> {
                    presenter.getData();
                });

        snackbar.show();
    }
}
