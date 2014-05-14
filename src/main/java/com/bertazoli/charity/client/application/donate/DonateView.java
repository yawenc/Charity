package com.bertazoli.charity.client.application.donate;

import javax.inject.Inject;

import com.bertazoli.charity.client.application.core.validators.MultipleOfValidator;
import com.bertazoli.charity.client.application.donate.widget.CharityWidget;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.client.i18n.GlobalDictionary;
import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.beans.DonationInformation;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.Slider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;

public class DonateView extends ViewImpl implements DonatePresenter.MyView {
    interface Binder extends UiBinder<Widget, DonateView> {}
    
    @UiField FormPanel mainPanel;
    @UiField HTMLPanel charityPanel;
    @UiField Slider amountToKeep;
    @UiField SimplePanel amountToKeepTextPanel;
    @UiField SimplePanel amountToDonatePanel;
    @UiField TextButton submit;
    private NumberField<Integer> amountToKeepText;
    private NumberField<Integer> amountToDonate;
    @Inject Provider<CharityWidget> charityWidgetProvider;
    @Inject SecurityManager securityManager;
    private Charity bean;

    @Inject
    DonateView(Binder uiBinder, GlobalDictionary dictionary) {
        initWidget(uiBinder.createAndBindUi(this));
        amountToKeepText = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        amountToKeepTextPanel.add(amountToKeepText);
        amountToDonate = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        amountToDonatePanel.add(amountToDonate);
        amountToDonate.setAllowBlank(false);
        amountToDonate.setFormat(NumberFormat.getFormat(dictionary.currencyFormat()));
        amountToDonate.addValidator(new MinNumberValidator<Integer>(15));
        amountToDonate.addValidator(new MultipleOfValidator(5, dictionary.valueHasToBeMultipleOf(5)));
        amountToKeepText.setValue(15);
        amountToKeepText.addValidator(new MinNumberValidator<Integer>(0));
        amountToKeepText.addValidator(new MaxNumberValidator<Integer>(30));
        amountToKeepText.setAllowBlank(false);
        amountToKeep.setValue(15);
        amountToKeep.setMinValue(0);
        amountToKeep.setMaxValue(30);
        amountToKeep.setIncrement(1);
    }

    @Override
    public HasValueChangeHandlers<Integer> getSliderValueChange() {
        return amountToKeep;
    }

    @Override
    public void setTextPercentage(Integer percentage) {
        amountToKeepText.setValue(percentage);
        amountToKeepText.validate();
    }

    @Override
    public HasValueChangeHandlers<Integer> getPercentageValueChange() {
        return amountToKeepText;
    }

    @Override
    public void setSliderPercentage(Integer percentage) {
        amountToKeep.setValue(percentage > 30 ? 30 : percentage);
    }

    @Override
    public void setBean(Charity bean) {
        this.bean = bean;
        charityPanel.clear();
        CharityWidget widget = charityWidgetProvider.get();
        widget.setBean(bean);
        charityPanel.add(widget);
    }

    @Override
    public HasSelectHandlers getSubmit() {
        return submit;
    }

    @Override
    public boolean validate() {
        return mainPanel.isValid();
    }

    @Override
    public DonationInformation mapBean(DonationInformation bean) {
        bean.setUserId(securityManager.getUser().getId());
        bean.setAmountToDonate(amountToDonate.getValue());
        bean.setPercentageToKeep(amountToKeepText.getValue());
        bean.setCharityId(this.bean.getId());
        return bean;
    }
}
