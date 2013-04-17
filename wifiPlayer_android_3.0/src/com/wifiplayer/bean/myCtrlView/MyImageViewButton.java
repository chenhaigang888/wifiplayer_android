package com.wifiplayer.bean.myCtrlView;


import com.wifiplayer.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyImageViewButton extends LinearLayout {

	private ImageView mButtonImageView;
	private TextView mTextView;
	
	private int textId;
//	private int imageResId;
	private int TextColor;
	private int TextSize;
	private int bgImg;//按钮上的图片
	private String ImgButnOrientation;
	
//	private String text;
	private int backGroundBefore;
	private int backGroundAfter;
	
	
	public int getBackGroundBefore() {
		return backGroundBefore;
	}

	public void setBackGroundBefore(int backGroundBefore) {
		this.backGroundBefore = backGroundBefore;
	}

	public int getBackGroundAfter() {
		return backGroundAfter;
	}

	public void setBackGroundAfter(int backGroundAfter) {
		this.backGroundAfter = backGroundAfter;
	}

	public int getBgImg() {
		return bgImg;
	}

	public void setBgImg(int bgImg) {
		this.bgImg = bgImg;
		setBackgroundResource(bgImg);
	}

	public String getImgButnOrientation() {
		return ImgButnOrientation;
	}

	public void setImgButnOrientation(String imgButnOrientation) {
		ImgButnOrientation = imgButnOrientation;
		if(imgButnOrientation.equals("h")){
			setOrientation(0);
		}else{
			setOrientation(1);
		}
		
	}

	public int getTextSize() {
		return TextSize;
	}

	public void setTextSize(int textSize) {
		TextSize = textSize;
		mTextView.setTextSize(textSize);
	}

//	public void setText(String text) {
//		if(text!=null){
//			this.text = text;
//		}else{
//			this.text = " ";
//		}
//		
//		mTextView.setText(text);
//	}

	public int getTextColor() {
		return TextColor;
	}

	public void setTextColor(int textColor) {
		TextColor = textColor;
		mTextView.setTextColor(textColor);
	}

	public String getText() {
		return mTextView.getText().toString();
	}

//	public void setText(int textId) {
//		this.textId = textId;
//		if(textId==0){
//			setText(text);
//		}else{
//			mTextView.setText(textId);
//		}
//	}

//	public int getImageResId() {
//		return imageResId;
//	}
//
//	public void setImageResId(int imageResId) {
//		this.imageResId = imageResId;
//		mButtonImageView.setImageResource(imageResId);
//	}

	
	
	
	public MyImageViewButton(Context context ,int textId, int imageResId){
		super(context);
		this.textId = textId;
//		this.imageResId = imageResId;
		init(context);
	}

	public MyImageViewButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyImageViewButton);
//		try {
//			text = array.getText(R.styleable.MyImageViewButton_text).toString();
//		} catch (Exception e) {
//			text = "";
//		}
		TextColor = array.getColor(R.styleable.MyImageViewButton_TextColor, 0xffffff);
		TextSize = (int)array.getDimension(R.styleable.MyImageViewButton_TextSize,5);
//		imageResId = array.getResourceId(R.styleable.MyImageViewButton_imageResId, R.drawable.ic_launcher);
		ImgButnOrientation = array.getText(R.styleable.MyImageViewButton_ImgButnOrientation).toString();
		bgImg = array.getResourceId(R.styleable.MyImageViewButton_bgImg, android.R.drawable.btn_default);//按钮背景图片
		backGroundBefore = array.getResourceId(R.styleable.MyImageViewButton_backGroundBefore, 0);
		backGroundAfter = array.getResourceId(R.styleable.MyImageViewButton_backGroundAfter, 0);
		init(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			this.setBackgroundResource(backGroundAfter);
			break;
		case MotionEvent.ACTION_UP:
			this.setBackgroundResource(backGroundBefore);
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	public MyImageViewButton(Context context) {
		super(context);
	}

	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context){
		/*设置按钮上的图片*/
		mButtonImageView = new ImageView(context);//按钮的图片
		mButtonImageView.setPadding(0, 0, 0, 0);
//		setImageResId(imageResId);
		
		/*设置按钮上的文字等相关信息*/
		mTextView = new TextView(context);//按钮上的文字
		mTextView.setGravity(Gravity.CENTER);
//		setText(textId);
		mTextView.setPadding(0, 0, 0, 0);
		setTextSize(TextSize);
		setTextColor(TextColor);
		
		/*设置文字离各个方向的距离*/
//		LayoutParams lp = new LayoutParams(getHightOrWith(text, TextSize),TextSize*2);
//		lp.setMargins(0, 0, 0, 0);
//		mTextView.setLayoutParams(lp);
		
		setClickable(true);
		setFocusable(true);
		setBgImg(bgImg);
		setImgButnOrientation(ImgButnOrientation);
		this.setBackgroundResource(backGroundBefore);
		setGravity(Gravity.CENTER);
		 
		addView(mButtonImageView);
		addView(mTextView);
	}
	/**
	 * 计算一个汉字要暂多少像素 
	 * @param text
	 * @param TextSize
	 * @return
	 */
	public int getHightOrWith(String text, int TextSize){
		int textLenth = text.length();
		return 2*textLenth*TextSize;
		
	}
	
}
