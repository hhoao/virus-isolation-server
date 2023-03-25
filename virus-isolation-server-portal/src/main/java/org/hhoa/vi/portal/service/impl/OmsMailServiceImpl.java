package org.hhoa.vi.portal.service.impl;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hhoa.vi.common.exception.Asserts;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.MailType;
import org.hhoa.vi.portal.service.OmsMailCacheService;
import org.hhoa.vi.portal.service.OmsMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author hhoa
 * @date 2022/5/15
 **/
@Service
@Data
@RequiredArgsConstructor
public class OmsMailServiceImpl implements OmsMailService {
    /**
     * 邮件类型
     */
    private final MailSender mailSender;
    private final OmsMailCacheService mailCacheService;
    /**
     * 发送方邮箱
     */
    @Value("${project.mail.from}")
    private String from;

    /**
     * 发送要缓存的信息
     * @param to 接收方
     * @param subject 主题
     * @param body 消息体
     * @param cacheBody 需要缓存的消息
     * @param typeId 类型Id
     */
    private void sendTerminableMessage(String to, String subject, String body, String cacheBody, String typeId) {
        sendMessage(to, subject, body);
        mailCacheService.setMailMessage(from, to, cacheBody, typeId);
    }

    /**
     * 发送普通消息
     * @param to 接收方
     * @param subject 主题
     * @param body 消息体
     */
    private void sendMessage(String to, String subject, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(this.from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
    }
    @Override
    public String sendUserRegisterMail(String to){
        if (existMessage(to, MailType.USER_REGISTER)){
            Asserts.fail("短时间内不能再验证码");
        }
        String authCode = generateAuthCode();
        String subject = "注册验证码";
        String body = "您的验证码为" + "\n" + authCode;
        sendTerminableMessage( to, subject, body, authCode, MailType.USER_REGISTER.toString());
        return authCode;
    }
    @Override
    public String sendBindMail(String to){
        if (existMessage(to, MailType.BIND_EMAIL)){
            Asserts.fail("短时间内不能再验证码");
        }
        String authCode = generateAuthCode();
        String subject = "邮箱验证码";
        String body = "您的验证码为" + "\n" + authCode;
        sendTerminableMessage( to, subject, body, authCode, MailType.BIND_EMAIL.toString());
        return authCode;
    }

    @Override
    public void sendOrganizationInvitation(String to, OmsOrganization factory){
        if (existMessage(to, MailType.FACTORY_INVITATION)){
            Asserts.fail("短时间内不能再验证码");
        }
        String subject = factory.getName()+ "邀请您加入我们工厂";
        String body =
                factory.getName()+ "邀请您加入我们工厂" + "\n" +
                "点击下方链接接受邀请: " + "\n" +
                "http://localhost:8080/factories/" + factory.getId() + "/invitations";
        sendTerminableMessage( to, subject, body, null, MailType.FACTORY_INVITATION.toString());
    }
    @Override
    public boolean existMessage(String to, MailType type){
        return mailCacheService.existMessage(from, to, type.toString());
    }

    @Override
    public boolean validateMessage(String to, String code, MailType type) {
        return getMessage(to, type).equals(code);
    }

    @Override
    public String getMessage(String to, MailType type) {
        String mailAuthCode = mailCacheService.getMailMessage(from, to, type.toString());
        if (mailAuthCode == null){
            Asserts.fail("没有该验证码或验证码已失效");
        }
        return mailAuthCode;
    }

    private String generateAuthCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
