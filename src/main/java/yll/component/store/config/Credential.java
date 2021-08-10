package yll.component.store.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Desc:
 * @author cc
 */
@Data
@Accessors(chain = true)
public class Credential {

    private String appId;

    @NotBlank
    private String secrectId;

    @NotBlank
    private String secrectKey;
}
