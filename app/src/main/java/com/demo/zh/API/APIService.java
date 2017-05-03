package com.demo.zh.API;

import com.demo.zh.dto.Response;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * @author zhongzhen
 * @date 2017/5/3
 * @describe API接口
 **/
public interface APIService {
    /**
     * 菜谱大全
     *
     * @param menu   需要查询的菜谱名
     * @param dtype  返回数据的格式,xml或json，默认json
     * @param pn     数据返回起始下标
     * @param rn     数据返回条数，最大30
     * @param albums albums字段类型，1字符串，默认数组
     * @param key    使用的key值
     * @return
     */
    @FormUrlEncoded
    @POST("cook/query.php")
    Observable<Response<Object>> getMenuList(@Field("menu") String menu, @Field("dtype") String dtype, @Field("pn") String pn, @Field("rn") String rn, @Field("albums") String albums, @Field("key") String key);

    /**
     * 菜谱大全
     * @param map  参数map
     * @return
     */
    @Multipart
    @POST("cook/query.php")
    Observable<Response<Object>> getMenuList(@PartMap Map<String, RequestBody> map);
}
