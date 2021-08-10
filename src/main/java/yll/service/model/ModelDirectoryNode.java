package yll.service.model;

import java.io.Serializable;
import java.util.List;

import com.github.relucent.base.util.tree.Node;

import lombok.Data;

/**
 * 数据调阅API树节点模型
 */
@SuppressWarnings("serial")
@Data
public class ModelDirectoryNode implements Node<ModelDirectoryNode>, Serializable {

    /** 主键 */
    private String id;
    /** 名称 */
    private String label;

    /** 名称 */
    private String modelId;
    /** 来源Id */
    private String fetcherId;

    /** 子节点 */
    private List<ModelDirectoryNode> children;
}
