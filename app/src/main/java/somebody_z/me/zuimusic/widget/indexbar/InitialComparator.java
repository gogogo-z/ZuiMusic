package somebody_z.me.zuimusic.widget.indexbar;

import java.util.Comparator;

/**
 * Created by Huanxing Zeng on 2017/3/6.
 * email : zenghuanxing123@163.com
 */
public class InitialComparator<T extends IndexableEntity> implements Comparator<EntityWrapper<T>> {
    @Override
    public int compare(EntityWrapper<T> lhs, EntityWrapper<T> rhs) {
        return lhs.getIndex().compareTo(rhs.getIndex());
    }
}
