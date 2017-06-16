package somebody_z.me.zuimusic.widget.indexbar;

/**
 * Created by Huanxing Zeng on 2017/3/6.
 * email : zenghuanxing123@163.com
 */
public interface IndexableEntity {

    String getFieldIndexBy();

    void setFieldIndexBy(String indexField);

    void setFieldPinyinIndexBy(String pinyin);

}
