package com.example.jorunal_bishe.been;

/**
 * Created by M02323 on 2017/9/25.
 * 图片存储路径及状态对象
 */

public class ImageModel {
	private String imagePath = ""; // 图片存放路径
	private boolean imageState = false; // 图片选中状态
	private int spareImage = 0; // 备用显示图片

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isImageState() {
		return imageState;
	}

	public void setImageState(boolean imageState) {
		this.imageState = imageState;
	}

	public int getSpareImage() {
		return spareImage;
	}

	public void setSpareImage(int spareImage) {
		this.spareImage = spareImage;
	}

	@Override
	public String toString() {
		return "ImageModel [图片路径=" + imagePath + ", 图片选中状态=" + imageState
				+ ", 备用资源图片id=" + spareImage + "]";
	}
}
