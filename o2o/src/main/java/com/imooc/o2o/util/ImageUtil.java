package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.web.superadmin.AreaController;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	static Logger logger = LoggerFactory.getLogger(AreaController.class);
	public static String generateThumbnail(ImageHolder thumbnail,String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :"+relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200,200)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath + "/123.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		}catch(IOException e){
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	public static String generateNormalImg(ImageHolder thumbnail,String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :"+relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(337,640)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath + "/123.jpg")),0.25f)
			.outputQuality(0.9f).toFile(dest);
		}catch(IOException e){
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	
	/**
	 * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg
	 * 那么 home work xiangze这三个文件夹都得自动创建
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
	}
	/**
	 * 获取文件的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟 + 五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	/**
	 * 已没用
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("E:/图片/89.jpg"))
		.size(200, 200).watermark(Positions.BOTTOM_RIGHT,
				ImageIO.read(new File(basePath + "/123.jpg")),0.25f).outputQuality(0.8f)
		.toFile("E:/图片/891.jpg");
	}
	/**
	 * storePath是文件的路径还是目录的路径
	 * 如果storePath是文件路径则删除该文件
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for(int i = 0;i < files.length;i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();		
		}
	}

}
