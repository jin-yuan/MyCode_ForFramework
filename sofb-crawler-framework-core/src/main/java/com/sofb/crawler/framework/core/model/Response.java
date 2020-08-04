package com.sofb.crawler.framework.core.model;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-10-16 17:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Response extends HttpResponseEntity implements Serializable {

    private static final long serialVersionUID = -8511555041129690766L;

    private String callBackName;
    /**
     * 处理返回的items
     */
    private ResultItems<List<Map<String, String>>> resultItems;

    /**
     * 推到队列requests
     */
    private List<Request> requestList;

    /**
     * 需要传递的参数
     */
    private Map<String, Object> extras;

    /**
     * 状态是否正常
     */
    private boolean isInvalidPage;

    /**
     * 状态信息
     */
    private StringBuilder msg;

    public void addRequest(Request... requests) {
        getRequestList().addAll(Arrays.asList(requests));
    }

    public List<Request> getRequestList() {

        if (requestList == null) {
            requestList = new ArrayList<>();
        }
        return requestList;
    }

    public Object getExtra(String key) {
        return getExtra(key, StringUtils.EMPTY);
    }

    public Object getExtra(String key, Object defaultValue) {
        return extras.getOrDefault(key, defaultValue);
    }

    public String getMsg() {
        if (msg == null) {
            return StringUtils.EMPTY;
        } else {
            return msg.toString();
        }
    }

    public void addMsg(String newMsg) {
        if (msg == null) {
            msg = new StringBuilder();
        }
        msg.append(newMsg);
    }


    public void clearItems() {
        this.requestList = null;
        this.resultItems = null;
    }
}
