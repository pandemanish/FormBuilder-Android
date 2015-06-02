package com.vertis.formbuilder;

import com.vertis.formbuilder.Listeners.CustomTextChangeListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.vertis.formbuilder.parser.FieldConfig;
import com.vertis.formbuilder.util.FormBuilderUtil;

public class SimpleEditText implements IField{

	private FieldConfig config;
	//Views
	LinearLayout llEditText;
	TextView tvEditText;
	EditText etEditText;

	//Values
	@Expose
	String cid;
	@Expose
	String text="";

	public SimpleEditText(FieldConfig fcg){
		this.config=fcg;
	}	

	@SuppressLint("ResourceAsColor")
	@Override
	public void createForm(Activity context) {
		Typeface typeface = new FormBuilderUtil().getFontFromRes(context);
		LayoutInflater inflater = context.getLayoutInflater();
		llEditText=(LinearLayout) inflater.inflate(R.layout.simple_edit_text,null);
		tvEditText = (TextView) llEditText.findViewById(R.id.simpleTextView);
		etEditText = (EditText) llEditText.findViewById(R.id.simpleEditText);
		etEditText.setTypeface(typeface);
		tvEditText.setTypeface(typeface);
		etEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,(float) 12.5);
		tvEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		etEditText.addTextChangedListener(new CustomTextChangeListener(config));
		defineViewSettings();
		setViewValues();
		mapView();
		setValues();
		noErrorMessage();
	}

	@SuppressLint("ResourceAsColor")
	private void noErrorMessage() {
		if(tvEditText==null)return;
		tvEditText.setText(this.config.getLabel() + (this.config.getRequired()?"*":"") );
		tvEditText.setTextColor(tvEditText.getContext().getResources().getColor(R.color.TextViewNormal));
	}

	private void mapView() {
		ViewLookup.mapField(this.config.getCid()+"_1", llEditText);
		ViewLookup.mapField(this.config.getCid()+"_1_1", etEditText);
	}

	private void setViewValues() {
		tvEditText.setText(this.config.getLabel() + (this.config.getRequired()?"*":"") );
		etEditText.setText(text);
		tvEditText.setTextColor(-1);
	}

	private void defineViewSettings() {
		etEditText.setOnFocusChangeListener( new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					noErrorMessage();
				else
					setValues();
			}
		});
	}

	@Override
	public ViewGroup getView() {
		return llEditText;
	}

	@Override
	public boolean validate() {
		boolean valid;
		if(config.getRequired() && text.equals("")){
			valid=false;
			errorMessage(" Required");
		}
		else{
			valid=true;
			noErrorMessage();
		}
		return valid;
	}

	@SuppressLint("ResourceAsColor")
	private void errorMessage(String message) {
		if(tvEditText==null)return;
		tvEditText.setText(this.config.getLabel() + (this.config.getRequired()?"*":"") );
		tvEditText.setText(tvEditText.getText() + message);
		tvEditText.setTextColor(tvEditText.getContext().getResources().getColor(R.color.ErrorMessage));
	}

	@Override
	public void setValues() {
		this.cid=config.getCid();
		if(llEditText!=null){
			text=etEditText.getText().toString();
		}
		validate();
	}

	@Override
	public void clearViews() {
		setValues();
		llEditText=null;
		tvEditText=null;
		etEditText=null;
	}

	public String getCIDValue() {
		return this.config.getCid();
	}

	public void hideField() {
		if(llEditText!=null){
			llEditText.setVisibility(View.GONE);
			llEditText.invalidate();
		}
	}

	@Override
	public void showField() {
		if(llEditText!=null){
			llEditText.setVisibility(View.VISIBLE);
			llEditText.invalidate();
		}
	}

	public boolean validateDisplay(String value,String condition) {
		return !condition.equals("equals") || text.toLowerCase().equals(value.toLowerCase()) || text.equals("");
	}

    public boolean isHidden() {
		return llEditText != null && !llEditText.isShown();
	}
}