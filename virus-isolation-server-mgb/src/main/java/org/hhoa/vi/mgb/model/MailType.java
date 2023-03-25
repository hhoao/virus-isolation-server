package org.hhoa.vi.mgb.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件类型
 */
public enum MailType {
    /**
     * 用户注册类型
     */
    USER_REGISTER(1),
    /**
     * 工厂邀请类型
     */
    FACTORY_INVITATION(2),
    /**
     * 修改密码类型
     */
    UPDATE_PASSWORD(3),
    /**
     * 绑定邮箱类型
     */
    BIND_EMAIL(4),

    /**
     * 绑定电话号码类型
     */
    BIND_PHONE(5);
    /**
     * 类型唯一标志
     */
    private final Integer id;
    MailType(Integer id){
        this.id = id;
    }
    public String toString() {
        return id.toString();
    }
    public Integer value() {
        return this.id;
    }

    private final static Map<Integer, MailType> map;
    static {
        map = new HashMap<>();
        for (MailType mailType : MailType.values()) {
            map.put(mailType.value(), mailType);
        }
    }
    public static MailType find(Integer value){
        return map.get(value);
    }
}
