package com.pst.mkt.ui.map;//package ui.map;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.AppCompatTextView;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.CameraUpdateFactory;
//import com.amap.api.maps2d.MapView;
//import com.amap.api.maps2d.model.*;
//import com.pst.basebata.annotation.AutoArg;
//import com.pst.basebata.base.BaseFragment;
//import com.pst.basebata.base.BaseViewModel;
//import util.GDMapUtil;
//import util.MapUtil;
//import util.MyToast;
//import ui.R;
//import ui.databinding.ResultMapNavigationFragmentBinding;
//import util.PatternUtil;
//
///**
// * 文件描述：    地图导航
// * 作者：pst
// * 邮箱：1274218999@qq.com
// * 创建时间：19-7-4 上午10:22
// * 更改时间：19-7-4
// * 版本号：1
// */
//public class ResultMapNavigationFragment extends BaseFragment<ResultMapNavigationFragmentBinding, BaseViewModel> implements AMap.OnMarkerClickListener,
//        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.InfoWindowAdapter {
//    Bundle savedInstanceState1;
//
//
//    private AMap aMap;
//    private MapView mapView;
//
//    @AutoArg
//    private Double latitude;
//    @AutoArg
//    private Double longitude;
//
//    private LatLng latlng = new LatLng(26.616661, 106.648923);//观山湖区
//    //    private final LatLng latlng = new LatLng(30.679879, 104.064855);// 成都市经纬度
//
//    @AutoArg
//    private String address = "贵州省贵阳市观山湖区都匀路85号靠近金利大厦(百挑路)";
//    @AutoArg
//    private String cityName = "";
//
//    private Context mContext;
//    private BottomDialog mBottomPhotoDialog;
//
//
//    @Override
//    public int getLayout() {
//        return R.layout.result_map_navigation_fragment;
//    }
//
//    @Override
//    public void initView() {
//
//    }
//
//    @Override
//    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        savedInstanceState1 = savedInstanceState;
//        return super.initContentView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void initData() {
//        mContext = getContext();
//        mNavTitleTv.setText("电子地图");
//        if (!PatternUtil.checkItude(longitude+"",latitude+"")){
//            MyToast.showToast("获取位置信息失败");
//        }else {
//            latlng = new LatLng(latitude, longitude);
//        }
//        binding.mapAddress.setText(address);
//        binding.mapName.setText(cityName);
//        mapView = binding.getRoot().findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState1); // 此方法必须重写
//
//        init();
//        GDMapUtil.getInstance().initLocation(mContext, new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//
//            }
//        });
//        GDMapUtil.getInstance().startLocation();
//
//        ImageView navigation = binding.getRoot().findViewById(R.id.navigation);
//        navigation.setOnClickListener(v -> {
//            showPhotoDialog();
//        });
//    }
//
//    /**
//     * 初始化AMap对象
//     */
//    private void init() {
//        if (aMap == null) {
//            aMap = mapView.getMap();
//            setUpMap();
//        }
//    }
//
//    private void setUpMap() {
//        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
//        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
//        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
//        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
//        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
////        aMap.setTrafficEnabled(true);// 显示实时交通状况
////        aMap.setMapType(AMap.MAP_TYPE_BUS);// 卫星地图模式
//
//        addMarkersToMap();// 往地图上添加marker
//    }
//
//    private void showPhotoDialog() {
//        mBottomPhotoDialog = new BottomDialog(mContext, 0, true);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.result_bottom_select_map, null, false);
//        //取消
//        AppCompatTextView cancel = view.findViewById(R.id.cancel);
//        cancel.setOnClickListener(v -> mBottomPhotoDialog.dismiss());
//
//        AppCompatTextView gaode = view.findViewById(R.id.gaode_map);
//        AppCompatTextView tencent = view.findViewById(R.id.tencent_map);
//        AppCompatTextView baidu = view.findViewById(R.id.baidu_map);
//        gaode.setOnClickListener(v -> {
//            if (MapUtil.isAvilible(mContext, MapUtil.GAODE_MAP)) {
//                MapUtil.openGaoDeNavi(mContext, 0, 0, "我的位置", latlng.latitude, latlng.longitude, "终点");
//            } else {
//                MapUtil.installMap(mContext, MapUtil.GAODE_MAP);
//            }
//            mBottomPhotoDialog.dismiss();
//        });
//        tencent.setOnClickListener(v -> {
//            if (MapUtil.isAvilible(mContext, MapUtil.TENCENT_MAP)) {
//                MapUtil.openTencentMap(mContext, 0, 0, "我的位置", latlng.latitude, latlng.longitude, "终点");
//            } else {
//                MapUtil.installMap(mContext, MapUtil.TENCENT_MAP);
//            }
//            mBottomPhotoDialog.dismiss();
//        });
//        baidu.setOnClickListener(v -> {
//            if (MapUtil.isAvilible(mContext, MapUtil.BAIDU_MAP)) {
//                MapUtil.openBaiDuNavi(mContext, 0, 0, "我的位置", latlng.latitude, latlng.longitude, "终点");
//            } else {
//                MapUtil.installMap(mContext, MapUtil.BAIDU_MAP);
//            }
//            mBottomPhotoDialog.dismiss();
//        });
//        mBottomPhotoDialog.setContentView(view);
//        // 设置背景为透明色 那么白色的就能呈现出来了
//        mBottomPhotoDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
//                .setBackgroundColor(getResources().getColor(android.R.color.transparent));
//
//        mBottomPhotoDialog.show();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//        GDMapUtil.getInstance().stopLocation();
//        GDMapUtil.getInstance().destroyLocation();
//    }
//
//    /**
//     * 在地图上添加marker
//     */
//    private void addMarkersToMap() {
//
//        aMap.addMarker(new MarkerOptions().anchor(1f, 1f)
//                .position(latlng).title(null)
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.mipmap.ic_location_1))
//                .snippet(address).draggable(true));
//    }
//
//
//    /**
//     * 对marker标注点点击响应事件
//     */
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
////        jumpPoint(marker)
//        return false;
//    }
//
//
//    /**
//     * 监听点击infowindow窗口事件回调
//     */
//    @Override
//    public void onInfoWindowClick(Marker marker) {
////		MyToast.showToast(this, "你点击了infoWindow窗口" + marker.getTitle());
//    }
//
//    /**
//     * 监听拖动marker时事件回调
//     */
//    @Override
//    public void onMarkerDrag(Marker marker) {
//        String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
//                + marker.getPosition().latitude + ","
//                + marker.getPosition().longitude + ")";
//    }
//
//    /**
//     * 监听拖动marker结束事件回调
//     */
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
////		markerText.setText(marker.getTitle() + "停止拖动");
//    }
//
//    /**
//     * 监听开始拖动marker事件回调
//     */
//    @Override
//    public void onMarkerDragStart(Marker marker) {
////		markerText.setText(marker.getTitle() + "开始拖动");
//    }
//
//    /**
//     * 监听amap地图加载成功事件回调
//     */
//    @Override
//    public void onMapLoaded() {
//        // 设置所有maker显示在当前可视区域地图中
//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(latlng).build();
//        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
//
//    }
//
//    /**
//     * 监听自定义infowindow窗口的infocontents事件回调
//     */
//    @Override
//    public View getInfoContents(Marker marker) {
//
//        View infoContent = getLayoutInflater().inflate(
//                R.layout.result_address_info, null);
//        render(marker, infoContent);
//        return infoContent;
//    }
//
//    /**
//     * 监听自定义infowindow窗口的infowindow事件回调
//     */
//    @Override
//    public View getInfoWindow(Marker marker) {
//
//        View infoWindow = getLayoutInflater().inflate(
//                R.layout.result_address_info, null);
//
//        render(marker, infoWindow);
//        return infoWindow;
//    }
//
//    /**
//     * 自定义infowinfow窗口
//     */
//    public void render(Marker marker, View view) {
//
//        ImageView imageView = (ImageView) view.findViewById(R.id.badge);
////			imageView.setImageResource(R.drawable.badge_wa);
//        String title = marker.getTitle();
//        TextView titleUi = ((TextView) view.findViewById(R.id.title));
//        if (title != null) {
//            SpannableString titleText = new SpannableString(title);
//            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
//                    titleText.length(), 0);
//            titleUi.setTextSize(14);
//            titleUi.setText(titleText);
//
//        } else {
//            titleUi.setVisibility(View.GONE);
//        }
//        String snippet = marker.getSnippet();
//        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
//        if (snippet != null) {
//            SpannableString snippetText = new SpannableString(snippet);
//            snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
//                    snippetText.length(), 0);
//            snippetUi.setTextSize(14);
//            snippetUi.setText(snippetText);
//        } else {
//            snippetUi.setVisibility(View.GONE);
//        }
//    }
//
//}
