package com.daking.sports.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.daking.sports.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 
 * @author hyx18670335751@163.com TODO 自定义输入控件
 * 
 */
public class FormatEditText extends android.support.v7.widget.AppCompatEditText implements OnFocusChangeListener,
        TextWatcher {
	// 对应输入的格式
	private int formatBankCard = 1; // 银行卡
	private int formatPhone = 2; // 手机格式
	private int formatNormal = 0; // 正常的输入
	private int formatSpecialPlane = 3; // 座机

	private TypedArray attrArray;
	private int formatType = 0;
	private String digits;

	private int beforeTextLength = 0;
	private int onTextLength = 0;
	boolean isChanged = false;

	private int inputTime; // 输入次数
	private int deleteTime; // 删除次数
	private int pasteTime; // 粘贴次数

	private int location = 0;// 记录光标的位置
	private char[] tempChar;
	private StringBuffer buffer = new StringBuffer();
	private int konggeNumberB = 0;
	boolean isOneText = true; // 判断运行时只进入一次onTextChanged

	private OnInputOverListener mOnInputOver;

	/** constructor */
	public FormatEditText(Context context) {
		super(context);
		initValidationEditText();
	}

	public FormatEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		attrArray = context.obtainStyledAttributes(attrs, R.styleable.destiny);
		initValidationEditText();
	}

	public FormatEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		attrArray = context.obtainStyledAttributes(attrs, R.styleable.destiny, defStyle, 0);
		initValidationEditText();
	}

	private void initValidationEditText() {
		// 获取格式枚举
		if (attrArray != null) {
			formatType = Integer.parseInt(attrArray
					.getString(R.styleable.destiny_format));
			digits = attrArray.getString(R.styleable.destiny_digits);

			attrArray.recycle();
		}

		// 根据枚举判断需要设置那种输入法
		if (formatType == formatNormal) {
			setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		} else if (formatType == formatBankCard) {
			setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		} else if (formatType == formatPhone) {
			setInputType(InputType.TYPE_CLASS_PHONE
					| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		} else if (formatType == formatSpecialPlane) {
			setInputType(InputType.TYPE_CLASS_PHONE
					| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		} else {
			setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		}

		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	public void onInputOverListener(OnInputOverListener onInputOverListener) {
		mOnInputOver = onInputOverListener;
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (!hasFocus) {
			// String text = this.getText().toString();

		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
		// TODO 输入之前
		beforeTextLength = s.length();
		if (buffer.length() > 0) {
			buffer.delete(0, buffer.length());
		}
		konggeNumberB = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				konggeNumberB++;
			}
		}
	}

	@Override
	public void onTextChanged(CharSequence text, int start, int lengthBefore,
                              int lengthAfter) {
		// TODO 内容改变时触发
		if (digits != null && formatType == formatNormal) {
			String editable = buffer.toString();
			String stri = stringFilter(editable.toString());
			if (!editable.equals(stri)) {
				setText(stri);
				// 设置新的光标所在位置
				setSelection(stri.length());
			}
		}
		if (!text.toString().equals("") && isOneText) {
			// 判断是输入还是删除
			if (lengthAfter > lengthBefore && lengthBefore == 0) {
				inputTime++;
				// lengthAfter - lengthBefore大于1说明是做了粘贴操作
				if (lengthAfter - lengthBefore > 1) {
					pasteTime++;
				}
			} else if (lengthBefore > lengthAfter && lengthAfter == 0) {
				deleteTime++;
			}
			isOneText = false;
			onTextLength = text.length();
			buffer.append(text.toString());
			if (onTextLength == beforeTextLength || onTextLength <= 3
					|| isChanged) {
				isChanged = false;
				return;
			}
			isChanged = true;
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO 输入之后的内容
		isOneText = true;
		if (isChanged) {
			location = getSelectionEnd();
			int index = 0;
			while (index < buffer.length()) {
				if (buffer.charAt(index) == ' ') {
					buffer.deleteCharAt(index);
				} else {
					index++;
				}
			}

			index = 0;
			int konggeNumberC = 0;
			while (index < buffer.length()) {
				if (formatType == formatBankCard) { // 银行卡号
					if (index % 5 == 4) {
						buffer.insert(index, ' ');
						konggeNumberC++;
					}
				} else if (formatType == formatPhone) { // 手机号
					if (index == 3) {
						buffer.insert(index, ' ');
						konggeNumberC++;
					} else if (index == 8) {
						buffer.insert(index, ' ');
						konggeNumberC++;
					} else {
						if (mOnInputOver != null)
							mOnInputOver.onInputOver(buffer.toString());
					}
				} else if (formatType == formatSpecialPlane) { // 座机
					String str = buffer.substring(0, 2);
					if (str.equals("01") || str.equals("02")) {
						if (index == 3) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
					} else {
						if (index == 4) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
					}
				}
				index++;
			}

			if (konggeNumberC > konggeNumberB) {
				location += (konggeNumberC - konggeNumberB);
			}

			tempChar = new char[buffer.length()];
			buffer.getChars(0, buffer.length(), tempChar, 0);
			String str = buffer.toString();

			if (formatType != formatNormal)
				setText(str);
			String textStr = getText().toString();
			if (location > str.length()) {
				location = str.length();
			} else if (location > textStr.length()) {
				location = textStr.length();
			} else if (location < 0) {
				location = 0;
			}

			Editable etable = getText();
			Selection.setSelection(etable, location);
			isChanged = false;
		}
	}

	public String stringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		String regEx = digits;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 
	 * @return 输入次数
	 */
	public int getInputTime() {
		return inputTime;
	}

	/**
	 * 
	 * @return 粘贴次数
	 */
	public int getPasteTime() {
		return pasteTime;
	}

	/**
	 * 
	 * @return 删除次数
	 */
	public int getDeleteTime() {
		return deleteTime;
	}

	/**
	 * 
	 * @return 输入完毕回调
	 */
	public interface OnInputOverListener {
		public void onInputOver(String str);
	}
}
