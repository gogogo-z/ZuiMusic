package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class LoopPicBean {

    /**
     * pic : [{},{},{},{},{}]
     * error_code : 22000
     */

    private int error_code;
    private List<PicBean> pic;

    public static LoopPicBean objectFromData(String str) {

        return new Gson().fromJson(str, LoopPicBean.class);
    }

    public static List<LoopPicBean> arrayLoopPicBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<LoopPicBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class PicBean {

        /**
         * randpic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_14830716065b8e7ae9682043c14b6384c31cd8ea01.jpg
         * randpic_ios5 :
         * randpic_desc : 2017火电四溅最佳桃花中国电子风神曲
         * randpic_ipad : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1483071619036ee673972061093184e51d03a7380a.jpg
         * randpic_qq : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1483071622608f320e19b5f92c492a7d51fc06d2e9.jpg
         * randpic_2 : bos_client_14830716144c6c45555e299b05ff29a0e85cd6d3e2
         * randpic_iphone6 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_14830716144c6c45555e299b05ff29a0e85cd6d3e2.jpg
         * special_type : 0
         * ipad_desc : 2017火电四溅最佳桃花中国电子风神曲
         * is_publish : 111111
         * mo_type : 2
         * type : 2
         * code : 300892213
         */

        private String randpic;
        private String randpic_ios5;
        private String randpic_desc;
        private String randpic_ipad;
        private String randpic_qq;
        private String randpic_2;
        private String randpic_iphone6;
        private int special_type;
        private String ipad_desc;
        private String is_publish;
        private String mo_type;
        private int type;
        private String code;

        public static PicBean objectFromData(String str) {

            return new Gson().fromJson(str, PicBean.class);
        }

        public static List<PicBean> arrayPicBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<PicBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getRandpic() {
            return randpic;
        }

        public void setRandpic(String randpic) {
            this.randpic = randpic;
        }

        public String getRandpic_ios5() {
            return randpic_ios5;
        }

        public void setRandpic_ios5(String randpic_ios5) {
            this.randpic_ios5 = randpic_ios5;
        }

        public String getRandpic_desc() {
            return randpic_desc;
        }

        public void setRandpic_desc(String randpic_desc) {
            this.randpic_desc = randpic_desc;
        }

        public String getRandpic_ipad() {
            return randpic_ipad;
        }

        public void setRandpic_ipad(String randpic_ipad) {
            this.randpic_ipad = randpic_ipad;
        }

        public String getRandpic_qq() {
            return randpic_qq;
        }

        public void setRandpic_qq(String randpic_qq) {
            this.randpic_qq = randpic_qq;
        }

        public String getRandpic_2() {
            return randpic_2;
        }

        public void setRandpic_2(String randpic_2) {
            this.randpic_2 = randpic_2;
        }

        public String getRandpic_iphone6() {
            return randpic_iphone6;
        }

        public void setRandpic_iphone6(String randpic_iphone6) {
            this.randpic_iphone6 = randpic_iphone6;
        }

        public int getSpecial_type() {
            return special_type;
        }

        public void setSpecial_type(int special_type) {
            this.special_type = special_type;
        }

        public String getIpad_desc() {
            return ipad_desc;
        }

        public void setIpad_desc(String ipad_desc) {
            this.ipad_desc = ipad_desc;
        }

        public String getIs_publish() {
            return is_publish;
        }

        public void setIs_publish(String is_publish) {
            this.is_publish = is_publish;
        }

        public String getMo_type() {
            return mo_type;
        }

        public void setMo_type(String mo_type) {
            this.mo_type = mo_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
