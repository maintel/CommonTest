/*
 * Copyright (C) 2012 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.a17zuoye.zxing.camera.open;

import android.hardware.Camera;
import android.util.Log;

import com.a17zuoye.zxing.CaptureActivityHandler;
import com.a17zuoye.zxing.R;

/**
 * Abstraction over the {@link Camera} API that helps open them and return their metadata.
 * <p>
 * by jieyu.chen
 * <p>
 * 因为打开相机比较耗时，所以放在子线程中来
 */
@SuppressWarnings("deprecation") // camera APIs
public final class OpenCameraInterface extends Thread {

    private static final String TAG = OpenCameraInterface.class.getName();

    private OpenCamera openCamera;

    private CaptureActivityHandler handler;

    public void setHandler(CaptureActivityHandler handler) {
        this.handler = handler;
    }

    public OpenCamera getOpenCamera() {
        return openCamera;
    }

    private OpenCamera open() {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            Log.w(TAG, "No cameras!");
            return null;
        }

        int cameraId = 0;
        while (cameraId < numCameras) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (CameraFacing.values()[cameraInfo.facing] == CameraFacing.BACK) {
                break;
            }
            cameraId++;
        }
        if (cameraId == numCameras) {
            Log.i(TAG, "No camera facing " + CameraFacing.BACK + "; returning camera #0");
            cameraId = 0;
        }

        Log.i(TAG, "Opening camera #" + cameraId);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        Camera camera = Camera.open(cameraId);
        if (camera == null) {
            return null;
        }
        return new OpenCamera(cameraId,
                camera,
                CameraFacing.values()[cameraInfo.facing],
                cameraInfo.orientation);
    }

    @Override
    public void run() {
        try {
            openCamera = open();
        } catch (Exception e) {
            openCamera = null;
        }
        handler.sendEmptyMessage(R.id.open_camera_complete);
    }
}
