package com.bertazoli.charity.client.application.charity;

import javax.inject.Inject;

import com.bertazoli.charity.client.application.oracle.OracleProperties;
import com.bertazoli.charity.shared.action.OracleAction;
import com.bertazoli.charity.shared.action.OracleResult;
import com.bertazoli.charity.shared.beans.oracle.Country;
import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResult;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfig;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfigBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent.BeforeLoadHandler;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class RegisterCharityView extends ViewImpl implements RegisterCharityPresenter.MyView {
    interface Binder extends UiBinder<Widget, RegisterCharityView> {}

    private static OracleProperties properties = GWT.create(OracleProperties.class);

    @UiField FormPanel mainPanel;
    @UiField TextField name;
    @UiField TextField registrationNumber;
    @UiField DateField effectiveDateOfStatus;
    @UiField TextField category;
    @UiField TextButton send;
    @UiField ComboBox<Country> country;
    @UiField(provided = true) ListStore<Country> listStore = new ListStore<Country>(properties.id());
    @UiField(provided = true) LabelProvider<Country> labelProvider = properties.description();

    @Inject
    RegisterCharityView(Binder uiBinder, final DispatchAsync dispatcher) {
        initWidget(uiBinder.createAndBindUi(this));

        DataProxy<OracleLoadConfig, OracleListLoadResult> dataProxy = new RpcProxy<OracleLoadConfig, OracleListLoadResult>() {
            @Override
            public void load(OracleLoadConfig loadConfig, final AsyncCallback<OracleListLoadResult> callback) {
                dispatcher.execute(new OracleAction(loadConfig), new AsyncCallback<OracleResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        caught.printStackTrace();
                    }

                    @Override
                    public void onSuccess(OracleResult result) {
                        callback.onSuccess(result.getResult());
                    }
                });
            }
        };

        PagingLoader<OracleLoadConfig, OracleListLoadResult> loader = new PagingLoader<OracleLoadConfig, OracleListLoadResult>(dataProxy);
        loader.useLoadConfig(new OracleLoadConfigBean());
        loader.addBeforeLoadHandler(new BeforeLoadHandler<OracleLoadConfig>() {
            @Override
            public void onBeforeLoad(BeforeLoadEvent<OracleLoadConfig> event) {
                String query = country.getText();
                if (query != null && !query.equals("")) {
                    event.getLoadConfig().setQuery(query);
                }
            }
        });

        loader.addLoadHandler(new LoadResultListStoreBinding<OracleLoadConfig, Country, OracleListLoadResult>(listStore));
        country.setLoader(loader);
        country.setMinChars(2);
        country.setHideTrigger(true);
        country.setPageSize(10);
    }
}
