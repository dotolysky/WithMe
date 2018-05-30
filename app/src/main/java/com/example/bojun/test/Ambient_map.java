package com.example.bojun.test;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class Ambient_map extends NMapActivity {
    private NMapView mMapView;
    private NMapError errorinfo;
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapPlacemark mMapPlacemark;
    private final String CLIENT_ID = "WC5uUXH6tUD_1Hr9a7V9";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = new NMapView(this);
        mMapController = mMapView.getMapController();
        mMapController.setMapCenter(new NGeoPoint(127.0630205, 37.5091300), 50);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(getApplicationContext());
        mOverlayManager = new NMapOverlayManager(getApplicationContext(), mMapView, mMapViewerResourceProvider);
        setContentView(mMapView);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setBuiltInZoomControls(true, null);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();


        int markId = NMapPOIflagType.PIN;

        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        NMapPOIitem item =  poiData.addPOIitem(37.883342, 127.739054, "헤라 PC방", markId, 0);
        item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
        poiData.addPOIitem(127.739715, 37.883403, "이리오너라 PC방", markId, 0);
        poiData.addPOIitem(127.738598, 37.883528, "헤라 PC방", markId, 0);
        poiData.addPOIitem(127.737755, 37.883190, "블랙스미스 PC방", markId, 0);
        poiData.addPOIitem(127.736957, 37.887457, "한림대학교 농구장", markId, 0);
        poiData.addPOIitem(127.738613, 37.884400, "한림대학교 레크레이션센터", markId, 0);
        poiData.addPOIitem(127.739766, 37.887650, "한림대학교 운동장", markId, 0);
        poiData.addPOIitem(127.735983, 37.882278,  "큐플레이스 카페", markId, 0);
        poiData.addPOIitem(127.735348, 37.881603,  "그라시아 카페", markId, 0);
        poiData.addPOIitem(127.739458, 37.883225, "탐앤탐스 카페", markId, 0);
        poiData.addPOIitem(127.739157, 37.883121, "맘스터치 패스트푸드점", markId, 0);
        poiData.addPOIitem(127.739026, 37.883712, "별채식당 한식점", markId, 0);
        poiData.addPOIitem(127.739879, 37.883376, "아방궁 중식점", markId, 0);
        poiData.addPOIitem(127.739413, 37.883113, "피플 호프집", markId, 0);
        poiData.addPOIitem(127.739537, 37.882713, "하지메 선술집", markId, 0);
        poiData.addPOIitem(127.741037, 37.883519, "29포차 포장마차", markId, 0);

        poiData.endPOIdata();
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);

    }
    @SuppressLint("LongLogTag")
    public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
        if (errorInfo == null) { // success
            mMapController.setMapCenter(new NGeoPoint(127.0630205, 37.5091300), 100);
        } else { // fail
            Log.e(LOCATION_SERVICE, "onMapInitHandler: error=" + errorInfo.toString());
        }
    }
    public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
        if (item != null) {
            Log.i(LOCATION_SERVICE, "onFocusChanged: " + item.toString());
        } else {
            Log.i(LOCATION_SERVICE, "onFocusChanged: ");
        }
    }
    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {
        // set your callout overlay
        return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
    }

}
