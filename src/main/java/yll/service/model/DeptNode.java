package yll.service.model;

import java.io.Serializable;
import java.util.List;

import com.github.relucent.base.util.tree.Node;

import lombok.Data;

/**
 * 部门树节点模型
 */
@SuppressWarnings("serial")
@Data
public class DeptNode implements Node<DeptNode>, Serializable {
    /** 主键 */
    private String id;
    /** 名称 */
    private String label;
    /** 子节点 */
    private List<DeptNode> children;
}
