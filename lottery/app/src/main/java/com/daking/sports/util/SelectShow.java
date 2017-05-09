package com.daking.sports.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @ClassName: SelectShow
 * @Description: TODO(传递内容给SelectBottomActivity)
 * @author hyx18670335751@163.com
 * @date 2015年12月28日 下午3:37:10
 * 
 */
public class SelectShow implements Parcelable {
	private String key;
	private String value;

	public SelectShow() {
	}

	public SelectShow(Parcel source) {
		key = source.readString();
		value = source.readString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(key);
		dest.writeString(value);
	}

	public final static Creator<SelectShow> CREATOR = new Creator<SelectShow>() {

		@Override
		public SelectShow createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new SelectShow(source);
		}

		@Override
		public SelectShow[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SelectShow[size];
		}
	};

}
