package somebody_z.me.zuimusic.utils;

import android.support.annotation.NonNull;

import java.util.Comparator;

import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.widget.indexbar.EntityWrapper;
import somebody_z.me.zuimusic.widget.indexbar.IndexableEntity;
import somebody_z.me.zuimusic.widget.indexbar.PinyinUtil;

/**
 * Created by Huanxing Zeng on 2017/3/6.
 * email : zenghuanxing123@163.com
 */
public class PinyinComparatorUtil<T extends IndexableEntity> implements Comparator<ContentBean> {

    @Override
    public int compare(ContentBean lhs, ContentBean rhs) {
        String lhsIndexName = lhs.getFieldIndexBy();
        String rhsIndexName = rhs.getFieldIndexBy();

        if (lhsIndexName == null) {
            lhsIndexName = "";
        }
        if (rhsIndexName == null) {
            rhsIndexName = "";
        }
        return compareIndexName(lhsIndexName.trim(), rhsIndexName.trim());
    }

    private int compareIndexName(String lhs, String rhs) {
        int index = 0;

        String lhsWord = getWord(lhs, index);
        String rhsWord = getWord(rhs, index);
        while (lhsWord.equals(rhsWord) && !lhsWord.equals("")) {
            index++;
            lhsWord = getWord(lhs, index);
            rhsWord = getWord(rhs, index);
        }
        return lhsWord.compareTo(rhsWord);
    }

    @NonNull
    private String getWord(String indexName, int index) {
        if (indexName.length() < (index + 1)) return "";
        String firstWord;
        if (PinyinUtil.matchingPolyphone(indexName)) {
            firstWord = PinyinUtil.getPingYin(PinyinUtil.getPolyphoneRealHanzi(indexName).substring(index, index + 1));
        } else {
            firstWord = PinyinUtil.getPingYin(indexName.substring(index, index + 1));
        }
        return firstWord;
    }
}