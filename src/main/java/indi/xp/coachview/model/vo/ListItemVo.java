package indi.xp.coachview.model.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import indi.xp.common.utils.CollectionUtils;
import indi.xp.common.utils.StringUtils;

public class ListItemVo implements Serializable {

    private static final long serialVersionUID = -2483867480088185131L;

    private String id;
    private String name;
    private Map<String, Object> extra; // 扩展字段

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "ListItemVo [id=" + id + ", name=" + name + ", extra=" + JSON.toJSONString(extra) + "]";
    }

    public void addExtra(String key, Object value) {
        if (CollectionUtils.isEmpty(extra)) {
            extra = new HashMap<String, Object>();
        }
        extra.put(key, value);
    }

    /**
     * 获取field中扩展字段的值
     */
    public String getExtraStringValue(String key) {
        return String.valueOf(this.getExtraValue(key));
    }

    /**
     * 获取field中扩展字段的值
     */
    public Object getExtraValue(String key) {
        Object value = null;
        if (StringUtils.isNotBlank(key)) {
            Map<String, Object> extra = this.getExtra();
            if (extra != null && extra.containsKey(key)) {
                value = extra.get(key);
            }
        }
        return value;
    }

}
