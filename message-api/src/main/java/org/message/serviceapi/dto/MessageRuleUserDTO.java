package org.message.serviceapi.dto;

import java.io.Serializable;

/**
 * @author WECENG
 * @since 2020年10月23日
 *
 **/
public class MessageRuleUserDTO implements Serializable {

    private static final long serialVersionUID = -9123981393311530372L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名、昵称
     */
    private String userName;

    public MessageRuleUserDTO() {
    }

    public MessageRuleUserDTO(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
