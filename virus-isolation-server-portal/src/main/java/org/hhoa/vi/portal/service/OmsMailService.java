package org.hhoa.vi.portal.service;


import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.MailType;

/**
 * The interface Ums mail service.
 *
 * @author hhoa
 * @date 2022 /5/15
 */
public interface OmsMailService {
    /**
     * 获取消息
     *
     * @param to   接收方
     * @param type the type
     * @return 消息 auth code
     */
    String getMessage(String to, MailType type);

    /**
     * Send user register mail.
     *
     * @param to the to
     * @return string authcode
     */
    String sendUserRegisterMail(String to);

    /**
     * Send bind mail string.
     *
     * @param to the to
     * @return the string
     */
    String sendBindMail(String to);

    /**
     * Send organization invitation.
     *
     * @param to      the to
     * @param organization the organization
     */
    void sendOrganizationInvitation(String to, OmsOrganization organization);

    /**
     * 是否存在消息
     *
     * @param to   接收方
     * @param type the type
     * @return boolean boolean
     */
    boolean existMessage(String to, MailType type);

    /**
     * 验证消息
     *
     * @param to   接收方
     * @param code 消息
     * @param type the type
     * @return boolean boolean
     */
    boolean validateMessage(String to, String code, MailType type);
}
