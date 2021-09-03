
package com.dongl.common.base;


import java.util.HashSet;

import java.util.Set;

public class FileCode {

    /**
     * 允许上传的的图片文件格式，需要与 WebSecurityConfig对应
     */
    public static final Set<String> ALLOW_UPLOAD_IMG_SUFFIX = new HashSet<>();
    static{
        ALLOW_UPLOAD_IMG_SUFFIX.add("jpg");
        ALLOW_UPLOAD_IMG_SUFFIX.add("png");
        ALLOW_UPLOAD_IMG_SUFFIX.add("jpeg");
        ALLOW_UPLOAD_IMG_SUFFIX.add("gif");
        ALLOW_UPLOAD_IMG_SUFFIX.add("mp4");
    }
}
