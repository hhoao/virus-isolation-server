package org.hhoa.vi.admin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Response token info.
 *
 * @author hhoa
 * @since 2022 /9/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenInfo {
    private String token;
    private String tokenHead;
}
