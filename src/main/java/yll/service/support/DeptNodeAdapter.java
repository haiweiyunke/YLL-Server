package yll.service.support;

import com.github.relucent.base.util.tree.TreeUtil.NodeAdapter;

import yll.entity.Department;
import yll.service.model.DeptNode;

public class DeptNodeAdapter implements NodeAdapter<Department, DeptNode> {

    public final static DeptNodeAdapter INSTANCE = new DeptNodeAdapter();

    @Override
    public DeptNode adapte(Department model) {
        DeptNode node = new DeptNode();
        node.setId(model.getId());
        node.setLabel(model.getName());
        return node;
    }
}
