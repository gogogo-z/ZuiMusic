package somebody_z.me.zuimusic.mvp.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import somebody_z.me.zuimusic.IMediaService;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.ActivityController;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.UserInfo;
import somebody_z.me.zuimusic.db.UserInfoManager;
import somebody_z.me.zuimusic.mvp.base.BackHandlerHelper;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.LoginEvent;
import somebody_z.me.zuimusic.mvp.view.activity.impl.playBarVisible;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.friend.FriendsFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.MusicFragment;
import somebody_z.me.zuimusic.service.IOnServiceConnectComplete;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.MusicTimer;
import somebody_z.me.zuimusic.utils.MusicUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.transformation.BlurTransformation;
import somebody_z.me.zuimusic.utils.transformation.CircleTransformation;
import somebody_z.me.zuimusic.widget.songlistdialog.SongListDialog;

/**
 * 主页容器
 * Created by Huanxing Zeng on 2016/12/28.
 * email : zenghuanxing123@163.com
 */
public class HomeActivity extends BaseActivity implements topBarVisible, IOnServiceConnectComplete, playBarVisible {

    @Bind(R.id.iv_home_menu)
    ImageView ivHomeMenu;
    @Bind(R.id.rb_home_discover)
    RadioButton rbHomeDiscover;
    @Bind(R.id.rb_home_music)
    RadioButton rbHomeMusic;
    @Bind(R.id.rb_home_friends)
    RadioButton rbHomeFriends;
    @Bind(R.id.rg_home)
    RadioGroup rgHome;
    @Bind(R.id.iv_home_search)
    ImageView ivHomeSearch;
    @Bind(R.id.rl_home_top)
    RelativeLayout rlHomeTop;
    @Bind(R.id.fl_home_content)
    FrameLayout flHomeContent;
    @Bind(R.id.ll_home_bottom)
    LinearLayout llHomeBottom;
    @Bind(R.id.iv_home_bottom_cover)
    ImageView ivHomeBottomCover;
    @Bind(R.id.rl_home_bottom_play_list)
    RelativeLayout rlHomeBottomPlayList;
    @Bind(R.id.iv_home_bottom_play_status)
    ImageView ivHomeBottomPlayStatus;
    @Bind(R.id.rl_home_bottom_play_status)
    RelativeLayout rlHomeBottomPlayStatus;
    @Bind(R.id.iv_home_bottom_play_next)
    ImageView ivHomeBottomPlayNext;
    @Bind(R.id.rl_home_bottom_play_next)
    RelativeLayout rlHomeBottomPlayNext;
    @Bind(R.id.ll_home_bottom_control)
    LinearLayout llHomeBottomControl;
    @Bind(R.id.tv_home_bottom_title)
    TextView tvHomeBottomTitle;
    @Bind(R.id.tv_home_bottom_artist)
    TextView tvHomeBottomArtist;
    @Bind(R.id.ll_home_bottom_info)
    LinearLayout llHomeBottomInfo;
    @Bind(R.id.pb_home)
    ProgressBar pbHome;
    @Bind(R.id.nv_home)
    NavigationView nvHome;
    @Bind(R.id.dl_home)
    DrawerLayout dlHome;
    @Bind(R.id.rl_home_option)
    RelativeLayout rlHomeOption;
    @Bind(R.id.rl_home_exit)
    RelativeLayout rlHomeExit;

    private Fragment[] fragments;

    private int lastIndex;

    private ServiceManager mServiceManager;

    private MusicPlayBroadcast mPlayBroadcast;

    private int playState;

    public Handler mHandler;

    private MusicTimer mMusicTimer;

    private Bitmap mDefaultAlbumIcon;

    private SharedPreferences sp;

    private SharedPreferences.Editor editor;// 获取编辑器

    private boolean isOpen = false;

    private View headView_login, headView_unlogin, headView_first_login;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        fragments = new Fragment[]{DiscoverFragment.getInstance(), MusicFragment.getInstance(), FriendsFragment.getInstance()};

