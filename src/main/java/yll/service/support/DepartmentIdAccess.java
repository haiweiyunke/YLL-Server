package yll.service.support;

import com.github.relucent.base.util.tree.TreeUtil.IdAccess;

import yll.entity.Department;

public class DepartmentIdAccess implements IdAccess<Department, String> {

    public final static DepartmentIdAccess INSTANCE = new DepartmentIdAccess();

    @Override
    public String getId(Department model) {
        return model.getId();
    }

    @Override
    public String getParentId(Department model) {
        return model.getParentId();
    }
}
