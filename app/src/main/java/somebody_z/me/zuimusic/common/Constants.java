package somebody_z.me.zuimusic.common;

import android.net.Uri;

/**
 * 常量类
 *
 * @author HuanxingZeng
 * @version 创建时间：2016年12月4日 下午11:46:09
 */
public class Constants {

    /**
     * 用来保存是否第一次使用应用的文件名
     */
    public static final String FLAG_FIRST_USED = "flag_first_used";

    /**
     * 用来保存是否第一次使用应用的标识值
     */
    public static final String FLAG_FIRST_USED_VALUE = "flag_first_used_value";

    public static final int SHOW_SHORT = 0;
    public static final int SHOW_LONG = 1;

    public static final String LOOP_PIC = "baidu.ting.plaza.getFocusPic";
    public static final String RECOMMEND_SONG_SHEET = "baidu.ting.diy.getHotGeDanAndOfficial";
    public static final String HOT_ALBUM = "baidu.ting.plaza.getRecommendAlbum";
    public static final String NEW_MUSIC = "baidu.ting.song.getEditorRecommend";
    public static final String ANCHOR_RADIO = "baidu.ting.radio.getRecommendRadioList";
    public static final String ALL_SONG_SHEET = "baidu.ting.diy.gedan";

    public static final String CATEGORY_SONG_SHEET = "baidu.ting.diy.search";

    public static final String ALL_RANK = "baidu.ting.billboard.billCategory";

    public static final String SONG_SHEET_DETAIL = "baidu.ting.diy.gedanInfo";

    public static final String ALBUM_INFO = "baidu.ting.album.getAlbumInfo";

    public static final String RANK_DETAIL = "baidu.ting.billboard.billList";

    public static final String SONG_INFO = "baidu.ting.song.play";

    public static final String SONG_SHEET_CATEGORY = "baidu.ting.diy.gedanCategory";

    public static final String SEARCH_SONG = "baidu.ting.search.catalogSug";

    public static final String FIELDS = "song_id,title,author,album_title,pic_big,pic_small,havehigh,all_rate,charge,has_mv_mobile,learn,song_source,korean_bb_song";

    public static final String URL = "url";

    public static final String TYPE = "type";
    public static final String ID = "id";

    public static final String ONLINE_ALBUM = "online_album";
    public static final String MY_ALBUM = "my_album";

    public static final String COLLECT_SONG_SHEET_ONLINE = "collect_song_sheet_online";
    public static final String COLLECT_SONG_SHEET_LOCAL = "collect_song_sheet_local";

    public static final String SONG_SHEET_NAME = "song_sheet_name";

    public static final String CATEGORY = "category";

    public static final String SONG_SHEET_TAG = "song_sheet_tag";

    public static final String SONGSHEET = "songsheet";

    public static final String ANCHORRADIO = "anchorradio";
    public static final String ALBUM = "album";
    public static final String NEWMUSIC = "newmusic";

    public static final String SEARCH_LIST = "search_list";

    public static final String INDEX = "index";

    public static final int SONG_SHEET_TAG_CODE = 1001;

    public static final int PLAY = 0;
    public static final int STOP = 1;


    public static final String BROADCAST_NAME = "somebody_z.me.zuimusic.broadcast";
    public static final String SERVICE_NAME = "somebody_z.me.zuimusic.service.MediaService";
    public static final String BROADCAST_QUERY_COMPLETE_NAME = "somebody_z.me.zuimusic.querycomplete.broadcast";
    public static final String BROADCAST_CHANGEBG = "somebody_z.me.zuimusic.changebg";
    public static final String BROADCAST_SHAKE = "somebody_z.me.zuimusic.shake";

    // 是否开启了振动模式
    public static final String SHAKE_ON_OFF = "SHAKE_ON_OFF";

    public static final String SP_NAME = "somebody_z.me.zuimusic_preference";
    public static final String SP_BG_PATH = "bg_path";
    public static final String SP_SHAKE_CHANGE_SONG = "shake_change_song";
    public static final String SP_AUTO_DOWNLOAD_LYRIC = "auto_download_lyric";
    public static final String SP_FILTER_SIZE = "filter_size";
    public static final String SP_FILTER_TIME = "filter_time";

    public final static int REFRESH_PROGRESS_EVENT = 0x100;

    // 播放状态
    public static final int MPS_NOFILE = -1; // 无音乐文件
    public static final int MPS_INVALID = 0; // 当前音乐文件无效
    public static final int MPS_PREPARE = 1; // 准备就绪
    public static final int MPS_PLAYING = 2; // 播放中
    public static final int MPS_PAUSE = 3; // 暂停

    // 播放模式
    public static final int MPM_LIST_LOOP_PLAY = 0; // 列表循环
    public static final int MPM_ORDER_PLAY = 1; // 顺序播放
    public static final int MPM_RANDOM_PLAY = 2; // 随机播放
    public static final int MPM_SINGLE_LOOP_PLAY = 3; // 单曲循环

    public static final String PLAY_STATE_NAME = "PLAY_STATE_NAME";
    public static final String PLAY_MUSIC_INDEX = "PLAY_MUSIC_INDEX";

    // 歌手和专辑列表点击都会进入MyMusic 此时要传递参数表明是从哪里进入的
    public static final String FROM = "from";
    public static final int START_FROM_ARTIST = 1;
    public static final int START_FROM_ALBUM = 2;
    public static final int START_FROM_LOCAL = 3;
    public static final int START_FROM_FOLDER = 4;
    public static final int START_FROM_FAVORITE = 5;

    public static final int FOLDER_TO_MYMUSIC = 6;
    public static final int ALBUM_TO_MYMUSIC = 7;
    public static final int ARTIST_TO_MYMUSIC = 8;

    public static final int MENU_BACKGROUND = 9;

    public static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

    public static final int UP_ANIMAL = 101;

    public static final int DOWN_ANIMAL = 102;
}
