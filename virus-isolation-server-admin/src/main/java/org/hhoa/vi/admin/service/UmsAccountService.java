package org.hhoa.vi.admin.service;


import org.hhoa.vi.admin.bean.AccountAuthWrapper;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.bean.UmsAccountWrapper;
import org.hhoa.vi.admin.bean.UmsLoginParam;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    UserDetails getAccountDetails(String accountName);


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
     * 更新用户.
     *
     * @param accountAuthWrapper 用户参数
     */
    @Transactional
    void updateAccount(AccountAuthWrapper accountAuthWrapper);

    /**
     * Gets account.
     *
     * @param accountId the account id
     * @return the account
     */
    UmsAccount getAccounts(Long accountId);

    /**
     * Gets account.
     *
     * @param account the account
     * @return the account
     */
    List<UmsAccount> getAccount(UmsAccount account);

    /**
     * Delete account by account id.
     *
     * @param accountId the account id
     */
    void deleteAccountByAccountId(Long accountId);

    /**
     * Delete accounts.
     *
     * @param account the account
     */
    void deleteAccounts(UmsAccount account);



    /**
     * Gets account resources.
     *
     * @param accountId the account id
     * @return the account resources
     */
    List<UmsResource> getAccountResources(Long accountId);

    /**
     * Gets account by authorization.
     *
     * @param authorization the authorization
     * @return the account by authorization
     */
    UmsAccountWrapper getAccountByAuthorization(String authorization);

    /**
     * Add account.
     *
     * @param accountAuthWrapper
     */
    void addAccount(AccountAuthWrapper accountAuthWrapper);
}
