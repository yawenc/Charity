package com.bertazoli.charity.client.application.oracle;

import com.bertazoli.charity.shared.action.OracleAction;
import com.bertazoli.charity.shared.action.OracleResult;
import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResultBean;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfigBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent.BeforeLoadHandler;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.form.ComboBox;

public class ComboBoxWithOracle<U extends OracleLoadConfigBean> {
    private static OracleProperties properties = GWT.create(OracleProperties.class);
    private ComboBox<IsOracleData> combobox;
    private ListStore<IsOracleData> listStore = new ListStore<IsOracleData>(properties.id());
    private LabelProvider<IsOracleData> labelProvider = properties.description();
    
    @Inject
    public ComboBoxWithOracle(final DispatchAsync dispatcher, Provider<U> configProvider) {
        this.combobox = new ComboBox<IsOracleData>(listStore, labelProvider);
        
        DataProxy<U, OracleListLoadResultBean> dataProxy = new RpcProxy<U, OracleListLoadResultBean>() {;

            @Override
            public void load(U loadConfig, final AsyncCallback<OracleListLoadResultBean> callback) {
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

        PagingLoader<U, OracleListLoadResultBean> loader = new PagingLoader<U, OracleListLoadResultBean>(dataProxy);
        loader.useLoadConfig(configProvider.get());
        loader.addBeforeLoadHandler(new BeforeLoadHandler<U>() {
            @Override
            public void onBeforeLoad(BeforeLoadEvent<U> event) {
                String query = combobox.getText();
                if (query != null && !query.equals("")) {
                    event.getLoadConfig().setQuery(query);
                }
            }
        });

        loader.addLoadHandler(new LoadResultListStoreBinding<U, IsOracleData, OracleListLoadResultBean>(listStore));
        combobox.setLoader(loader);
        combobox.setMinChars(2);
        combobox.setHideTrigger(true);
    }
    
    public ComboBox<IsOracleData> getComboBox() {
        return combobox;
    }
}
