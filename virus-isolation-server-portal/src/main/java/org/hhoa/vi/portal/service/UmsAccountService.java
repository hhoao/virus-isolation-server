package org.hhoa.vi.portal.service;



import com.github.pagehelper.PageInfo;
import org.hhoa.vi.mgb.model.UmsAccount;
import org.hhoa.vi.mgb.model.UmsResource;
import org.springframework.security.core.userdetails.UserDetails;

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
    UmsAccount getAccountByAccountName(String accountName);

    /**
     * Gets account.
     *
     * @param account the account
     * @return the account
     */
    List<UmsAccount> getAccounts(UmsAccount account);

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
}