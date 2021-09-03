package com.dongl.common.utils;


import com.dongl.common.base.FileCode;
import com.dongl.common.exception.BusinessException;


public class FileUtil {


	/**
	 * 获取文件的后缀名
	 * @param appendDot 是否拼接.
	 * @return
	 */
	public static String getFileSuffix(String fullFileName, boolean appendDot){
		if(fullFileName == null || fullFileName.indexOf(".") < 0 || fullFileName.length() <= 1) {
            return "";
        }
		return (appendDot? "." : "") + fullFileName.substring(fullFileName.lastIndexOf(".") + 1);
	}


	/** 获取有效的图片格式， 返回null： 不支持的图片类型 **/
	public static String getImgSuffix(String filePath){

		String suffix = getFileSuffix(filePath, false).toLowerCase();
		if(FileCode.ALLOW_UPLOAD_IMG_SUFFIX.contains(suffix)){
			return suffix;
		}
		throw new BusinessException("10003",  "不支持的图片类型");
	}

}
