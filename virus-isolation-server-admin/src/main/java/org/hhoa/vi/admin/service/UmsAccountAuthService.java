package org.hhoa.vi.admin.service;


import org.hhoa.vi.admin.bean.IdentifyType;
import org.hhoa.vi.admin.bean.UmsAccountAuthParam;
import org.hhoa.vi.mgb.model.generator.UmsAccountAuth;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface Ums user auth service.
 *
 * @author hhoa
 * @date 2022 /5/29
 */
public interface UmsAccountAuthService {

    /**
     * Gets user auth.
     *
     * @param userId the user id
     * @return the user auth
     */
    List<UmsAccountAuth> getAccountAuth(Long userId);

    /**
     * Gets user auth.
     *
     * @param userId       the user id
     * @param identifyType the identify type
     * @return the user auth
     */
    UmsAccountAuth getAccountAuth(Long userId, IdentifyType identifyType);

    /**
     * Gets user auth with identify type.
     *
     * @param identifyType the identify type
     * @param identifier   the identifier
     * @return the user auth with identify type
     */
    UmsAccountAuth getAccountAuth(IdentifyType identifyType, String identifier);


    /**
     * Exists boolean.
     *
     * @param identifyType the identify type
     * @param identifier   the identifier
     * @return the boolean
     */
    boolean exists(IdentifyType identifyType, String identifier);

    /**
     * Bind.
     *
     * @param id           the id
     * @param identifier   the identifier
     * @param identifyType the identifyType
     */
    @Transactional
    void bind(Long id, String identifier, IdentifyType identifyType);

    /**
     * Update credential.
     *
     * @param userId     the user id
     * @param credential the credential
     */
    void updateCredential(Long userId, String credential);

    /**
     * Delete user auth.
     *
     * @param userId the user id
     */
    @Transactional
    void deleteAllAccountAuth(Long userId);


    /**
     * Update user auth.
     *
     * @param userId        the user id
     * @param identifyType  the identify type
     * @param userAuthParam the user auth param
     */
    void updateAccountAuth(Long userId, IdentifyType identifyType, UmsAccountAuthParam userAuthParam);


    /**
     * Gets user id by user name.
     *
     * @param username the username
     * @return the user id by user name
     */
    Long getAccountIdByAccountName(String username);

    /**
     * Delete user auth.
     *
     * @param userId   the user id
     * @param authType the auth type
     */
    void deleteAccountAuth(Long userId, IdentifyType authType);

    /**
     * Gets user auth.
     *
     * @param identifier the identifier
     * @return the user auth
     */
    UmsAccountAuth getAccountAuth(String identifier);

    /**
     * Exists boolean.
     *
     * @param userId the user id
     * @param email  the email
     * @return the boolean
     */
    boolean exists(Long userId, IdentifyType email);
}
