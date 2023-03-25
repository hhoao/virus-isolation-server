package org.hhoa.vi.portal.service;

import org.hhoa.vi.common.exception.ApiException;
import org.hhoa.vi.mgb.model.AccountOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsAccountAuth;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.mgb.model.IdentifyType;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.bean.UmsAccountDetails;
import org.hhoa.vi.portal.bean.UmsAccountRegisterParam;
import org.hhoa.vi.portal.bean.UmsAccountWrapper;
import org.hhoa.vi.portal.bean.UmsLoginParam;
import org.hhoa.vi.portal.bean.UmsUpdateAccountPasswordParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The interface Ums account service.
 *
 * @author hhoa
 */
public interface UmsAccountService {

    /**
     * Login string.
     *
     * @param loginParam the login param
     * @return the string
     */
    String login(UmsLoginParam loginParam);

    /**
     * Logout.
     *
     * @param authorization the authorization
     */
    void logout(String authorization);

    /**
     * 获取AccountDetails.
     *
     * @param accountName accountName
     * @return accountDetails account details by account name
     */
    UmsAccountDetails getAccountDetails(String accountName);


    /**
     * 获取部分用户.
     *
     * @param pageInfo @return 部分用户 list
     * @param account  the account
     * @return the list
     */
    List<UmsAccount> list(PageInfo pageInfo, UmsAccount account);

    /**
     * Refresh token string.
     *
     * @param authorization the authorization
     * @return the string
     */
    String refreshToken(String authorization);

    /**
     * Gets account by accountName.
     *
     * @param accountName the accountName
     * @return the account by accountName
     */
    UmsAccount getAccountByAccountNameUseAccountDetailsCache(String accountName);

    /**
     * Gets account.
     *
     * @param account the account
     * @return the account
     */
    List<UmsAccount> getAccounts(UmsAccount account);


    /**
     * Gets account by authorization.
     *
     * @param authorization the authorization
     * @return the account by authorization
     */
    UmsAccountWrapper getAccountByAuthorization(String authorization);

    /**
     * Gets account organizations.
     *
     * @param authorization the authorization
     * @return the account organizations
     */
    List<AccountOrganization> getAccountOrganizations(String authorization);


    /**
     * Register ret account.
     *
     * @param registerParam the register param
     * @return accountId long
     */
    @Transactional
    Long register(UmsAccountRegisterParam registerParam);


    /**
     * 使用验证码更改密码
     *
     * @param passwordParam 更新密码需要的参数
     */
    @Transactional(rollbackFor = Exception.class, noRollbackFor = ApiException.class)
    void updateAccountPassword(UmsUpdateAccountPasswordParam passwordParam);


    /**
     * Gets account by accountname.
     *
     * @param username the accountname
     * @return the account by accountname
     */
    UmsAccount getAccountByUsername(String username);

    /**
     * 发送邮箱验证码
     *
     * @param mail 接收方
     */
    void sendAccountRegisterMail(String mail);

    /**
     * Gets account email by accountname.
     *
     * @param username the accountname
     * @return the account email by accountname
     */
    UmsAccountAuth getAccountEmailByUsername(String username);

    /**
     * Update account.
     *
     * @param account          the account
     * @param authorization the authorization
     */
    void updateAccount(UmsAccount account, String authorization);

    /**
     * Unbind account auth.
     *
     * @param authType      the auth type
     * @param authorization the authorization
     */
    void unbindAccountAuth(IdentifyType authType, String authorization);

    /**
     * Update newUsername.
     *
     * @param newUsername   the newUsername
     * @param authorization the authorization
     */
    void updateUsername(String newUsername, String authorization);

    /**
     * Gets account auths.
     *
     * @param accountId the account id
     * @return the account auths
     */
    Map<String, String> getAccountAuths(Long accountId);

    /**
     * Bind email.
     *
     * @param email    the email
     * @param authCode the auth code
     * @param s        the s
     */
    void bindEmail(String email, String authCode, String s);

    /**
     * Bind phone.
     *
     * @param phone    the phone
     * @param authCode the auth code
     * @param s        the s
     */
    void bindPhone(String phone, String authCode, String s);

    /**
     * Send bind email code.
     *
     * @param email the email
     * @param s     the s
     */
    void sendBindEmailCode(String email, String s);

    /**
     * Gets verified account auths.
     *
     * @param authorization the authorization
     * @return the verified account auths
     */
    Map<String, String> getVerifiedAccountAuths(String authorization);

}
