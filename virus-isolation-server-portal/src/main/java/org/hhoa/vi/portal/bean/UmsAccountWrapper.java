package org.hhoa.vi.portal.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsRole;

/**
 * The type Ums account wrapper.
 *
 * @author hhoa
 * @since 2022 /9/9
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UmsAccountWrapper extends UmsAccount {
    private OmsOrganization organization;
}
