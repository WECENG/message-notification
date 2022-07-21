package org.message.web.controller;

/**
 * <p>
 *  获取当前登录用户
 * </p>
 *
 * @author WECENG
 * @since 2021/2/20 12:41
 */
public abstract class AbstractCurrentUserController {
    /**
     * 获取当前登录用户
     * @return
     */
    public abstract String getCurrentLoginUser();
}
