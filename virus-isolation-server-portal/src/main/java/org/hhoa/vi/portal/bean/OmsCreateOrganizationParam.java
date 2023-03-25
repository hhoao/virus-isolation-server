package org.hhoa.vi.portal.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;

/**
 * OmsCreateOrganizationParam
 *
 * @author hhoa
 * @since 2023/3/20
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class OmsCreateOrganizationParam extends OmsOrganization {
    private String userName;
}
