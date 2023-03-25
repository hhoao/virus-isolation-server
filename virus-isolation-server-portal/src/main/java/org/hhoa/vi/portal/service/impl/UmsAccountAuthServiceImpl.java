package org.hhoa.vi.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;

import org.hhoa.vi.common.exception.Asserts;
import org.hhoa.vi.mgb.model.generator.UmsAccountAuth;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author hhoa
 * @date 2022/5/29
 **/
@Service
@RequiredArgsConstructor
public class UmsAccountAuthServiceImpl implements UmsAccountAuthService {
    private final UmsAccountAuthDao accountAuthMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UmsAccountAuth> getAccountAuth(Long accountId) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setAccountId(accountId);
        return accountAuthMapper.selectList(new QueryWrapper<>(accountAuth));
    }

    @Override
    public UmsAccountAuth getAccountAuth(Long accountId, String identifyType) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setAccountId(accountId);
        accountAuth.setIdentityType(identifyType);
        List<UmsAccountAuth> retAccountAuths = accountAuthMapper.selectList(new QueryWrapper<>(accountAuth));
        if (retAccountAuths == null || retAccountAuths.size() == 0) {
            Asserts.fail("没有该验证方式");
        }
        return retAccountAuths.get(0);
    }

    @Override
    public UmsAccountAuth getAccountAuth(String identifyType, String identifier) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setIdentifier(identifier);
        accountAuth.setIdentityType(identifyType);
        List<UmsAccountAuth> retAccountAuths = accountAuthMapper.selectList(new QueryWrapper<>(accountAuth));
        if (retAccountAuths == null || retAccountAuths.size() == 0) {
            Asserts.fail("没有该用户");
        }
        return retAccountAuths.get(0);
    }

    @Override
    public boolean exists(String identifyType, String identifier) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setIdentifier(identifier);
        accountAuth.setIdentityType(identifyType);
        List<UmsAccountAuth> retAccountAuths = accountAuthMapper.selectList(new QueryWrapper<>(accountAuth));
        return retAccountAuths != null && retAccountAuths.size() != 0;
    }

    @Override
    public void bind(Long accountId, String identifier, String identifyType) {
        List<UmsAccountAuth> accountAuth1 = getAccountAuth(accountId);
        String credential = UUID.randomUUID().toString();
        if (accountAuth1.size() > 0) {
            credential = accountAuth1.get(0).getCredential();
        }
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setAccountId(accountId);
        accountAuth.setIdentifier(identifier);
        accountAuth.setIdentityType(identifyType);
        accountAuth.setCredential(credential);
        accountAuthMapper.insert(accountAuth);
    }


    @Override
    public void updateCredential(Long accountId, String credential) {
        List<UmsAccountAuth> accountAuths = getAccountAuth(accountId);
        for (UmsAccountAuth accountAuth : accountAuths) {
            accountAuth.setCredential(passwordEncoder.encode(credential));
            int i = accountAuthMapper.updateById(accountAuth);
            if (i == 0) {
                Asserts.fail("更新失败");
            }
        }
    }


    @Override
    public void deleteAllAccountAuth(Long accountId) {
        UmsAccountAuth accountAuthParam = new UmsAccountAuth();
        accountAuthParam.setAccountId(accountId);
        List<UmsAccountAuth> retAccountAuths = accountAuthMapper.selectList(new QueryWrapper<>(accountAuthParam));
        for (UmsAccountAuth accountAuth : retAccountAuths) {
            accountAuthMapper.deleteById(accountAuth.getId());
        }
    }

    @Override
    public void updateAccountAuth(Long accountId, String identifyType, UmsAccountAuthParam accountAuthParam) {
        UmsAccountAuth oldAccountAuth = getAccountAuth(accountId, identifyType);
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        BeanUtils.copyProperties(accountAuthParam, accountAuth);

        accountAuth.setId(oldAccountAuth.getId());
        accountAuthMapper.updateById(accountAuth);
        if (accountAuthParam.getCredential() != null) {
            updateCredential(accountId, accountAuthParam.getCredential());
        }
    }

    @Override
    public Long getAccountIdByAccountName(String accountname) {
        UmsAccountAuth usernameAuth = getAccountAuth(IdentifyType.USERNAME, accountname);
        return usernameAuth.getAccountId();
    }

    @Override
    public void deleteAccountAuth(Long accountId, String authType) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setAccountId(accountId);
        accountAuth.setIdentityType(authType);
        accountAuthMapper.delete(new QueryWrapper<>(accountAuth));
    }

    @Override
    public UmsAccountAuth getAccountAuth(String identifier) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setIdentifier(identifier);
        List<UmsAccountAuth> umsAccountAuths = accountAuthMapper.selectList(new QueryWrapper<>(accountAuth));
        if (umsAccountAuths == null || umsAccountAuths.size() == 0) {
            Asserts.fail("没有该用户");
        }
        if (umsAccountAuths.size() > 1) {
            Asserts.fail("该认证标识数量不少于一个");
        }

        return umsAccountAuths.get(0);
    }

    @Override
    public boolean exists(Long accountId, String identifyType) {
        UmsAccountAuth accountAuth = new UmsAccountAuth();
        accountAuth.setAccountId(accountId);
        accountAuth.setIdentityType(identifyType);
        return accountAuthMapper.exists(new QueryWrapper<>(accountAuth));
    }
}
