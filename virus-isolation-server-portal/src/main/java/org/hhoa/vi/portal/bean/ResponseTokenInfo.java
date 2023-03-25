package org.hhoa.vi.portal.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应的Token信息.
 *
 * @author hhoa
 * @since 2022/9/6
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenInfo {
    private String token;
    private String tokenHead;
}
