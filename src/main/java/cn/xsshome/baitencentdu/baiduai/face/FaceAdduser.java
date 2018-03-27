package cn.xsshome.baitencentdu.baiduai.face;

import cn.xsshome.baitencentdu.baiduai.util.HttpUtil;


public class FaceAdduser {
	public static String FACE_GROUP_ADDUSER_URL="https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/adduser";
	   public static void main(String[] args) {
	        // 人脸查找——组内添加用户 url
	        String addUserUrl = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/adduser";
	        // 请求参数
	        // 用户组
	        String groupId = "test_adduser_group";
	        // 用户ID
	        String uid = "u20170720001";
	        //从指定group里复制信息
	        String src_group_id = "group1";
	        String params = "group_id=" + groupId + "&uid=" + uid+"&src_group_id="+src_group_id;
	        try {
	            /**
	             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	             */
	            String accessToken = "24.58259c506a30706f4a8da345d6bb3e0a.2592000.1503192205.282335-9639237";
	            String result = HttpUtil.post(addUserUrl, accessToken, params);
	            System.out.println(result);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
