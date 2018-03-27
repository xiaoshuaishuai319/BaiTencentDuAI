package cn.xsshome.baitencentdu.baiduai.face;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpUtils;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import cn.xsshome.baitencentdu.baiduai.bean.FaceDetectBean;
import cn.xsshome.baitencentdu.baiduai.util.Base64Util;
import cn.xsshome.baitencentdu.baiduai.util.FileUtil;
import cn.xsshome.baitencentdu.baiduai.util.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class FaceDetect {
	/*
	 * age,年龄
	 * beauty,美丑打分，范围0-100，越大表示越美。
	 * expression,表情，0，不笑；1，微笑；2，大笑。
	 * faceshape,脸型置信度。face_fields包含faceshape时返回
	 * gender,性别，male男、female女
	 * glasses,是否带眼镜，0-无眼镜，1-普通眼镜，2-墨镜
	 * landmark,4个关键点位置，左眼中心、右眼中心、鼻尖、嘴中心。
	 * race,所属种族  yellow、white、black、arabs(阿拉伯)。
	 * qualities 人脸质量信息
	 */
	/**
	 * 人脸检测URL
	 */
	public static String FACE_DETECT_URL="https://aip.baidubce.com/rest/2.0/face/v1/detect?access_token=ACCESS_TOKEN";
	
	public static void main(String[] args) throws Exception {
//		AIUtil aiUtil = new AIUtil();
		//得到AccessToken
//		BDAccessToken accessToken = aiUtil.getAccessToken("", "");
		String url = FACE_DETECT_URL.replace("ACCESS_TOKEN","24.2b2a767c16f644ac01cad9afbf258060.2592000.1522986527.282335-9400741");
        String filePath ="C:/Users/Administrator/Documents/Tencent Files/783021975/FileRecv/234.png";
		String result = faceDetect(url,filePath,"1","age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
		System.out.println(result);
		JSON json = JSON.parseObject(result);
		FaceDetectBean bean = JSONObject.toJavaObject(json, FaceDetectBean.class);
		System.out.println("美丑打分:"+bean.getResult().get(0).getBeauty());
	}
	public static String detect() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
        try {
            // 本地文件路径
            String filePath = "C:/Users/Administrator/Documents/Tencent Files/783021975/FileRecv/234.png";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "max_face_num=" + 5 + "&face_fields=" + "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities" + "&image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.2b2a767c16f644ac01cad9afbf258060.2592000.1522986527.282335-9400741";
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * 所有参数人脸检测
	 * @param url  接口地址 包含了AccessToken
	 * @param filePath 图片路径数据，图片大小不超过2M。
	 * @param max_face_num 最多处理人脸数目，默认值1
	 * @param face_fields 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度。
	 * @return
	 * @throws Exception 
	 */
	public static String faceDetect(String url,String filePath,String max_face_num,String face_fields) throws Exception {
		byte[] imgData = FileUtil.readFileByBytes(filePath);
        String imgStr = Base64Util.encode(imgData);
		Map<String, String> headers = new HashMap<String, String>(); 
		Map<String, String> bodys= new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		bodys.put("image", imgStr);
		bodys.put("max_face_num", max_face_num);
		bodys.put("face_fields", face_fields);
		String result  = HttpUtil.post(url,"", bodys.toString());
		return result;
	}
	/**
	 * 默认参数人脸检测
	 * @param url  接口地址 包含了AccessToken
	 * @param image base64编码后的图片数据，图片大小不超过2M。
	 * @return
	 * @throws Exception 
	 */
	public static String faceDetect(String url,String filePath) throws Exception {
		byte[] imgData = FileUtil.readFileByBytes(filePath);
        String imgStr = Base64Util.encode(imgData);
		Map<String, String> headers = new HashMap<String, String>(); 
		Map<String, String> bodys= new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		bodys.put("image", imgStr);
		String result  = HttpUtil.post(url,"", bodys.toString());
		return result;
	}
}
