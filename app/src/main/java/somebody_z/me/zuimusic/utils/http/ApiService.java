package somebody_z.me.zuimusic.utils.http;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.LoopPicBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewsBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewsDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.mvp.model.bean.RankDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;
import somebody_z.me.zuimusic.mvp.model.bean.SearchSongBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetCategoryBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * @author HuanxingZeng
 * @version 创建时间：2016年12月5日 上午11:13:22
 */
public interface ApiService {

    // 个性推荐顶部轮播图
    // method 方法
    // num 获取的数量
    @GET("ting")
    Observable<LoopPicBean> getLoopPic(@Query("method") String method, @Query("num") int num);

    //获取推荐歌单
    @GET("ting")
    Observable<RecommendSongSheetBean> getRecommendSongSheet(@Query("method") String method, @Query("num") int num);

    //获取热门唱片
    @GET("t】ing")
    Observable<HotAlbumBean> getHotAlbum(@Query("method") String method, @Query("offset") int offset, @Query("limit") int limit);

    //获取最新音乐
    @GET("ting")
    Observable<NewMusicBean> getNewMusic(@Query("method") String method, @Query("num") int num);

    //获取主播电台
    @GET("ting")
    Observable<AnchorRadioBean> getAnchorRadio(@Query("method") String method, @Query("num") int num);

    //获取全部歌单
    @GET("ting")
    Observable<AllSongSheetBean> getAllSongSheet(@Query("method") String method, @Query("page_size") int page_size,
                                                 @Query("page_no") int page_no);

    //获取分类歌单
    @GET("ting")
    Observable<AllSongSheetBean> getCategorySongSheet(@Query("method") String method, @Query("page_size") int page_size,
                                                      @Query("page_no") int page_no, @Query("query") String query);

    //获取排行榜
    @GET("ting")
    Observable<RankBean> getAllRank(@Query("method") String method, @Query("kflag") int kflag);

    //获取歌单详情
    @GET("ting")
    Observable<SongSheetDetail> getSongSheetDetail(@Query("method") String method, @Query("listid") String listid);

    //获取唱片详情
    @GET("ting")
    Observable<AlbumDetailBean> getAlbumDetail(@Query("method") String method, @Query("album_id") String album_id);

    //获取排行榜详情
    @GET("ting")
    Observable<RankDetailBean> getRankDetail(@Query("method") String method, @Query("type") String type, @Query("offset") int offset
            , @Query("size") int size, @Query("fields") String fields);

    //获取歌单分类
    @GET("ting")
    Observable<SongSheetCategoryBean> getSongSheetCategory(@Query("method") String method);

    //获取播放地址
    @GET("ting")
    Observable<PlaySongInfoBean> getSongInfo(@Query("method") String method, @Query("songid") String song_id);

    //获取播放地址
    @GET("ting")
    Observable<SearchSongBean> searchSong(@Query("method") String method, @Query("query") String query);

    //获取知乎日报内容
    @GET("http://news-at.zhihu.com/api/4/news/before/{date}")
    Observable<NewsBean> getNews(@Path("date") String date);

    //获取知乎日报具体内容
    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<NewsDetailBean> getNewsDetail(@Path("id") int id);

    @Multipart
    @POST("http://gon.tunnel.qydev.com/upload/img")
    Observable<Object> post(@Part MultipartBody.Part photo);

}