        // 获取事务管理器
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 开始事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.length; i++) {
            // 先添加
            transaction.add(R.id.fl_home_content, fragments[i]);
            // 后隐藏
            transaction.hide(fragments[i]);
        }

        // 默认显示第一个
        transaction.show(fragments[0]);
        // 事务提交
        transaction.commit();

        mServiceManager = MyApplication.mServiceManager;

        MyApplication.mServiceManager.connectService();
        MyApplication.mServiceManager.setOnServiceConnectComplete(this);

        mPlayBroadcast = new MusicPlayBroadcast();
        IntentFilter filter = new IntentFilter(Constants.BROADCAST_NAME);
        filter.addAction(Constants.BROADCAST_NAME);
        registerReceiver(mPlayBroadcast, filter);

        mMusicTimer = new MusicTimer(mHandler);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //注册订阅者
        EventBus.getDefault().register(this);

        DownLoadManager.getInstance().register(this);
    }

    @Override
    protected void initListener() {
        rgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.rb_home_discover:
                        index = 0;
                        break;

                    case R.id.rb_home_music:
                        index = 1;
                        break;

                    case R.id.rb_home_friends:
                        index = 2;
                        break;

                    default:
                        index = 0;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.hide(fragments[lastIndex]);
                transaction.show(fragments[index]);

                transaction.commit();
                lastIndex = index;
            }
        });

        nvHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()) {
                    // 我的消息
                    case R.id.menu_my_msg:
                        break;

                    // 会员中心
                    case R.id.menu_vip:
                        break;

                    // 商城
                    case R.id.menu_shop:
                        break;

                    // 在线听歌免流量
                    case R.id.menu_online_listen:
                        break;

                    // 听歌识曲
                    case R.id.menu_listen_to_know:
                        break;

                    // 主题换肤
                    case R.id.menu_theme:
                        break;

                    // 夜间模式
                    case R.id.menu_night_mode:
                        break;

                    // 定时停止播放
                    case R.id.menu_stop_play:
                        break;

                    // 扫一扫
                    case R.id.menu_scan:
                        break;

                    // 音乐闹钟
                    case R.id.menu_music_alarm_clock:
                        break;

                    // 驾驶模式
                    case R.id.menu_drive:
                        break;

                }

                dlHome.closeDrawers();
                return true;
            }
        });

        dlHome.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    @Override
    protected void initView() {

        sp = getSharedPreferences("Bottom_Bar", Activity.MODE_PRIVATE);
        editor = sp.edit();// 获取编辑器

        //设置底部播放栏的透明度
        llHomeBottom.setAlpha((float) 0.95);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llHomeBottomControl.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth(this) * 11 / 30;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                refreshSeekProgress(mServiceManager.position(),
                        mServiceManager.duration());
            }
        };

        mDefaultAlbumIcon = BitmapFactory.decodeResource(getResources(), R.drawable.note_default_cover);

        boolean isLogin = sp.getBoolean("isLogin", false);
        String platform = sp.getString("platform", null);

        if (isLogin) {

            String user_icon = null;
            String user_name = null;

            switch (platform) {
                case "qq":
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);

                    //判断指定平台是否已经完成授权,如果已经授权则直接登录
                    if (qq.isAuthValid()) {
                        String userId = qq.getDb().getUserId();
                        if (userId != null) {
                            user_icon = qq.getDb().getUserIcon();
                            user_name = qq.getDb().getUserName();
                        }
                        addLoginHeadView(user_icon, user_name);
                    } else {
                        addUnLoginHeadView();
                        toast("QQ授权信息已过期，请重新登录");
                    }
                    break;
                case "weixin":
                    Platform weixin = ShareSDK.getPlatform(Wechat.NAME);

                    //判断指定平台是否已经完成授权,如果已经授权则直接登录
                    if (weixin.isAuthValid()) {
                        String userId = weixin.getDb().getUserId();
                        if (userId != null) {
                            user_icon = weixin.getDb().getUserIcon();
                            user_name = weixin.getDb().getUserName();
                        }
                        addLoginHeadView(user_icon, user_name);
                    } else {
                        addUnLoginHeadView();
                        toast("微信授权信息已过期，请重新登录");
                    }
                    break;
                case "weibo":
                    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);

                    //判断指定平台是否已经完成授权,如果已经授权则直接登录
                    if (weibo.isAuthValid()) {
                        String userId = weibo.getDb().getUserId();
                        if (userId != null) {
                            user_icon = weibo.getDb().getUserIcon();
                            user_name = weibo.getDb().getUserName();
                        }
                        addLoginHeadView(user_icon, user_name);
                    } else {
                        addUnLoginHeadView();
                        toast("微博授权信息已过期，请重新登录");
                    }
                    break;
                case "neteasy":
                    Platform neteasy = ShareSDK.getPlatform(QZone.NAME);

                    //判断指定平台是否已经完成授权,如果已经授权则直接登录
                    if (neteasy.isAuthValid()) {
                        String userId = neteasy.getDb().getUserId();
                        if (userId != null) {
                            user_icon = neteasy.getDb().getUserIcon();
                            user_name = neteasy.getDb().getUserName();
                        }
                        addLoginHeadView(user_icon, user_name);
                    } else {
                        addUnLoginHeadView();
                        toast("网易邮箱授权信息已过期，请重新登录");
                    }
                    break;
                case "local":

                    List<UserInfo> userInfoList = UserInfoManager.getInstance().getUserList(this);
                    UserInfo userInfo = userInfoList.get(userInfoList.size() - 1);

                    user_name = "用户" + userInfo.getUserName();
                    addLoginHeadView(user_icon, user_name);

                    break;
                default:
                    List<UserInfo> userList = UserInfoManager.getInstance().getUserList(this);
                    UserInfo user_info = userList.get(userList.size() - 1);

                    user_name = "用户" + user_info.getUserName();
                    addLoginHeadView(user_icon, user_name);
                    break;
            }
        } else {
            addUnLoginHeadView();
        }

        //隐藏滚动条
        removeNavigationViewScrollbar(nvHome);

        //让图片显示本来的颜色
        nvHome.setItemIconTintList(null);
    }

    private void addLoginHeadView(String user_icon, String user_name) {
        headView_login = View.inflate(this, R.layout.drawer_head_login, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenHeight(this) / 4 + ScreenUtil.getStatusBarHeight(this));
        headView_login.setLayoutParams(layoutParams);

        ImageView ivUser = (ImageView) headView_login.findViewById(R.id.iv_user_icon);
        ImageView ivBg = (ImageView) headView_login.findViewById(R.id.iv_draw_head_login_bg);

        TextView tvUser = (TextView) headView_login.findViewById(R.id.tv_user_name);

        tvUser.setText(user_name);

        //设置滤镜
        ivBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //使用Glide第三方框架加载图片
        Glide.with(this)
                .load(user_icon)
                .placeholder(R.drawable.default_user_icon)//显示默认图片
                .bitmapTransform(new CircleTransformation(this))
                .into(ivUser);

        Glide.with(this)
                .load(user_icon)
                .placeholder(R.drawable.default_bg)//显示默认图片
                .bitmapTransform(new BlurTransformation(this))
                .into(ivBg);


        nvHome.addHeaderView(headView_login);
    }

    private void addUnLoginHeadView() {
        headView_unlogin = View.inflate(this, R.layout.drawer_head_unlogin, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenHeight(this) / 4 + ScreenUtil.getStatusBarHeight(this));
        headView_unlogin.setLayoutParams(layoutParams);

        //点击登录
        headView_unlogin.findViewById(R.id.btn_drawer_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlHome.closeDrawers();
                //跳转登录
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        nvHome.addHeaderView(headView_unlogin);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void hideBar() {
        rlHomeTop.setVisibility(View.GONE);
    }

    @Override
    public void showBar() {
        rlHomeTop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlayBar() {
        llHomeBottom.setVisibility(View.GONE);
    }

    @Override
    public void showPlayBar() {
        llHomeBottom.setVisibility(View.VISIBLE);
    }

    /**
     * 拦截返回键，设置返回事件
     */
    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if (isOpen) {
                //关闭drawerlayout
                dlHome.closeDrawers();
            } else {
                //回到桌面
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        }
    }

    private void removeNavigationViewScrollbar(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @OnClick({R.id.iv_home_menu, R.id.iv_home_search, R.id.rl_home_bottom_play_list, R.id.rl_home_bottom_play_status,
            R.id.rl_home_bottom_play_next, R.id.ll_home_bottom, R.id.rl_home_option, R.id.rl_home_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home_menu:
                dlHome.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_home_search:
                //搜索
                Intent intent2 = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent2);
                //设置跳转无动画
                overridePendingTransition(0, 0);

                break;
            case R.id.rl_home_bottom_play_list:
                //播放列表
                new SongListDialog().showDialog(this);
                break;
            case R.id.rl_home_bottom_play_status:
                switch (playState) {
                    case Constants.MPS_PAUSE:
                        mServiceManager.rePlay();
                        break;
                    case Constants.MPS_PLAYING:
                        mServiceManager.pause();
                        break;
                }
                break;
            case R.id.rl_home_bottom_play_next:
                mServiceManager.next();
                break;
            case R.id.ll_home_bottom:
                Intent intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_home_option:
                //关闭drawerlayout
                dlHome.closeDrawers();
                //设置
                Intent intent1 = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent1);

                break;
            case R.id.rl_home_exit:
                //退出应用
                exit();
                break;
        }
    }

    @Override
    public void onServiceConnectComplete(IMediaService service) {
        // service绑定成功会执行到这里
        boolean isPlayed = sp.getBoolean("played", false);

        if (isPlayed) {
            showPlayBar();
            //填充内容
            String title = sp.getString("title", null);
            String song_id = sp.getString("song_id", null);
            String author = sp.getString("author", null);
            String album_title = sp.getString("album_title", null);
            String album_id = sp.getString("album_id", null);
            String localUrl = sp.getString("localUrl", null);
            int has_mv = sp.getInt("has_mv", 0);
            String pic_url = sp.getString("pic_url", null);
            String lrc_url = sp.getString("lrc_url", null);

            int position = sp.getInt("position", 0);
            int duration = sp.getInt("duration", 0);

            int play_mode = sp.getInt("play_mode", 0);

            ContentBean lastMusic = new ContentBean(title, song_id, author, album_title, album_id, localUrl, has_mv, pic_url, lrc_url);

            List<ContentBean> songList = new ArrayList<>();

            songList.add(lastMusic);

            mServiceManager.refreshMusicList(songList);

            mServiceManager.play(0);

            mServiceManager.setPlayMode(play_mode);

            refreshPlayBar(position, duration, lastMusic);

            showPlay(true);

            mServiceManager.seekTo((position * 100) / duration);

            mServiceManager.pause();
        } else {
            hidePlayBar();
        }
    }

    private class MusicPlayBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.BROADCAST_NAME)) {
                ContentBean music = new ContentBean();
                playState = intent.getIntExtra(Constants.PLAY_STATE_NAME, Constants.MPS_NOFILE);
                int curPlayIndex = intent.getIntExtra(Constants.PLAY_MUSIC_INDEX, -1);
                Bundle bundle = intent.getBundleExtra(ContentBean.KEY_MUSIC);
                if (bundle != null) {
                    music = bundle.getParcelable(ContentBean.KEY_MUSIC);
                }

                switch (playState) {
                    case Constants.MPS_INVALID:// 考虑后面加上如果文件不可播放直接跳到下一首
                        mMusicTimer.stopTimer();
                        refreshPlayBar(0, mServiceManager.duration(), music);
                        showPlay(true);
                        break;
                    case Constants.MPS_PAUSE:
                        mMusicTimer.stopTimer();
                        refreshPlayBar(mServiceManager.position(), mServiceManager.duration(), music);

                        showPlay(true);
                        break;
                    case Constants.MPS_PLAYING:
                        mMusicTimer.startTimer();
                        refreshPlayBar(mServiceManager.position(), mServiceManager.duration(), music);

                        showPlay(false);
                        break;
                    case Constants.MPS_PREPARE:
                        mMusicTimer.stopTimer();
                        refreshPlayBar(0, mServiceManager.duration(), music);
                        showPlay(true);
                        break;
                }

                if (music.getPic_url() == null) {
                    if (music.getAlbum_id() != null) {
                        Long album_id = Long.valueOf(music.getAlbum_id());
                        Bitmap bitmap = MusicUtils.getCachedArtwork(HomeActivity.this, album_id, mDefaultAlbumIcon);
                        mServiceManager.updateNotification(bitmap, music.getTitle(), music.getAuthor());
                    }
                } else {

                    final ContentBean music_back = music;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //使用Glide通过url获取bitmap
                                Bitmap bitmap = Glide.with(HomeActivity.this)
                                        .load(music_back.getPic_url())
                                        .asBitmap() //必须
                                        .centerCrop()
                                        .into(200, 200)
                                        .get();
                                mServiceManager.updateNotification(bitmap, music_back.getTitle(), music_back.getAuthor());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }

                //收到播放的广播，显示底部控制栏，其他fragment中的控制显示可以去掉
                showPlayBar();

            }
        }
    }

    private void refreshPlayBar(int curTime, int totalTime, ContentBean music) {
        tvHomeBottomTitle.setText(music.getTitle());
        tvHomeBottomArtist.setText(music.getAuthor());

        if (music.getPic_url() == null) {
            //使用Glide第三方框架加载图片
            Glide.with(this)
                    .load(MusicUtils.getCoverUri(Long.valueOf(music.getAlbum_id())))
                    .placeholder(R.drawable.note_default_cover)//显示默认图片
                    .into(ivHomeBottomCover);
        } else {
            Glide.with(this)
                    .load(music.getPic_url())
                    .placeholder(R.drawable.note_default_cover)//显示默认图片
                    .into(ivHomeBottomCover);
        }


        refreshSeekProgress(curTime, totalTime);
    }

    public void refreshSeekProgress(int curTime, int totalTime) {
        curTime /= 1000;
        totalTime /= 1000;

        int rate = 0;
        if (totalTime != 0) {
            rate = (int) ((float) curTime / totalTime * 100);
        }

        pbHome.setProgress(rate);
    }

    public void showPlay(boolean flag) {
        if (flag) {
            ivHomeBottomPlayStatus.setBackgroundResource(R.drawable.playbar_btn_play);
        } else {
            ivHomeBottomPlayStatus.setBackgroundResource(R.drawable.playbar_btn_pause);
        }
    }

    String user_icon = null;
    String user_name = null;

    /**
     * 订阅事件
     * <p/>
     * 不管发布者的操作是在哪个线程，操作一律放在ＵＩ线程中执行
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        user_icon = event.getPic();
        user_name = event.getUser_name();

        if (event.getPlatform().equals("local")) {
            user_name = "用户" + user_name;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (headView_login != null) {
                    nvHome.removeHeaderView(headView_login);
                }

                if (headView_unlogin != null) {
                    nvHome.removeHeaderView(headView_unlogin);
                }

                if (headView_first_login != null) {
                    nvHome.removeHeaderView(headView_first_login);
                }

                headView_first_login = View.inflate(HomeActivity.this, R.layout.drawer_head_login, null);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ScreenUtil.getScreenHeight(HomeActivity.this) / 4 + ScreenUtil.getStatusBarHeight(HomeActivity.this));
                headView_first_login.setLayoutParams(layoutParams);

                ImageView ivUser = (ImageView) headView_first_login.findViewById(R.id.iv_user_icon);
                ImageView ivBg = (ImageView) headView_first_login.findViewById(R.id.iv_draw_head_login_bg);

                TextView tvUser = (TextView) headView_first_login.findViewById(R.id.tv_user_name);

                tvUser.setText(user_name);

                //设置滤镜
                ivBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                //使用Glide第三方框架加载图片
                Glide.with(HomeActivity.this)
                        .load(user_icon)
                        .placeholder(R.drawable.icon_default)//显示默认图片
                        .bitmapTransform(new CircleTransformation(HomeActivity.this))
                        .into(ivUser);

                Glide.with(HomeActivity.this)
                        .load(user_icon)
                        .placeholder(R.drawable.default_bg)//显示默认图片
                        .bitmapTransform(new BlurTransformation(HomeActivity.this))
                        .into(ivBg);

                nvHome.addHeaderView(headView_first_login);
            }
        });

        editor.putString("platform", event.getPlatform());
        editor.putBoolean("isLogin", true);
        editor.commit();

    }

    @Subscribe
    public void onEventMainThread(String str) {

        if (str.equals("exit_account")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (headView_login != null) {
                        nvHome.removeHeaderView(headView_login);
                    }

                    if (headView_unlogin != null) {
                        nvHome.removeHeaderView(headView_unlogin);
                    }

                    if (headView_first_login != null) {
                        nvHome.removeHeaderView(headView_first_login);
                    }

                    addUnLoginHeadView();
                }
            });

            editor.putBoolean("isLogin", false);
            editor.commit();

        }

    }

    /**
     * 退出应用
     */
    private void exit() {
        if (mPlayBroadcast != null) {
            unregisterReceiver(mPlayBroadcast);
        }

        if (mServiceManager.getCurMusic() != null) {
            ContentBean music = mServiceManager.getCurMusic();

            editor.putString("title", music.getTitle());
            editor.putString("song_id", music.getSong_id());
            editor.putString("author", music.getAuthor());
            editor.putString("album_title", music.getAlbum_title());
            editor.putString("album_id", music.getAlbum_id());
            editor.putString("localUrl", music.getLocalUrl());
            editor.putInt("has_mv", music.getHas_mv());
            editor.putString("pic_url", music.getPic_url());
            editor.putString("lrc_url", music.getLrc_url());
            editor.putInt("position", mServiceManager.position());
            editor.putInt("duration", mServiceManager.duration());

            editor.putBoolean("played", true);

            editor.putInt("play_mode", mServiceManager.getPlayMode());

            editor.commit();

        }

        //注销订阅者
        EventBus.getDefault().unregister(this);

        DownLoadManager.getInstance().unRegister();

        finish();

        System.exit(0);
        //ActivityController.getInstance().exitApp();
    }

}
