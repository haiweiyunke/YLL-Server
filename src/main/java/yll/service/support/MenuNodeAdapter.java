package yll.service.support;

import com.github.relucent.base.util.tree.TreeUtil.NodeAdapter;

import yll.entity.Permission;
import yll.service.model.MenuNode;

public class MenuNodeAdapter implements NodeAdapter<Permission, MenuNode> {

    public final static MenuNodeAdapter INSTANCE = new MenuNodeAdapter();

    @Override
    public MenuNode adapte(Permission model) {
        MenuNode node = new MenuNode();
        node.setId(model.getId());
        node.setLabel(model.getName());
        node.setValue(model.getValue());
        node.setType(model.getType());
        node.setIcon(model.getIcon());
        return node;
    }
}
