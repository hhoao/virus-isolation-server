package org.hhoa.vi.admin.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hhoa.vi.mgb.model.generator.UmsAccount;

/**
 * OrganizationAccount
 *
 * @author hhoa
 * @since 2023/3/19
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationAccount extends UmsAccount {
    private Long positionId;
}
