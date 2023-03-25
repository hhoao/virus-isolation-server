package org.hhoa.vi.admin.service.impl;


import lombok.RequiredArgsConstructor;
import org.hhoa.vi.admin.bean.IdentifyType;
import org.hhoa.vi.admin.bean.UmsAccountAuthParam;
import org.hhoa.vi.admin.service.UmsAccountAuthService;
import org.hhoa.vi.common.exception.Asserts;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author hhoa
 * @date 2022/5/29
 **/
@Service
@RequiredArgsConstructor
public class RetUserAuthServiceImpl implements UmsAccountAuthService {
    private final UmsAccountAuthMapper userAuthMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UmsAccountAuth> getAccountAuth(Long userId) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().
                andAccountIdEqualTo(userId);
        return userAuthMapper.selectByExample(userAuthExample);
    }

    @Override
    public UmsAccountAuth getAccountAuth(Long userId, IdentifyType identifyType) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().
                andAccountIdEqualTo(userId).
                andIdentityTypeEqualTo(identifyType.value());
        List<UmsAccountAuth> retAccountAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retAccountAuths == null || retAccountAuths.size() == 0) {
            Asserts.fail("没有该验证方式");
        }
        return retAccountAuths.get(0);
    }

    @Override
    public UmsAccountAuth getAccountAuth(IdentifyType identifyType, String identifier) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().
                andIdentifierEqualTo(identifier).
                andIdentityTypeEqualTo(identifyType.value());
        List<UmsAccountAuth> retAccountAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retAccountAuths == null || retAccountAuths.size() == 0) {
            Asserts.fail("没有该用户");
        }
        return retAccountAuths.get(0);
    }

    @Override
    public boolean exists(IdentifyType identifyType, String identifier) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().
                andIdentityTypeEqualTo(identifyType.value()).
                andIdentifierEqualTo(identifier);
        List<UmsAccountAuth> retAccountAuths = userAuthMapper.selectByExample(userAuthExample);
        return retAccountAuths != null && retAccountAuths.size() != 0;
    }

    @Override
    public void bind(Long id, String identifier, IdentifyType identifyType) {
        UmsAccountAuth userAuth = new UmsAccountAuth();
        String s = UUID.randomUUID().toString();
        userAuth.setAccountId(id);
        userAuth.setIdentifier(identifier);
        userAuth.setIdentityType(identifyType.value());
        userAuth.setCredential(s);
        userAuthMapper.insert(userAuth);
    }


    @Override
    public void updateCredential(Long userId, String credential) {
        List<UmsAccountAuth> userAuths = getAccountAuth(userId);
        for (UmsAccountAuth userAuth : userAuths) {
            userAuth.setCredential(passwordEncoder.encode(credential));
            int i = userAuthMapper.updateByPrimaryKey(userAuth);
            if (i == 0) {
                Asserts.fail("更新失败");
            }
        }
    }

    private UmsAccountAuthExample getAccountAuthExample(UmsAccountAuth userAuth) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        UmsAccountAuthExample.Criteria criteria = userAuthExample.createCriteria();
        if (userAuth.getId() != null) {
            criteria.andIdEqualTo(userAuth.getId());
            return userAuthExample;
        }
        if (userAuth.getAccountId() != null) {
            criteria.andAccountIdEqualTo(userAuth.getAccountId());
            return userAuthExample;
        }
        if (userAuth.getIdentifier() != null) {
            criteria.andIdentifierEqualTo(userAuth.getIdentifier());
            return userAuthExample;
        }
        if (userAuth.getCredential() != null && userAuth.getAccountId() != null) {
            String newCredential = passwordEncoder.encode(userAuth.getCredential());
            criteria.andCredentialEqualTo(newCredential);
        }
        if (userAuth.getIdentityType() != null) {
            criteria.andIdentityTypeEqualTo(userAuth.getIdentityType());
        }
        return userAuthExample;
    }

    @Override
    public void deleteAllAccountAuth(Long userId) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().
                andAccountIdEqualTo(userId);
        List<UmsAccountAuth> retAccountAuths = userAuthMapper.selectByExample(userAuthExample);
        for (UmsAccountAuth userAuth : retAccountAuths) {
            userAuthMapper.deleteByPrimaryKey(userAuth.getId());
            userAuthMapper.deleteByPrimaryKey(userAuth.getId());
        }
    }

    @Override
    public void updateAccountAuth(Long userId, IdentifyType identifyType, UmsAccountAuthParam userAuthParam) {
        UmsAccountAuth oldAccountAuth = getAccountAuth(userId, identifyType);
        UmsAccountAuth userAuth = new UmsAccountAuth();
        BeanUtils.copyProperties(userAuthParam, userAuth);

        userAuth.setId(oldAccountAuth.getId());
        userAuthMapper.updateByPrimaryKeySelective(userAuth);
        if (userAuthParam.getCredential() != null) {
            updateCredential(userId, userAuthParam.getCredential());
        }
    }

    @Override
    public Long getAccountIdByAccountName(String username) {
        UmsAccountAuth usernameAuth = getAccountAuth(IdentifyType.username, username);
        return usernameAuth.getAccountId();
    }

    @Override
    public void deleteAccountAuth(Long userId, IdentifyType authType) {
        UmsAccountAuth userAuth = getAccountAuth(userId, authType);
        int i = userAuthMapper.deleteByPrimaryKey(userAuth.getId());
        if (i == 0) {
            Asserts.fail("删除失败");
        }
    }

    @Override
    public UmsAccountAuth getAccountAuth(String identifier) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().andIdentifierEqualTo(identifier);
        List<UmsAccountAuth> retAccountAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retAccountAuths == null || retAccountAuths.size() == 0) {
            Asserts.fail("没有该用户");
        }
        if (retAccountAuths.size() > 1) {
            Asserts.fail("该认证标识数量不少于一个");
        }

        return retAccountAuths.get(0);
    }

    @Override
    public boolean exists(Long userId, IdentifyType email) {
        UmsAccountAuthExample userAuthExample = new UmsAccountAuthExample();
        userAuthExample.createCriteria().
                andAccountIdEqualTo(userId).
                andIdentityTypeEqualTo(email.toString());
        return userAuthMapper.selectByExample(userAuthExample).size() != 0;
    }
}
