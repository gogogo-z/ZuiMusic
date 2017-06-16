package somebody_z.me.zuimusic.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;

/**
 * Created by Huanxing Zeng on 2017/2/10.
 * email : zenghuanxing123@163.com
 */
public class ZEditText extends LinearLayout {

    private OnChangeTextListener onChangeTextListener;

    private EditText editText;
    private LinearLayout llClear;
    private TextView tvLine;
    private ImageView ivClear;

    private int defaultColor = R.color.Gray;
    private int focusColor = R.color.colorWhite;

    public ZEditText(Context context) {
        this(context, null);
    }

    public ZEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.widget_zedittext, this);
        editText = (EditText) findViewById(R.id.et_widget_zedittext);
        llClear = (LinearLayout) findViewById(R.id.ll_widget_zedittext_clear);
        tvLine = (TextView) findViewById(R.id.tv_widget_zedittext_bottom_line);
        ivClear = (ImageView) findViewById(R.id.iv_widget_clear);
    }

    private void initListener(Context context) {
        llClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tvLine.setBackgroundColor(getResources().getColor(focusColor));
                    llClear.setVisibility(VISIBLE);
                } else {
                    tvLine.setBackgroundColor(getResources().getColor(defaultColor));
                    llClear.setVisibility(GONE);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onChangeTextListener.setTextContent(charSequence.toString());
                if (charSequence.length() > 0) {
                    llClear.setVisibility(VISIBLE);
                } else {
                    llClear.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setFocusColor(int focusColor) {
        this.focusColor = focusColor;
    }

    public void setHint(String hint) {
        editText.setHint(hint);
    }

    public void setHintColor(int color) {
        editText.setHintTextColor(getResources().getColor(color));
    }

    public void setClearRes(int resId) {
        ivClear.setBackgroundResource(resId);
    }

    public void setEditTextColor(int color) {
        editText.setTextColor(color);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    public void getFocus() {

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public void showSoftInput() {
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(MyApplication.getContext().INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    public void setOnChangeTextListener(OnChangeTextListener onChangeTextListener) {
        this.onChangeTextListener = onChangeTextListener;
    }

    public interface OnChangeTextListener {
        void setTextContent(String content);
    }
}
