package com.demo.zh.API;

import com.demo.zh.utils.RetrofitUtil;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author zhongzhen
 * @date 2017/5/3
 * @describe  api接口请求数据
 **/
public class ApiWrapper extends RetrofitUtil {
    /**
     * 菜谱大全
     * @param menu   需要查询的菜谱名
     * @param dtype  返回数据的格式,xml或json，默认json
     * @param pn     数据返回起始下标
     * @param rn     数据返回条数，最大30
     * @param albums albums字段类型，1字符串，默认数组
     * @param key    使用的key值
     * @return
     */
    public Observable<Object> getMenuList(String menu, String dtype, String pn, String rn, String albums, String key) {
        return getService().getMenuList(menu, dtype, pn, rn, albums, key).compose(this.<Object>applySchedulers());
    }

    /**
     * 菜谱大全
     * @param map   参数集合
     * @return
     */
    public Observable<Object> getMenuList(Map<String, RequestBody> map) {
        return getService().getMenuList(map).compose(this.<Object>applySchedulers());
    }
}
